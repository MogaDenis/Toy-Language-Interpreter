package source.model.structures;

import java.util.HashMap;

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
}
