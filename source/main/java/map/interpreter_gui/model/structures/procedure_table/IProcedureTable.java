package map.interpreter_gui.model.structures.procedure_table;

import map.interpreter_gui.model.statements.IStatement;

import java.util.List;

public interface IProcedureTable {

    void addProcedure(String procedureName, List<String> parameterNames, IStatement procedureBody);

    List<String> getParameterNames(String procedureName);

    IStatement getProcedureBody(String procedureName);

    boolean isDefined(String procedureName);
}