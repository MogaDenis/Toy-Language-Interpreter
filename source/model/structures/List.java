package source.model.structures;

import java.util.ArrayList;

public class List<TElem> implements IList<TElem>
{
    private ArrayList<TElem> internalList;

    public List()
    {
        this.internalList = new ArrayList<TElem>();
    }

    public void add(TElem element)
    {
        this.internalList.add(element);
    }

    public void add(int index, TElem element)
    {
        this.internalList.add(index, element);
    }
    
    public void set(int index, TElem element)
    {
        this.internalList.set(index, element);
    }
    
    public int indexOf(TElem element)
    {
        return this.internalList.indexOf(element);
    }
    
    public int lastIndexOf(TElem element)
    {
        return this.internalList.lastIndexOf(element);
    }
    
    public boolean remove(TElem element)
    {
        return this.internalList.remove(element);
    }
    
    public TElem remove(int index)
    {
        return this.internalList.remove(index);
    }
    
    public TElem get(int index)
    {
        return this.internalList.get(index);
    }
    
    public boolean contains(TElem element)
    {
        return this.internalList.contains(element);
    }
    
    public boolean isEmpty()
    {
        return this.internalList.isEmpty();
    }
    
    public int size()
    {
        return this.internalList.size();
    }
}
