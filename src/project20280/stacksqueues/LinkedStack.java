package project20280.stacksqueues;

import project20280.interfaces.Stack;
import project20280.list.DoublyLinkedList;

public class LinkedStack<E> implements Stack<E> {

    private DoublyLinkedList<E> ll;

    public static void main(String[] args) {
        LinkedStack<Integer> s = new LinkedStack<>();
        for (int i = 0; i < 10; ++i)
            s.push(i);
            System.out.println(s);
        for (int i = 0; i < 10; ++i) {
            s.pop();
            System.out.println(s);
        }
    }

    public LinkedStack() {
        this.ll = new DoublyLinkedList<E>();
    }

    @Override
    public int size() {
        return ll.size();
    }

    @Override
    public boolean isEmpty() {
        return ll.isEmpty();
    }

    @Override
    public void push(E e) {
        ll.addFirst(e);
    }

    @Override
    public E top() {
        return ll.first();
    }

    @Override
    public E pop() {
        return ll.removeFirst();
    }

    public String toString() {
        return ll.toString();
    }
}
