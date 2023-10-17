package source.model.structures;

import java.util.HashMap;
import java.util.Map;

public class Dictionary<TKey, TValue> implements IDictionary<TKey, TValue>
{
    private HashMap<TKey, TValue> internalDictionary;

    public Boolean containsKey(TKey key)
    {
        return this.internalDictionary.containsKey(key);
    }   

    public Boolean containsValue(TValue value)
    {
        return this.internalDictionary.containsValue(value);
    }

    public TValue get(TKey key)
    {
        return this.internalDictionary.get(key);
    }

    public void put(TKey key, TValue value)
    {
        this.internalDictionary.put(key, value);
    }

    public TValue remove(TKey key)
    {
        return this.internalDictionary.remove(key);
    }

    public String toString()
    {
        String string = "";

        for (Map.Entry<TKey, TValue> pair : this.internalDictionary.entrySet())
            string += pair.getKey().toString() + " - " + pair.getValue().toString() + '\n';

        return string;
    }
}
