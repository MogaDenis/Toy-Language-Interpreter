package map.interpreter_gui.model.structures;

import java.util.HashMap;
import java.util.Map;

import map.interpreter_gui.model.values.Value;

public class SymbolTable implements IDictionary<String, Value>
{
    private final Map<String, Value> internalDictionary;

    public SymbolTable()
    {
        this.internalDictionary = new HashMap<>();
    }

    @Override
    public Map<String, Value> getContent()
    {
        return this.internalDictionary;
    }

    public SymbolTable deepCopy()
    {
        SymbolTable symbolTable = new SymbolTable();

        for (Map.Entry<String, Value> pair : this.internalDictionary.entrySet())
            symbolTable.put(pair.getKey(), pair.getValue().deepCopy());

        return symbolTable;
    }

    @Override
    public Boolean containsKey(String key)
    {
        return this.internalDictionary.containsKey(key);
    }   

    @Override
    public Boolean containsValue(Value value)
    {
        return this.internalDictionary.containsValue(value);
    }

    @Override
    public Value get(String key)
    {
        return this.internalDictionary.get(key);
    }

    @Override
    public void put(String key, Value value)
    {
        this.internalDictionary.put(key, value);
    }

    @Override
    public Value remove(String key)
    {
        return this.internalDictionary.remove(key);
    }

    @Override
    public String toString()
    {
        if (this.internalDictionary.isEmpty())
            return "Empty dictionary\n";

        StringBuilder string = new StringBuilder();

        for (Map.Entry<String, Value> pair : this.internalDictionary.entrySet())
            string.append(pair.getKey()).append(" -> ").append(pair.getValue().toString()).append('\n');

        return string.toString();
    }

    @Override
    public String toStringKeySet()
    {
        if (this.internalDictionary.isEmpty())
            return "Empty dictionary\n";

        StringBuilder string = new StringBuilder();

        for (Map.Entry<String, Value> pair : this.internalDictionary.entrySet())
            string.append(pair.getKey()).append('\n');

        return string.toString();
    }
}
