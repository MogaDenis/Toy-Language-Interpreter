package map.interpreter_gui.model.statements.procedure_statements;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.*;
import map.interpreter_gui.model.expressions.Expression;
import map.interpreter_gui.model.statements.IStatement;
import map.interpreter_gui.model.structures.ExecutionStack;
import map.interpreter_gui.model.structures.IDictionary;
import map.interpreter_gui.model.structures.IHeap;
import map.interpreter_gui.model.structures.SymbolTable;
import map.interpreter_gui.model.structures.procedure_table.IProcedureTable;
import map.interpreter_gui.model.types.Type;

import java.util.List;

public class CallProcedureStatement implements IStatement {
    private final String procedureName;
    private final List<Expression> arguments;

    public CallProcedureStatement(String procedureName, List<Expression> arguments) {
        this.procedureName = procedureName;
        this.arguments = arguments;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException, ValueException, EmptyStackException, TypeException {
        ExecutionStack executionStack = programState.getExecutionStack();
        SymbolTable currentSymbolTable = programState.getSymbolTable();
        IHeap heap = programState.getHeap();
        IProcedureTable procedureTable = programState.getProcedureTable();

        if (!procedureTable.isDefined(this.procedureName))
            throw new StatementException("The given procedure was not defined.");

        List<String> parameterNames = procedureTable.getParameterNames(this.procedureName);
        IStatement procedureBody = procedureTable.getProcedureBody(this.procedureName);

        if (parameterNames.size() != this.arguments.size())
            throw new StatementException("Inappropriate number of arguments passed to procedure.");

        SymbolTable procedureSymbolTable = new SymbolTable();

        for (int index = 0; index < this.arguments.size(); index++) {
            String parameterName = parameterNames.get(index);
            Expression argumentExpression = this.arguments.get(index);

            procedureSymbolTable.put(parameterName, argumentExpression.evaluate(currentSymbolTable, heap));
        }

        programState.getSymbolTableStack().push(procedureSymbolTable);
        executionStack.push(new ReturnStatement());
        executionStack.push(procedureBody);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CallProcedureStatement(this.procedureName, this.arguments);
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) throws TypeException {
        return typeEnvironment;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        string.append("call ").append(this.procedureName).append("(");

        for (int i = 0; i < this.arguments.size(); i++) {
            string.append(this.arguments.get(i));

            if (i != this.arguments.size() - 1)
                string.append(", ");
        }

        string.append(");\n");

        return string.toString();
    }
}