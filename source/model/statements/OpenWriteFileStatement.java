package source.model.statements;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
import source.model.types.StringType;
import source.model.types.Type;
import source.model.values.StringValue;
import source.model.values.Value;

public class OpenWriteFileStatement implements IStatement
{
    private final Expression expression;

    public OpenWriteFileStatement(Expression expression)
    {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException
    {
        SymbolTable symbolTable = programState.getSymbolTable();
        Value expressionValue = this.expression.evaluate(symbolTable, programState.getHeap());

        StringValue fileName = (StringValue)expressionValue;

        if (symbolTable.containsKey(fileName.getValue()))
            throw new StatementException("There already exists an opened file with the given name.");

        BufferedWriter bufferedWriter;
        try
        {
            bufferedWriter = new BufferedWriter(new FileWriter(fileName.getValue()));
        }
        catch (IOException e)
        {
            throw new StatementException(e.getMessage());
        }

        WriteFileTable writeFileTable = programState.getWriteFileTable();
        writeFileTable.put(fileName, bufferedWriter);

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
        return new OpenWriteFileStatement(this.expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return "openWriteFile(" + this.expression.toString() + ");\n";
    }
}
