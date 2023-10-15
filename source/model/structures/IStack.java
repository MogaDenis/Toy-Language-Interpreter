package source.model.structures;

public interface IStack<TElem>
{
    public TElem pop();
    public void push(TElem element);
    public boolean isEmpty();
}
