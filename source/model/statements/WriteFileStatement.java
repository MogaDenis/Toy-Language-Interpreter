package source.model.statements;

import java.io.BufferedWriter;
import java.io.IOException;

import source.model.ProgramState;
import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.TypeException;
import source.model.exceptions.ValueException;
import source.model.expressions.Expression;
import source.model.structures.IDictionary;
import source.model.structures.SymbolTable;
import source.model.structures.WriteFileTable;
import source.model.types.CharType;
import source.model.types.IntType;
import source.model.types.StringType;
import source.model.types.Type;
import source.model.values.CharValue;
import source.model.values.IntValue;
import source.model.values.StringValue;
import source.model.values.Value;

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
