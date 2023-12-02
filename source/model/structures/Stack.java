package source.model.structures;

import java.util.Vector;

import source.model.exceptions.EmptyStackException;

public class Stack<T> implements IStack<T>
{
    private final Vector<T> internalStack;

    public Stack()
    {
        this.internalStack = new Vector<T>();
    }

    @Override
    public T pop() throws EmptyStackException
    {
        if (this.internalStack.isEmpty())
            throw new EmptyStackException("Empty stack!");

        T element = this.internalStack.get(this.internalStack.size() - 1);

        this.internalStack.remove(this.internalStack.size() - 1);

        return element;
    }

    @Override
    public void push(T element)
    {
        this.internalStack.add(element);
    }

    @Override
    public Boolean isEmpty()
    {
        return this.internalStack.isEmpty();
    }

    @Override
    public String toString()
    {
        if (this.internalStack.isEmpty())
            return "Empty stack\n";

        String string = "";

        for (int i = this.internalStack.size() - 1; i >= 0; i--)
            string += this.internalStack.get(i).toString() + "-----------------\n";

        return string;
    }
}
