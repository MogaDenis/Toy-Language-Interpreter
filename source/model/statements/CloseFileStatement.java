package source.model.statements;

import java.io.BufferedReader;
import java.io.IOException;

import source.model.ProgramState;
import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.TypeException;
import source.model.exceptions.ValueException;
import source.model.expressions.Expression;
import source.model.structures.Dictionary;
import source.model.structures.IDictionary;
import source.model.types.Type;
import source.model.values.StringValue;
import source.model.values.Value;
import source.model.structures.FileTable;
import source.model.structures.SymbolTable;
import source.model.types.StringType;

public class CloseFileStatement implements IStatement
{
    private Expression expression;

    public CloseFileStatement(Expression expression)
    {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException
    {
        SymbolTable symbolTable = programState.getSymbolTable();

        Value expressionValue = this.expression.evaluate(symbolTable, programState.getHeap());

        if (expressionValue.getType().equals(new StringType()) == false)
            throw new StatementException("The given expression is not of StringType");

        StringValue stringFileName = (StringValue)expressionValue;

        FileTable fileTable = programState.getFileTable();

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
        return new CloseFileStatement(this.expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return "closeReadFile(" + this.expression.toString() + ");\n";
    }
}
