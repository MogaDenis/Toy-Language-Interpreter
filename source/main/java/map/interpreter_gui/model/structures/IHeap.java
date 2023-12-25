package map.interpreter_gui.model.structures;

import java.util.List;
import java.util.Map;

import map.interpreter_gui.model.values.Value;

public interface IHeap 
{
    Integer allocate(Value value);
    void update(Integer address, Value value);
    Value get(Integer address);
    Boolean isUsed(Integer address);
    Map<Integer, Value> getContent();
    void setContent(Map<Integer, Value> newContent);
    List<Value> getValues();
}
