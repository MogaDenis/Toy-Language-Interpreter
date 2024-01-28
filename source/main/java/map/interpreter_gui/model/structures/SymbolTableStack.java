package map.interpreter_gui.model.structures;

import map.interpreter_gui.model.exceptions.EmptyStackException;

import java.util.ArrayList;
import java.util.List;

public class SymbolTableStack implements IStack<SymbolTable> {
    private final List<SymbolTable> internalStack;

    public SymbolTableStack() {
        this.internalStack = new ArrayList<>();
    }

    @Override
    public List<SymbolTable> getContent() {
        return this.internalStack.reversed();
    }

    public SymbolTable top() {
        return this.internalStack.getLast();
    }

    @Override
    public SymbolTable pop() throws EmptyStackException {
        if (this.internalStack.isEmpty())
            throw new EmptyStackException("Empty stack!");

        SymbolTable element = this.internalStack.getLast();

        this.internalStack.removeLast();

        return element;
    }

    @Override
    public void push(SymbolTable element) {
        this.internalStack.add(element);
    }

    @Override
    public Boolean isEmpty() {
        return this.internalStack.isEmpty();
    }

    public SymbolTableStack deepCopy() {
        SymbolTableStack symbolTableStackCopy = new SymbolTableStack();

        for (SymbolTable symbolTable : this.getContent()) {
            symbolTableStackCopy.push(symbolTable.deepCopy());
        }

        return symbolTableStackCopy;
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