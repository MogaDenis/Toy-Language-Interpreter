package map.interpreter_gui.model.structures;

public interface IList<T>
{
    void add(T element);
    void add(int index, T element);
    void set(int index, T element);
    Integer indexOf(T element);
    Integer lastIndexOf(T element);
    Boolean remove(T element);
    T remove(int index);
    T get(int index);
    Boolean contains(T element);
    Boolean isEmpty();
    Integer size();
}
