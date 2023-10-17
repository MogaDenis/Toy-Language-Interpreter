package source.model.structures;

import source.model.exceptions.EmptyStackException;

public interface IStack<TElem>
{
    public TElem pop() throws EmptyStackException;
    public void push(TElem element);
    public Boolean isEmpty();
    public String toString();
}
