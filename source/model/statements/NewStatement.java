package source.model.statements;

import source.model.ProgramState;
import source.model.exceptions.ExpressionException;
import source.model.exceptions.StatementException;
import source.model.exceptions.TypeException;
import source.model.exceptions.ValueException;
import source.model.expressions.Expression;
import source.model.structures.IDictionary;
import source.model.structures.IHeap;
import source.model.structures.SymbolTable;
import source.model.types.ReferenceType;
import source.model.types.Type;
import source.model.values.ReferenceValue;
import source.model.values.Value;

public class NewStatement implements IStatement
{
    private final String variableName;
    private final Expression expression;

    public NewStatement(String variableName, Expression expression)
    {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException
    {
        SymbolTable symbolTable = programState.getSymbolTable();
        
        if (!symbolTable.containsKey(this.variableName))
            throw new StatementException("The given variable was not defined.");

        if (!(symbolTable.get(this.variableName).getType() instanceof ReferenceType))
            throw new StatementException("The given variable is not of ReferenceType.");

//        Value referenceValue = symbolTable.get(this.variableName);
        Value expressionValue = this.expression.evaluate(symbolTable, programState.getHeap());

//        ReferenceType referenceType = (ReferenceType)referenceValue.getType();

//        if (!referenceType.getInner().equals(expressionValue.getType()))
//            throw new StatementException("The type of the expression and the type of the reference do not match.");

        IHeap heap = programState.getHeap();
        Integer address = heap.allocate(expressionValue);

        symbolTable.put(variableName, new ReferenceValue(address, expressionValue.getType()));

        return null;
    }

    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException
    {
        Type variableType = typeEnvironment.get(this.variableName);
        Type expressionType = this.expression.typecheck(typeEnvironment);

        if (!variableType.equals(new ReferenceType(expressionType)))
            throw new TypeException("The type of the expression and the type of the reference do not match.");

        return typeEnvironment;
    }

    @Override
    public IStatement deepCopy()
    {
        return new NewStatement(this.variableName, this.expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return "new(" + this.variableName + ", " + this.expression.toString() + ")\n";
    }
}
