package map.interpreter_gui.model.structures;

import java.util.List;
import java.util.Map;

import javafx.util.Pair;

public interface IBarrierTable
{
    Integer add(Integer value);
    void addProgramStateIDToList(Integer address, Integer ID);
    Pair<Integer, List<Integer>> get(Integer address);
    Boolean isUsed(Integer address);
    Map<Integer, Pair<Integer, String>> getContentString();
    void setContent(Map<Integer, Pair<Integer, List<Integer>>> newContent);
    List<Pair<Integer, List<Integer>>> getValues();
}
