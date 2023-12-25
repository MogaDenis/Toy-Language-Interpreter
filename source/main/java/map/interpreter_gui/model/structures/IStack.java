package map.interpreter_gui.model.structures;

import map.interpreter_gui.model.exceptions.EmptyStackException;

import java.util.List;

public interface IStack<T>
{
    List<T> getContent();

    T pop() throws EmptyStackException;
    void push(T element);
    Boolean isEmpty();
    String toString();
}
