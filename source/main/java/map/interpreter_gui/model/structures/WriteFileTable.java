package map.interpreter_gui.model.structures;

import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;

import map.interpreter_gui.model.values.StringValue;

public class WriteFileTable implements IDictionary<StringValue, BufferedWriter>
{
    private final HashMap<StringValue, BufferedWriter> internalDictionary;

    public WriteFileTable()
    {
        this.internalDictionary = new HashMap<>();
    }

    @Override
    public Map<StringValue, BufferedWriter> getContent()
    {
        return this.internalDictionary;
    }

    @Override
    public Boolean containsKey(StringValue key)
    {
        return this.internalDictionary.containsKey(key);
    }

    @Override
    public Boolean containsValue(BufferedWriter value)
    {
        return this.internalDictionary.containsValue(value);
    }

    @Override
    public BufferedWriter get(StringValue key)
    {
        return this.internalDictionary.get(key);
    }

    @Override
    public void put(StringValue key, BufferedWriter value)
    {
        this.internalDictionary.put(key, value);
    }

    @Override
    public BufferedWriter remove(StringValue key)
    {
        return this.internalDictionary.remove(key);
    }

    @Override
    public String toString()
    {
        if (this.internalDictionary.isEmpty())
            return "Empty dictionary\n";

        StringBuilder string = new StringBuilder();

        for (Map.Entry<StringValue, BufferedWriter> pair : this.internalDictionary.entrySet())
            string.append(pair.getKey().toString()).append(" -> ").append(pair.getValue().toString()).append('\n');

        return string.toString();
    }

    @Override
    public String toStringKeySet()
    {
        if (this.internalDictionary.isEmpty())
            return "Empty dictionary\n";

        StringBuilder string = new StringBuilder();

        for (Map.Entry<StringValue, BufferedWriter> pair : this.internalDictionary.entrySet())
            string.append(pair.getKey().toString()).append('\n');

        return string.toString();
    }
}
