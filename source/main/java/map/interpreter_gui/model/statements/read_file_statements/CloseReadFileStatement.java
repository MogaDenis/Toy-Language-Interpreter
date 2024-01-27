package map.interpreter_gui.model.statements.read_file_statements;

import java.io.BufferedReader;
import java.io.IOException;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.ExpressionException;
import map.interpreter_gui.model.exceptions.StatementException;
import map.interpreter_gui.model.exceptions.TypeException;
import map.interpreter_gui.model.exceptions.ValueException;
import map.interpreter_gui.model.expressions.Expression;
import map.interpreter_gui.model.statements.IStatement;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.StringValue;
import map.interpreter_gui.model.values.Value;
import map.interpreter_gui.model.structures.ReadFileTable;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.types.StringType;

public class CloseReadFileStatement implements IStatement
{
    private final Expression expression;

    public CloseReadFileStatement(Expression expression)
    {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException
    {
        SymbolTable symbolTable = programState.getSymbolTable();

        Value expressionValue = this.expression.evaluate(symbolTable, programState.getHeap());

        StringValue stringFileName = (StringValue)expressionValue;

        ReadFileTable readFileTable = programState.getReadFileTable();

        BufferedReader bufferedReader = readFileTable.get(stringFileName);

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

        readFileTable.remove(stringFileName);

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
        return new CloseReadFileStatement(this.expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return "closeReadFile(" + this.expression.toString() + ");\n";
    }
}
