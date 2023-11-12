package source.model.structures;

import java.util.List;
import java.util.Map;

import source.model.values.Value;

public interface IHeap 
{
    public Integer allocate(Value value);
    public void update(Integer address, Value value);
    public Value get(Integer address);
    public Boolean isUsed(Integer address);
    public Map<Integer, Value> getContent();
    public void setContent(Map<Integer, Value> newContent);
    public List<Value> getValues();
}
