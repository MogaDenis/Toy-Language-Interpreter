package source.model.structures;

import java.util.HashMap;
import java.util.Map;

import source.model.types.Type;

public class TypeTable implements IDictionary<String, Type>
{
    private Map<String, Type> internalDictionary;

    public TypeTable()
    {
        this.internalDictionary = new HashMap<>();
    }

    @Override
    public Map<String, Type> getContent()
    {
        return this.internalDictionary;
    }

    public TypeTable deepCopy()
    {
        TypeTable typeTable = new TypeTable();

        for (Map.Entry<String, Type> pair : this.internalDictionary.entrySet())
            typeTable.put(pair.getKey(), pair.getValue().deepCopy());

        return typeTable;
    }

    @Override
    public Boolean containsKey(String key)
    {
        return this.internalDictionary.containsKey(key);
    }

    @Override
    public Boolean containsValue(Type value)
    {
        return this.internalDictionary.containsValue(value);
    }

    @Override
    public Type get(String key)
    {
        return this.internalDictionary.get(key);
    }

    @Override
    public void put(String key, Type type)
    {
        this.internalDictionary.put(key, type);
    }

    @Override
    public Type remove(String key)
    {
        return this.internalDictionary.remove(key);
    }

    @Override
    public String toString()
    {
        if (this.internalDictionary.isEmpty())
            return "Empty dictionary\n";

        String string = "";

        for (Map.Entry<String, Type> pair : this.internalDictionary.entrySet())
            string += pair.getKey().toString() + " -> " + pair.getValue().toString() + '\n';

        return string;
    }

    @Override
    public String toStringKeySet()
    {
        if (this.internalDictionary.isEmpty())
            return "Empty dictionary\n";

        String string = "";

        for (Map.Entry<String, Type> pair : this.internalDictionary.entrySet())
            string += pair.getKey().toString() + '\n';

        return string;
    }
}
