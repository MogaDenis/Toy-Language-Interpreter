package map.interpreter_gui.model.structures;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountSemaphoreTable implements ICountSemaphoreTable {
    private Map<Integer, Pair<Integer, List<Integer>>> internalDictionary;
    private Integer nextFreeLocation;

    public CountSemaphoreTable() {
        this.internalDictionary = new HashMap<>();
        this.nextFreeLocation = 1;
    }

    @Override
    public synchronized Integer add(Integer value) {
        this.internalDictionary.put(this.nextFreeLocation, new Pair<>(value, new ArrayList<>()));
        return this.nextFreeLocation++;
    }

    @Override
    public synchronized void addProgramStateIDToList(Integer address, Integer ID) {
        this.internalDictionary.get(address).getValue().add(ID);
    }

    @Override
    public synchronized void removeProgramStateIDFromList(Integer address, Integer ID) {
        this.internalDictionary.get(address).getValue().remove(ID);
    }

    @Override
    public synchronized Pair<Integer, List<Integer>> get(Integer address) {
        return this.internalDictionary.get(address);
    }

    @Override
    public synchronized Integer getValue(Integer address) {
        return this.internalDictionary.get(address).getKey();
    }

    @Override
    public synchronized List<Integer> getListOfProgramStateIDs(Integer address) {
        return this.internalDictionary.get(address).getValue();
    }

    @Override
    public synchronized Boolean isUsed(Integer address) {
        return this.internalDictionary.containsKey(address);
    }

    @Override
    public synchronized Map<Integer, Pair<Integer, String>> getContentString() {
        Map<Integer, Pair<Integer, String>> representation = new HashMap<>();

        for (Map.Entry<Integer, Pair<Integer, List<Integer>>> pair : this.internalDictionary.entrySet())
            representation.put(pair.getKey(), new Pair<>(pair.getValue().getKey(),
                    pair.getValue().getValue().toString()));

        return representation;
    }

    @Override
    public synchronized void setContent(Map<Integer, Pair<Integer, List<Integer>>> newContent) {
        this.internalDictionary = newContent;
    }

    @Override
    public synchronized List<Pair<Integer, List<Integer>>> getValues() {
        List<Pair<Integer, List<Integer>>> values = new ArrayList<>();

        for (Map.Entry<Integer, Pair<Integer, List<Integer>>> pair : this.internalDictionary.entrySet()) {
            values.add(pair.getValue());
        }

        return values;
    }

    @Override
    public String toString() {
        if (this.internalDictionary.isEmpty())
            return "Empty table\n";

        StringBuilder string = new StringBuilder();

        for (Map.Entry<Integer, Pair<Integer, List<Integer>>> pair : this.internalDictionary.entrySet())
            string.append(pair.getKey()).append("->").append(pair.getValue().toString()).append("\n");

        return string.toString();
    }
}