package map.interpreter_gui.model.structures.count_semaphore_table;

import java.util.List;
import java.util.Map;

import javafx.util.Pair;

public interface ICountSemaphoreTable
{
    Integer add(Integer value);
    void addProgramStateIDToList(Integer address, Integer ID);
    void removeProgramStateIDFromList(Integer address, Integer ID);
    Pair<Integer, List<Integer>> get(Integer address);
    Integer getValue(Integer address);
    List<Integer> getListOfProgramStateIDs(Integer address);
    Boolean isUsed(Integer address);
    Map<Integer, Pair<Integer, String>> getContentString();
    void setContent(Map<Integer, Pair<Integer, List<Integer>>> newContent);
    List<Pair<Integer, List<Integer>>> getValues();
}
