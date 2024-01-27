package map.interpreter_gui.model.statements;

import java.io.BufferedReader;
import java.io.IOException;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.ExpressionException;
import map.interpreter_gui.model.exceptions.StatementException;
import map.interpreter_gui.model.exceptions.TypeException;
import map.interpreter_gui.model.exceptions.ValueException;
import map.interpreter_gui.model.expressions.Expression;
import map.interpreter_gui.model.structures.ReadFileTable;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.types.IntType;
import map.interpreter_gui.model.types.StringType;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.IntValue;
import map.interpreter_gui.model.values.StringValue;
import map.interpreter_gui.model.values.Value;

public class ReadFileStatement implements IStatement
{
    private final Expression expression;
    private final String variableName;

    public ReadFileStatement(Expression expression, String variableName)
    {
        this.expression = expression;
        this.variableName = variableName;
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
            String readValue = bufferedReader.readLine();

            IntValue integerValue;

            if (readValue == null)
                integerValue = new IntValue(0);
            else
                integerValue = new IntValue(Integer.parseInt(readValue));

            symbolTable.put(this.variableName, integerValue);
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
            throw new TypeException("The given expression is not of type StringType");

        if (!typeEnvironment.containsKey(this.variableName))
            throw new TypeException("The given variable name is not defined!");

        Type variableType = typeEnvironment.get(this.variableName);

        if (variableType == null)
            throw new TypeException("The given variable was not declared in this scope.");

        if (!(variableType instanceof IntType))
            throw new TypeException("The given variable is not of type IntType.");

        return typeEnvironment;
    }

    @Override
    public IStatement deepCopy()
    {
        return new ReadFileStatement(this.expression.deepCopy(), this.variableName);
    }

    @Override
    public String toString()
    {
        return "readFile(" + this.expression.toString() + ", " + this.variableName + ");\n";
    }
}
