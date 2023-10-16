package source.model.structures;

public interface IList<TElem>
{
    public void add(TElem element);
    public void add(int index, TElem element);
    public void set(int index, TElem element);
    public Integer indexOf(TElem element);
    public Integer lastIndexOf(TElem element);
    public Boolean remove(TElem element);
    public TElem remove(int index);  
    public TElem get(int index);
    public Boolean contains(TElem element);
    public Boolean isEmpty();
    public Integer size();
}
