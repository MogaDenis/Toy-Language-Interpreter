package source.model.structures;

public interface IStack<TElem>
{
    public TElem pop() throws Exception;
    public void push(TElem element);
    public Boolean isEmpty();
    public String toString();
}
