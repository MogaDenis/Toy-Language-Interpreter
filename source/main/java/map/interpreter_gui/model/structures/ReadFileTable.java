package map.interpreter_gui.model.structures;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

import map.interpreter_gui.model.values.StringValue;

public class ReadFileTable implements IDictionary<StringValue, BufferedReader>
{
    private final HashMap<StringValue, BufferedReader> internalDictionary;

    public ReadFileTable()
    {
        this.internalDictionary = new HashMap<>();
    }

    @Override
    public Map<StringValue, BufferedReader> getContent()
    {
        return this.internalDictionary;
    }

    @Override
    public Boolean containsKey(StringValue key)
    {
        return this.internalDictionary.containsKey(key);
    }   

    @Override
    public Boolean containsValue(BufferedReader value)
    {
        return this.internalDictionary.containsValue(value);
    }

    @Override
    public BufferedReader get(StringValue key)
    {
        return this.internalDictionary.get(key);
    }

    @Override
    public void put(StringValue key, BufferedReader value)
    {
        this.internalDictionary.put(key, value);
    }

    @Override
    public BufferedReader remove(StringValue key)
    {
        return this.internalDictionary.remove(key);
    }

    @Override
    public String toString()
    {
        if (this.internalDictionary.isEmpty())
            return "Empty dictionary\n";

        StringBuilder string = new StringBuilder();

        for (Map.Entry<StringValue, BufferedReader> pair : this.internalDictionary.entrySet())
            string.append(pair.getKey().toString()).append(" -> ").append(pair.getValue().toString()).append('\n');

        return string.toString();
    }

    @Override
    public String toStringKeySet()
    {
        if (this.internalDictionary.isEmpty())
            return "Empty dictionary\n";

        StringBuilder string = new StringBuilder();

        for (Map.Entry<StringValue, BufferedReader> pair : this.internalDictionary.entrySet())
            string.append(pair.getKey().toString()).append('\n');

        return string.toString();
    }
}
