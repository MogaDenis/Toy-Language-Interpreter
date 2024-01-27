package map.interpreter_gui.model.structures.lock_table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LockTable implements ILockTable {
    private Map<Integer, Integer> internalDictionary;
    private Integer nextFreeLocation;

    public LockTable() {
        this.internalDictionary = new HashMap<>();
        this.nextFreeLocation = 1;
    }

    @Override
    public synchronized Integer add() {
        this.internalDictionary.put(nextFreeLocation, -1);

        return this.nextFreeLocation++;
    }
    @Override
    public synchronized void update(Integer address, Integer programStateID) {
        this.internalDictionary.put(address, programStateID);
    }

    @Override
    public synchronized Integer get(Integer address) {
        return this.internalDictionary.get(address);
    }

    @Override
    public synchronized Boolean isUsed(Integer address) {
        return this.internalDictionary.containsKey(address);
    }

    @Override
    public synchronized Map<Integer, Integer> getContent() {
        return this.internalDictionary;
    }

    @Override
    public synchronized void setContent(Map<Integer, Integer> newContent) {
        this.internalDictionary = newContent;
    }

    @Override
    public synchronized List<Integer> getValues() {
        List<Integer> values = new ArrayList<>();

        for (Map.Entry<Integer, Integer> pair : this.internalDictionary.entrySet()) {
            values.add(pair.getValue());
        }

        return values;
    }

    @Override
    public String toString() {
        if (this.internalDictionary.isEmpty())
            return "Empty table\n";

        StringBuilder string = new StringBuilder();

        for (Map.Entry<Integer, Integer> pair : this.internalDictionary.entrySet())
            string.append(pair.getKey()).append("->").append(pair.getValue().toString()).append("\n");

        return string.toString();
    }
}