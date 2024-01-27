package map.interpreter_gui.model.structures.toy_semaphore_table;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToySemaphoreTable implements IToySemaphoreTable {
    private Map<Integer, Pair<Integer, Pair<List<Integer>, Integer>>> internalDictionary;
    private Integer nextFreeLocation;

    public ToySemaphoreTable() {
        this.internalDictionary = new HashMap<>();
        this.nextFreeLocation = 1;
    }

    @Override
    public synchronized Integer add(Integer firstValue, Integer secondValue) {
        this.internalDictionary.put(this.nextFreeLocation, new Pair<>(firstValue, new Pair<>(new ArrayList<>(), secondValue)));

        return this.nextFreeLocation++;
    }

    @Override
    public synchronized void addProgramStateIDToList(Integer address, Integer ID) {
        this.internalDictionary.get(address).getValue().getKey().add(ID);
    }

    @Override
    public void removeProgramStateIDFromList(Integer address, Integer ID) {
        this.internalDictionary.get(address).getValue().getKey().remove(ID);
    }

    @Override
    public synchronized Pair<Integer, Pair<List<Integer>, Integer>> get(Integer address) {
        return this.internalDictionary.get(address);
    }

    @Override
    public synchronized Integer getFirstValue(Integer address) {
        return this.internalDictionary.get(address).getKey();
    }

    @Override
    public synchronized Integer getSecondValue(Integer address) {
        return this.internalDictionary.get(address).getValue().getValue();
    }

    @Override
    public synchronized List<Integer> getListOfProgramStateIDs(Integer address) {
        return this.internalDictionary.get(address).getValue().getKey();
    }

    @Override
    public synchronized Boolean isUsed(Integer address) {
        return this.internalDictionary.containsKey(address);
    }

    @Override
    public synchronized Map<Integer, Pair<Integer, Pair<String, Integer>>> getContentString() {
        Map<Integer, Pair<Integer, Pair<String, Integer>>> representation = new HashMap<>();

        for (Map.Entry<Integer, Pair<Integer, Pair<List<Integer>, Integer>>> pair : this.internalDictionary.entrySet())
            representation.put(pair.getKey(), new Pair<>(pair.getValue().getKey(),
                    new Pair<>(pair.getValue().getValue().getKey().toString(),
                            pair.getValue().getValue().getValue())));

        return representation;
    }

    @Override
    public synchronized void setContent(Map<Integer, Pair<Integer, Pair<List<Integer>, Integer>>> newContent) {
        this.internalDictionary = newContent;
    }

    @Override
    public synchronized List<Pair<Integer, Pair<List<Integer>, Integer>>> getValues() {
        List<Pair<Integer, Pair<List<Integer>, Integer>>> values = new ArrayList<>();

        for (Map.Entry<Integer, Pair<Integer, Pair<List<Integer>, Integer>>> pair : this.internalDictionary.entrySet()) {
            values.add(pair.getValue());
        }

        return values;
    }

    @Override
    public String toString() {
        if (this.internalDictionary.isEmpty())
            return "Empty table\n";

        StringBuilder string = new StringBuilder();

        for (Map.Entry<Integer, Pair<Integer, Pair<List<Integer>, Integer>>> pair : this.internalDictionary.entrySet())
            string.append(pair.getKey()).append("->").append(pair.getValue().toString()).append("\n");

        return string.toString();
    }
}