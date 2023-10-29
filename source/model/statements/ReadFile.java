package source.model.statements;

import java.io.BufferedReader;
import java.io.IOException;

import source.model.ProgramState;
import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.ValueException;
import source.model.expressions.Expression;
import source.model.structures.IDictionary;
import source.model.types.IntType;
import source.model.types.StringType;
import source.model.values.IntValue;
import source.model.values.StringValue;
import source.model.values.Value;

public class ReadFile implements IStatement
{
    private Expression expression;
    private String variableName;

    public ReadFile(Expression expression, String variableName)
    {
        this.expression = expression;
        this.variableName = variableName;
    }

    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException
    {
        IDictionary<String, Value> symbolTable = programState.getSymbolTable();

        if (symbolTable.containsKey(variableName) == false)
            throw new StatementException("The variable '" + this.variableName + "' is undefined.");

        Value variableValue = symbolTable.get(variableName);

        if (variableValue.getType() != new IntType())
            throw new StatementException("The given variable is not of IntType.");

        IntValue integerValue = (IntValue)variableValue;

        Value expressionValue = this.expression.evaluate(symbolTable);

        if (expressionValue.getType() != new StringType())
            throw new StatementException("The given expression is not of StringType.");

        StringValue stringFileName = (StringValue)expressionValue;

        IDictionary<StringValue, BufferedReader> fileTable = programState.getFileTable();

        BufferedReader bufferedReader = fileTable.get(stringFileName);

        if (bufferedReader == null)
            throw new StatementException("File with given name was not found.");

        String readValue;

        try 
        {
            readValue = bufferedReader.readLine();
        }
        catch (IOException e)
        {
            throw new StatementException(e.getMessage());
        }

        if (readValue == null)
            integerValue = new IntValue(0);
        else 
            integerValue = new IntValue(Integer.parseInt(readValue));

        symbolTable.put(this.variableName, integerValue);        

        return programState;
    }

    public IStatement deepCopyStatement()
    {
        return new ReadFile(this.expression, this.variableName);
    }
}
