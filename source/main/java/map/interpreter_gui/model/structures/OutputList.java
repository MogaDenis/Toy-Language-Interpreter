package map.interpreter_gui.model.structures;

import java.util.ArrayList;

import map.interpreter_gui.model.values.Value;

import java.util.List;

public class OutputList implements IList<Value>
{
    private final List<Value> internalList;

    public OutputList()
    {
        this.internalList = new ArrayList<>();
    }

    public List<Value> getList() {
        return this.internalList;
    }

    public void add(Value element) {
        this.internalList.add(element);
    }
    public void add(int index, Value element) {
        this.internalList.add(index, element);
    }
    public void set(int index, Value element) {
        this.internalList.set(index, element);
    }
    public Integer indexOf(Value element) {
        return this.internalList.indexOf(element);
    }
    public Integer lastIndexOf(Value element) {
        return this.internalList.lastIndexOf(element);
    }
    public Boolean remove(Value element) {
        return this.internalList.remove(element);
    }
    public Value remove(int index) {
        return this.internalList.remove(index);
    }

    public Value get(int index) {
        return this.internalList.get(index);
    }
    public Boolean contains(Value element) {
        return this.internalList.contains(element);
    }

    public Boolean isEmpty() {
        return this.internalList.isEmpty();
    }
    public Integer size() {
        return this.internalList.size();
    }

    @Override
    public String toString() {
        if (this.internalList.isEmpty())
            return "Empty list\n";

        StringBuilder string = new StringBuilder();

        for (Value value : this.internalList)
            string.append(value.toString()).append("\n");

        return string.toString();
    }
}
