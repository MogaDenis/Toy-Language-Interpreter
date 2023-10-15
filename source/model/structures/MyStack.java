package source.model.structures;

import java.util.Stack;

public class MyStack<TElem> implements IStack<TElem>
{
    private Stack<TElem> internalStack;

    public TElem pop()
    {
        return this.internalStack.pop();
    }

    public void push(TElem element)
    {
        this.internalStack.push(element);
    }

    public boolean isEmpty()
    {
        return this.internalStack.empty();
    }
}
