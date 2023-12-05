package source.model.statements;

import source.model.exceptions.TypeException;
import source.model.expressions.Expression;
import source.model.structures.IDictionary;
import source.model.structures.SymbolTable;
import source.model.values.Value;
import source.model.ProgramState;
import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.ValueException;
import source.model.types.Type;

public class AssignmentStatement implements IStatement
{
    private final String id;
    private final Expression expression;

    public AssignmentStatement(String id, Expression expression)
    {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public IStatement deepCopy()
    {
        return new AssignmentStatement(this.id, this.expression.deepCopy());
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException
    {
        SymbolTable symbolTable = programState.getSymbolTable();

        if (!symbolTable.containsKey(this.id))
            throw new StatementException("The used variable " + this.id + " was not declared.");

        Value value = this.expression.evaluate(symbolTable, programState.getHeap());

        symbolTable.put(id, value);

        return null;
    }

    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException
    {
        Type variableType = typeEnvironment.get(this.id);
        Type expressionType = this.expression.typecheck(typeEnvironment);

        if (!variableType.equals(expressionType))
            throw new TypeException("Declared type of variable \" + this.id + \" and type of assigned expression do not match.");

        return typeEnvironment;
    }

    @Override
    public String toString()
    {
        return this.id + " = " + this.expression.toString() + ";\n";  
    }
}
