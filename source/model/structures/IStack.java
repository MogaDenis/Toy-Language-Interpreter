package source.model.structures;

import source.model.exceptions.EmptyStackException;

public interface IStack<T>
{
    public T pop() throws EmptyStackException;
    public void push(T element);
    public Boolean isEmpty();
    public String toString();
}
