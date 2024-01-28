package map.interpreter_gui.model.structures.procedure_table;

import javafx.util.Pair;
import map.interpreter_gui.model.statements.IStatement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcedureTable implements IProcedureTable {
    private final Map<String, Pair<List<String>, IStatement>> internalDictionary;

    public ProcedureTable() {
        this.internalDictionary = new HashMap<>();
    }

    @Override
    public void addProcedure(String procedureName, List<String> parameterNames, IStatement procedureBody) {
        this.internalDictionary.put(procedureName, new Pair<>(parameterNames, procedureBody));
    }

    @Override
    public List<String> getParameterNames(String procedureName) {
        return this.internalDictionary.get(procedureName).getKey();
    }

    @Override
    public IStatement getProcedureBody(String procedureName) {
        return this.internalDictionary.get(procedureName).getValue();
    }

    @Override
    public boolean isDefined(String procedureName) {
        return this.internalDictionary.containsKey(procedureName);
    }

    @Override
    public String toString() {
        if (this.internalDictionary.isEmpty())
            return "Empty table\n";

        StringBuilder string = new StringBuilder();

        for (Map.Entry<String, Pair<List<String>, IStatement>> pair : this.internalDictionary.entrySet())
            string.append(pair.getKey()).append("->").append(pair.getValue().toString()).append("\n");

        return string.toString();
    }
}