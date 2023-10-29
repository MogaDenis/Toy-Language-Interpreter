package source.model.statements;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import source.model.ProgramState;
import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.ValueException;
import source.model.expressions.Expression;
import source.model.structures.IDictionary;
import source.model.types.StringType;
import source.model.values.StringValue;
import source.model.values.Value;

public class OpenRFile implements IStatement
{
    private Expression expression;

    public OpenRFile(Expression expression)
    {
        this.expression = expression;
    }

    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException
    {
        IDictionary<String, Value> symbolTable = programState.getSymbolTable();
        Value expressionValue = this.expression.evaluate(symbolTable);

        if (expressionValue.getType() != new StringType())
            throw new StatementException("The given expression is not of StringType.");

        StringValue fileName = (StringValue)expressionValue;

        if (symbolTable.containsKey(fileName.getValue()))
            throw new StatementException("There already exists an opened file with the given name.");

        BufferedReader bufferedReader = null;
        try 
        {
            bufferedReader = new BufferedReader(new FileReader(fileName.getValue()));
        }
        catch (IOException e)
        {
            throw new StatementException(e.getMessage());
        }

        IDictionary<StringValue, BufferedReader> fileTable = programState.getFileTable();
        fileTable.put(fileName, bufferedReader);

        return programState;
    }

    public IStatement deepCopyStatement()
    {
        return new OpenRFile(this.expression);
    }
}
