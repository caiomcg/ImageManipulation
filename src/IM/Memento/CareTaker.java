package IM.Memento;

import com.sun.istack.internal.Nullable;

import java.awt.image.BufferedImage;
import java.util.EmptyStackException;
import java.util.Stack;

public class CareTaker {
    private Stack<Memento> mementos = new Stack<>();

    public void addMemento(Memento memento) {
        this.mementos.push(memento);
    }

    @Nullable
    public Memento getMemento() {
        try {
            return this.mementos.pop();
        } catch (EmptyStackException e) {
            System.err.println("No more mementos");
        }
        return null;
    }
}
