package map.interpreter_gui.model.statements.procedure_statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.*;
import map.interpreter_gui.model.statements.IStatement;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.procedure_table.IProcedureTable;
import map.interpreter_gui.model.types.Type;

import java.util.List;

public class NewProcedureStatement implements IStatement {
    private final String procedureName;
    private final List<String> parameterNames;
    private final IStatement procedureBody;

    public NewProcedureStatement(String procedureName, List<String> parameterNames, IStatement procedureBody) {
        this.procedureName = procedureName;
        this.parameterNames = parameterNames;
        this.procedureBody = procedureBody.deepCopy();
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException, EmptyStackException, TypeException {
        IProcedureTable procedureTable = programState.getProcedureTable();

        procedureTable.addProcedure(this.procedureName, this.parameterNames, this.procedureBody);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NewProcedureStatement(this.procedureName, this.parameterNames, this.procedureBody.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException {
        return typeEnvironment;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        string.append("procedure ").append(this.procedureName).append("(");

        for (int i = 0; i < this.parameterNames.size(); i++) {
            string.append(this.parameterNames.get(i));

            if (i != this.parameterNames.size() - 1)
                string.append(", ");
        }

        string.append(") {").append(this.procedureBody).append("}\n");

        return string.toString();
    }
}