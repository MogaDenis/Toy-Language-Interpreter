package map.interpreter_gui.model.structures;

import map.interpreter_gui.model.exceptions.EmptyStackException;
import map.interpreter_gui.model.statements.IStatement;

import java.util.ArrayList;
import java.util.List;

public class ExecutionStack implements IStack<IStatement>
{
    private final List<IStatement> internalStack;

    public ExecutionStack()
    {
        this.internalStack = new ArrayList<>();
    }

    @Override
    public List<IStatement> getContent() {
        return this.internalStack.reversed();
    }

    @Override
    public IStatement pop() throws EmptyStackException
    {
        if (this.internalStack.isEmpty())
            throw new EmptyStackException("Empty stack!");

        IStatement element = this.internalStack.getLast();

        this.internalStack.removeLast();

        return element;
    }

    @Override
    public void push(IStatement element)
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

        StringBuilder string = new StringBuilder();

        for (int i = this.internalStack.size() - 1; i >= 0; i--)
            string.append(this.internalStack.get(i).toString()).append("-----------------\n");

        return string.toString();
    }
}
