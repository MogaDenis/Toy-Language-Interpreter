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
import source.model.structures.WriteFileTable;
import source.model.types.Type;
import source.model.values.StringValue;
import source.model.values.Value;
import source.model.structures.SymbolTable;
import source.model.types.StringType;

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
