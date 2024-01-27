package map.interpreter_gui.model.statements.read_file_statements;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.ExpressionException;
import map.interpreter_gui.model.exceptions.StatementException;
import map.interpreter_gui.model.exceptions.TypeException;
import map.interpreter_gui.model.exceptions.ValueException;
import map.interpreter_gui.model.expressions.Expression;
import map.interpreter_gui.model.statements.IStatement;
import map.interpreter_gui.model.structures.ReadFileTable;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.types.StringType;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.StringValue;
import map.interpreter_gui.model.values.Value;

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

        ReadFileTable readFileTable = programState.getReadFileTable();
        readFileTable.put(fileName, bufferedReader);

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
