package source.model.structures;

public interface IStack<TElem>
{
    public TElem pop() throws Exception;
    public void push(TElem element);
    public boolean isEmpty();
    public String toString();
}
