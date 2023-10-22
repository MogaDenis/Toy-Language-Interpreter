package source.model.structures;

import java.util.Vector;

import source.model.exceptions.EmptyStackException;

public class Stack<TElem> implements IStack<TElem>
{
    private Vector<TElem> internalStack;

    public Stack()
    {
        this.internalStack = new Vector<TElem>();
    }

    @Override
    public TElem pop() throws EmptyStackException
    {
        if (this.internalStack.size() == 0)
            throw new EmptyStackException("Empty stack!");

        TElem element = this.internalStack.get(this.internalStack.size() - 1);

        this.internalStack.remove(this.internalStack.size() - 1);

        return element;
    }

    @Override
    public void push(TElem element)
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

        String string = new String();

        for (int i = this.internalStack.size() - 1; i >= 0; i--)
            string += this.internalStack.get(i).toString();

        return string;
    }
}
