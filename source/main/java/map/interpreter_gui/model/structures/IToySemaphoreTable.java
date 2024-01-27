package map.interpreter_gui.model.structures;

import java.util.List;
import java.util.Map;

import javafx.util.Pair;

public interface IToySemaphoreTable
{
    Integer add(Integer firstValue, Integer secondValue);
    void addProgramStateIDToList(Integer address, Integer ID);
    void removeProgramStateIDFromList(Integer address, Integer ID);
    Pair<Integer, Pair<List<Integer>, Integer>> get(Integer address);
    Integer getFirstValue(Integer address);
    Integer getSecondValue(Integer address);
    List<Integer> getListOfProgramStateIDs(Integer address);
    Boolean isUsed(Integer address);
    Map<Integer, Pair<Integer, Pair<String, Integer>>> getContentString();
    void setContent(Map<Integer, Pair<Integer, Pair<List<Integer>, Integer>>> newContent);
    List<Pair<Integer, Pair<List<Integer>, Integer>>> getValues();
}
