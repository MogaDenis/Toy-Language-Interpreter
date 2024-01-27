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
import map.interpreter_gui.model.structures.WriteFileTable;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.StringValue;
import map.interpreter_gui.model.values.Value;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.types.StringType;

public class CloseWriteFileStatement implements IStatement
{
    private final Expression expression;

    public CloseWriteFileStatement(Expression expression)
    {
        this.expression = expression;
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

        try
        {
            bufferedWriter.close();
        }
        catch (IOException e)
        {
            throw new StatementException(e.getMessage());
        }

        writeFileTable.remove(stringFileName);

        return null;
    }

    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException
    {
        Type expressionType = this.expression.typecheck(typeEnvironment);

        if (!(expressionType instanceof StringType))
            throw new TypeException("The given expression is not of StringType");

        return typeEnvironment;
    }

    @Override
    public IStatement deepCopy()
    {
        return new CloseWriteFileStatement(this.expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return "closeWriteFile(" + this.expression.toString() + ");\n";
    }
}
