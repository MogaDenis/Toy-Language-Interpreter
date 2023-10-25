package source.model.structures;

import java.util.Vector;

public class List<TElem> implements IList<TElem>
{
    private Vector<TElem> internalList;

    public List()
    {
        this.internalList = new Vector<TElem>();
    }

    @Override
    public void add(TElem element)
    {
        this.internalList.add(element);
    }

    @Override
    public void add(int index, TElem element)
    {
        this.internalList.add(index, element);
    }
    
    @Override
    public void set(int index, TElem element)
    {
        this.internalList.set(index, element);
    }
    
    @Override
    public Integer indexOf(TElem element)
    {
        return this.internalList.indexOf(element);
    }
    
    @Override
    public Integer lastIndexOf(TElem element)
    {
        return this.internalList.lastIndexOf(element);
    }
    
    @Override
    public Boolean remove(TElem element)
    {
        return this.internalList.remove(element);
    }
    
    @Override
    public TElem remove(int index)
    {
        return this.internalList.remove(index);
    }
    
    @Override
    public TElem get(int index)
    {
        return this.internalList.get(index);
    }
    
    @Override
    public Boolean contains(TElem element)
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

        for (TElem element : this.internalList)
            string += element.toString() + '\n';

        return string;
    }
}
