package map.interpreter_gui.model.statements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.ExpressionException;
import map.interpreter_gui.model.exceptions.StatementException;
import map.interpreter_gui.model.exceptions.TypeException;
import map.interpreter_gui.model.exceptions.ValueException;
import map.interpreter_gui.model.expressions.Expression;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.structures.WriteFileTable;
import map.interpreter_gui.model.types.StringType;
import map.interpreter_gui.model.types.Type;
import map.interpreter_gui.model.values.StringValue;
import map.interpreter_gui.model.values.Value;

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
