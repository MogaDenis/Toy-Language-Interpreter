package map.interpreter_gui.model.structures;

import map.interpreter_gui.model.values.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Heap implements IHeap
{
    private Map<Integer, Value> content;
    private Integer nextFree;

    public Heap()
    {
        this.content = new HashMap<>();
        this.nextFree = 1;
    }

    @Override
    public Integer allocate(Value value)
    {
        this.content.put(this.nextFree, value);
        return this.nextFree++;
    }

    @Override
    public void update(Integer address, Value value)
    {
        this.content.put(address, value);
    }

    @Override
    public Value get(Integer address)
    {
        return this.content.get(address);
    }

    @Override
    public Boolean isUsed(Integer address)
    {
        return this.content.containsKey(address);
    }

    @Override
    public Map<Integer, Value> getContent()
    {
        return new HashMap<>(this.content);
    }

    @Override
    public void setContent(Map<Integer, Value> newContent)
    {
        this.content = newContent;
    }

    @Override
    public List<Value> getValues()
    {
        List<Value> values = new ArrayList<>();

        for (Map.Entry<Integer, Value> pair : this.content.entrySet())
            values.add(pair.getValue());

        return values;
    }

    @Override
    public String toString()
    {
        if (this.content.isEmpty())
            return "Empty heap\n";

        StringBuilder string = new StringBuilder();

        for (Map.Entry<Integer, Value> pair : this.content.entrySet())
            string.append(pair.getKey()).append("->").append(pair.getValue().toString()).append("\n");

        return string.toString();
    }
}
