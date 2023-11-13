package source.model.structures;

import java.util.HashMap;
import java.util.Map;

public class Dictionary<K, V> implements IDictionary<K, V>
{
    private HashMap<K, V> internalDictionary;

    public Dictionary()
    {
        this.internalDictionary = new HashMap<>();
    }

    @Override
    public Map<K, V> getContent()
    {
        return this.internalDictionary;
    }

    @Override
    public Boolean containsKey(K key)
    {
        return this.internalDictionary.containsKey(key);
    }   

    @Override
    public Boolean containsValue(V value)
    {
        return this.internalDictionary.containsValue(value);
    }

    @Override
    public V get(K key)
    {
        return this.internalDictionary.get(key);
    }

    @Override
    public void put(K key, V value)
    {
        this.internalDictionary.put(key, value);
    }

    @Override
    public V remove(K key)
    {
        return this.internalDictionary.remove(key);
    }

    @Override
    public String toString()
    {
        if (this.internalDictionary.isEmpty())
            return "Empty dictionary\n";

        String string = "";

        for (Map.Entry<K, V> pair : this.internalDictionary.entrySet())
            string += pair.getKey().toString() + " -> " + pair.getValue().toString() + '\n';

        return string;
    }

    @Override
    public String toStringKeySet()
    {
        if (this.internalDictionary.isEmpty())
            return "Empty dictionary\n";

        String string = "";

        for (Map.Entry<K, V> pair : this.internalDictionary.entrySet())
            string += pair.getKey().toString() + '\n';

        return string;
    }
}
