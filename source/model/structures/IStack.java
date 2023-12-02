package source.model.structures;

import source.model.exceptions.EmptyStackException;

public interface IStack<T>
{
    T pop() throws EmptyStackException;
    void push(T element);
    Boolean isEmpty();
    String toString();
}
