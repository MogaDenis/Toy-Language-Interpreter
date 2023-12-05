package source.model.structures;

import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;

import source.model.values.StringValue;

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

        String string = "";

        for (Map.Entry<StringValue, BufferedWriter> pair : this.internalDictionary.entrySet())
            string += pair.getKey().toString() + " -> " + pair.getValue().toString() + '\n';

        return string;
    }

    @Override
    public String toStringKeySet()
    {
        if (this.internalDictionary.isEmpty())
            return "Empty dictionary\n";

        String string = "";

        for (Map.Entry<StringValue, BufferedWriter> pair : this.internalDictionary.entrySet())
            string += pair.getKey().toString() + '\n';

        return string;
    }
}
