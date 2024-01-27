package map.interpreter_gui.model.statements.write_file_statements;

import java.io.BufferedWriter;
import java.io.IOException;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.ExpressionException;
import map.interpreter_gui.model.exceptions.StatementException;
import map.interpreter_gui.model.exceptions.TypeException;
import map.interpreter_gui.model.exceptions.ValueException;
import map.interpreter_gui.model.expressions.Expression;
import map.interpreter_gui.model.statements.IStatement;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.structures.WriteFileTable;
import map.interpreter_gui.model.types.CharType;
import map.interpreter_gui.model.types.IntType;
import map.interpreter_gui.model.types.StringType;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.CharValue;
import map.interpreter_gui.model.values.IntValue;
import map.interpreter_gui.model.values.StringValue;
import map.interpreter_gui.model.values.Value;

public class WriteFileStatement implements IStatement
{
    private final Expression expression;
    private final Expression expressionToWrite;

    public WriteFileStatement(Expression expression, Expression expressionToWrite)
    {
        this.expression = expression;
        this.expressionToWrite = expressionToWrite;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException
    {
        SymbolTable symbolTable = programState.getSymbolTable();

        Value expressionValue = this.expression.evaluate(symbolTable, programState.getHeap());
        StringValue stringFileName = (StringValue)expressionValue;

        WriteFileTable writeFileTable = programState.getWriteFileTable();

        BufferedWriter bufferedWriter = writeFileTable.get(stringFileName);

        if (bufferedWriter == null)
            throw new StatementException("File with given name was not found.");

        Value expressionToWriteValue = this.expressionToWrite.evaluate(symbolTable, programState.getHeap());

        try
        {
            if (expressionToWriteValue instanceof IntValue)
                bufferedWriter.write(String.valueOf(((IntValue) expressionToWriteValue).getValue()));
            else if (expressionToWriteValue instanceof StringValue)
                bufferedWriter.write(((StringValue) expressionToWriteValue).getValue());
            else
                bufferedWriter.write(((CharValue) expressionToWriteValue).getValue());

            bufferedWriter.write('\n');
        }
        catch (IOException e)
        {
            throw new StatementException(e.getMessage());
        }

        return null;
    }

    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException
    {
        Type expressionType = this.expression.typecheck(typeEnvironment);

        if (!(expressionType instanceof StringType))
            throw new TypeException("The given expression is not of type StringType.");

        Type expressionToWriteType = this.expressionToWrite.typecheck(typeEnvironment);

        if (!(expressionToWriteType instanceof IntType) && !(expressionToWriteType instanceof StringType) &&
                !(expressionToWriteType instanceof CharType))
            throw new TypeException("The given expression type can't be written to a file.");

        return typeEnvironment;
    }

    @Override
    public IStatement deepCopy()
    {
        return new WriteFileStatement(this.expression.deepCopy(), this.expressionToWrite);
    }

    @Override
    public String toString()
    {
        return "writeFile(" + this.expression.toString() + ", " + this.expressionToWrite + ");\n";
    }
}
