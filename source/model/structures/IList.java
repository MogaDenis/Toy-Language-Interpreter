package source.model.structures;

public interface IList<TElem>
{
    public void add(TElem element);
    public void add(int index, TElem element);
    public void set(int index, TElem element);
    public int indexOf(TElem element);
    public int lastIndexOf(TElem element);
    public boolean remove(TElem element);
    public TElem remove(int index);  
    public TElem get(int index);
    public boolean contains(TElem element);
    public boolean isEmpty();
    public int size();
}
