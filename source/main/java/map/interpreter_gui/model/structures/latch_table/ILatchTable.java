package map.interpreter_gui.model.structures.latch_table;

import java.util.List;
import java.util.Map;

public interface ILatchTable {
    Integer add(Integer value);
    void update(Integer address, Integer value);
    Integer get(Integer address);
    Boolean isUsed(Integer address);
    Map<Integer, Integer> getContent();
    void setContent(Map<Integer, Integer> newContent);
    List<Integer> getValues();
}