package source.model.structures;

public interface IList<T>
{
    public void add(T element);
    public void add(int index, T element);
    public void set(int index, T element);
    public Integer indexOf(T element);
    public Integer lastIndexOf(T element);
    public Boolean remove(T element);
    public T remove(int index);  
    public T get(int index);
    public Boolean contains(T element);
    public Boolean isEmpty();
    public Integer size();
}
