package source.model.statements;

import java.io.BufferedReader;
import java.io.IOException;

import source.model.ProgramState;
import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.ValueException;
import source.model.expressions.Expression;
import source.model.values.StringValue;
import source.model.values.Value;
import source.model.structures.IDictionary;
import source.model.types.StringType;

public class CloseFile implements IStatement
{
    private Expression expression;

    public CloseFile(Expression expression)
    {
        this.expression = expression;
    }

    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException
    {
        IDictionary<String, Value> symbolTable = programState.getSymbolTable();

        Value expressionValue = this.expression.evaluate(symbolTable);

        if (expressionValue.getType() != new StringType())
            throw new StatementException("The given expression is not of StringType");

        StringValue stringFileName = (StringValue)expressionValue;

        IDictionary<StringValue, BufferedReader> fileTable = programState.getFileTable();

        BufferedReader bufferedReader = fileTable.get(stringFileName);

        if (bufferedReader == null)
            throw new StatementException("File with given name was not found.");

        try 
        {
            bufferedReader.close();
        }
        catch (IOException e)
        {
            throw new StatementException(e.getMessage());
        }

        fileTable.remove(stringFileName);

        return programState;
    }

    public IStatement deepCopyStatement()
    {
        return new CloseFile(this.expression);
    }
}
