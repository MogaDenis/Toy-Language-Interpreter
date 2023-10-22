package source.model.structures;

import java.util.HashMap;
import java.util.Map;

public class Dictionary<TKey, TValue> implements IDictionary<TKey, TValue>
{
    private HashMap<TKey, TValue> internalDictionary;

    public Dictionary()
    {
        this.internalDictionary = new HashMap<>();
    }

    @Override
    public Boolean containsKey(TKey key)
    {
        return this.internalDictionary.containsKey(key);
    }   

    @Override
    public Boolean containsValue(TValue value)
    {
        return this.internalDictionary.containsValue(value);
    }

    @Override
    public TValue get(TKey key)
    {
        return this.internalDictionary.get(key);
    }

    @Override
    public void put(TKey key, TValue value)
    {
        this.internalDictionary.put(key, value);
    }

    @Override
    public TValue remove(TKey key)
    {
        return this.internalDictionary.remove(key);
    }

    @Override
    public String toString()
    {
        if (this.internalDictionary.isEmpty())
            return "Empty dictionary\n";

        String string = "";

        for (Map.Entry<TKey, TValue> pair : this.internalDictionary.entrySet())
            string += pair.getKey().toString() + " -> " + pair.getValue().toString() + '\n';

        return string;
    }
}
