package map.interpreter_gui.model.statements.declaration_statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.StatementException;
import map.interpreter_gui.model.exceptions.TypeException;
import map.interpreter_gui.model.statements.IStatement;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.types.Type;

public class VariableDeclarationStatement implements IStatement {
    private final String name;
    private final Type type;

    public VariableDeclarationStatement(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public IStatement deepCopy() {
        return new VariableDeclarationStatement(this.name, this.type);
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException {
        SymbolTable symbolTable = programState.getSymbolTable();

        if (symbolTable.containsKey(name))
            throw new StatementException("Variable " + this.name + " was already defined.");

        symbolTable.put(this.name, this.type.defaultValue());

        return null;
    }

    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException {
        if (typeEnvironment.get(this.name) != null)
            throw new TypeException("The given variable was already declared in this scope.");

        typeEnvironment.put(this.name, this.type);

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return this.type.toString() + " " + this.name + ";\n";
    }
}
