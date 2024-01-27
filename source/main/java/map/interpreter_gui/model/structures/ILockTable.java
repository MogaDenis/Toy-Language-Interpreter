package map.interpreter_gui.model.structures;

import java.util.List;
import java.util.Map;

public interface ILockTable {
    Integer add();
    void update(Integer address, Integer programStateID);
    Integer get(Integer address);
    Boolean isUsed(Integer address);
    Map<Integer, Integer> getContent();
    void setContent(Map<Integer, Integer> newContent);
    List<Integer> getValues();
}