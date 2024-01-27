package map.interpreter_gui.model.statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.ExpressionException;
import map.interpreter_gui.model.exceptions.StatementException;
import map.interpreter_gui.model.exceptions.TypeException;
import map.interpreter_gui.model.exceptions.ValueException;
import map.interpreter_gui.model.expressions.Expression;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.types.Type;

public class VariableDeclarationWithInitializerStatement implements IStatement {
    private final String name;
    private final Type type;
    private final Expression initializer;

    public VariableDeclarationWithInitializerStatement(String name, Type type, Expression initializer) {
        this.name = name;
        this.type = type;
        this.initializer = initializer;
    }

    @Override
    public IStatement deepCopy() {
        return new VariableDeclarationWithInitializerStatement(this.name, this.type.deepCopy(), this.initializer.deepCopy());
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ValueException, ExpressionException {
        SymbolTable symbolTable = programState.getSymbolTable();

        symbolTable.put(this.name, this.initializer.evaluate(programState.getSymbolTable(), programState.getHeap()));

        return null;
    }

    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException {
        if (typeEnvironment.get(this.name) != null)
            throw new TypeException("The given variable was already declared in this scope.");

        Type initializerType = this.initializer.typecheck(typeEnvironment);

        if (!this.type.equals(initializerType))
            throw new TypeException("The type of the variable is not compatible with the type of the initializer.");

        typeEnvironment.put(this.name, this.type);

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return this.type.toString() + " " + this.name + " = " + this.initializer.toString() + ";\n";
    }
}
