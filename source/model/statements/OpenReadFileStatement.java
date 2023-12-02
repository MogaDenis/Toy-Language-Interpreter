package source.model.statements;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import source.model.ProgramState;
import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.TypeException;
import source.model.exceptions.ValueException;
import source.model.expressions.Expression;
import source.model.structures.FileTable;
import source.model.structures.IDictionary;
import source.model.structures.SymbolTable;
import source.model.types.StringType;
import source.model.types.Type;
import source.model.values.StringValue;
import source.model.values.Value;

public class OpenReadFileStatement implements IStatement
{
    private final Expression expression;

    public OpenReadFileStatement(Expression expression)
    {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException
    {
        SymbolTable symbolTable = programState.getSymbolTable();
        Value expressionValue = this.expression.evaluate(symbolTable, programState.getHeap());

//        if (!expressionValue.getType().equals(new StringType()))
//            throw new StatementException("The given expression is not of StringType.");

        StringValue fileName = (StringValue)expressionValue;

        if (symbolTable.containsKey(fileName.getValue()))
            throw new StatementException("There already exists an opened file with the given name.");

        BufferedReader bufferedReader;
        try 
        {
            bufferedReader = new BufferedReader(new FileReader(fileName.getValue()));
        }
        catch (IOException e)
        {
            throw new StatementException(e.getMessage());
        }

        FileTable fileTable = programState.getFileTable();
        fileTable.put(fileName, bufferedReader);

        return null;
    }

    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException
    {
        Type expressionType = this.expression.typecheck(typeEnvironment);

        if (!(expressionType instanceof StringType))
            throw new TypeException("The given expression is not of type StringType.");

        return typeEnvironment;
    }

    @Override
    public IStatement deepCopy()
    {
        return new OpenReadFileStatement(this.expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return "openReadFile(" + this.expression.toString() + ");\n";
    }
}
