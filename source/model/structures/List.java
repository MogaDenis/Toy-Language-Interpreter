package source.model.structures;

import java.util.Vector;

public class List<T> implements IList<T>
{
    private final Vector<T> internalList;

    public List()
    {
        this.internalList = new Vector<T>();
    }

    @Override
    public void add(T element)
    {
        this.internalList.add(element);
    }

    @Override
    public void add(int index, T element)
    {
        this.internalList.add(index, element);
    }
    
    @Override
    public void set(int index, T element)
    {
        this.internalList.set(index, element);
    }
    
    @Override
    public Integer indexOf(T element)
    {
        return this.internalList.indexOf(element);
    }
    
    @Override
    public Integer lastIndexOf(T element)
    {
        return this.internalList.lastIndexOf(element);
    }
    
    @Override
    public Boolean remove(T element)
    {
        return this.internalList.remove(element);
    }
    
    @Override
    public T remove(int index)
    {
        return this.internalList.remove(index);
    }
    
    @Override
    public T get(int index)
    {
        return this.internalList.get(index);
    }
    
    @Override
    public Boolean contains(T element)
    {
        return this.internalList.contains(element);
    }
    
    @Override
    public Boolean isEmpty()
    {
        return this.internalList.isEmpty();
    }
    
    @Override
    public Integer size()
    {
        return this.internalList.size();
    }

    @Override
    public String toString()
    {
        if (this.internalList.isEmpty())
            return "Empty list\n";

        String string = "";

        for (T element : this.internalList)
            string += element.toString() + '\n';

        return string;
    }
}
