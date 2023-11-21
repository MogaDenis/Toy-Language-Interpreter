package source.model.structures;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

import source.model.values.StringValue;

public class FileTable implements IDictionary<StringValue, BufferedReader>
{
    private HashMap<StringValue, BufferedReader> internalDictionary;

    public FileTable()
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

        String string = "";

        for (Map.Entry<StringValue, BufferedReader> pair : this.internalDictionary.entrySet())
            string += pair.getKey().toString() + " -> " + pair.getValue().toString() + '\n';

        return string;
    }

    @Override
    public String toStringKeySet()
    {
        if (this.internalDictionary.isEmpty())
            return "Empty dictionary\n";

        String string = "";

        for (Map.Entry<StringValue, BufferedReader> pair : this.internalDictionary.entrySet())
            string += pair.getKey().toString() + '\n';

        return string;
    }
}
