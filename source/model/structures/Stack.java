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

    public TElem pop() throws EmptyStackException
    {
        if (this.internalStack.size() == 0)
            throw new EmptyStackException("Empty stack!");

        TElem element = this.internalStack.get(this.internalStack.size() - 1);

        this.internalStack.remove(this.internalStack.size() - 1);

        return element;
    }

    public void push(TElem element)
    {
        this.internalStack.add(element);
    }

    public Boolean isEmpty()
    {
        return this.internalStack.isEmpty();
    }

    public String toString()
    {
        String string = new String();

        for (TElem element : this.internalStack)
            string += element.toString() + '\n';

        return string;
    }
}
