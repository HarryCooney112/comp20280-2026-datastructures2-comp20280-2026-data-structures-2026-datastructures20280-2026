package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class CircularlyLinkedList<E> implements List<E> {

    private class Node<T> {
        private final T data;
        private Node<T> next;

        public Node(T e, Node<T> n) {
            data = e;
            next = n;
        }

        public T getData() {
            return data;
        }

        public void setNext(Node<T> n) {
            next = n;
        }

        public Node<T> getNext() {
            return next;
        }
    }

    private Node<E> tail = null;
    private int size = 0;

    public CircularlyLinkedList() {

    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public E first() {
        return this.tail.getNext().getData();
    }

    @Override
    public E get(int i) {
        if (i > size - 1) {
            throw new IllegalArgumentException("Index outside of list range");
        }
        var curr = tail.getNext();
        for (int j = 0; j < i; j++) {
            curr = curr.getNext();
        }
        // TODO
        return curr.getData();
    }

    /**
     * Inserts the given element at the specified index of the list, shifting all
     * subsequent elements in the list one position further to make room.
     *
     * @param i the index at which the new element should be stored
     * @param e the new element to be stored
     */
    @Override
    public void add(int i, E e) {
        if (i > size - 1) {
            throw new IllegalArgumentException("Index outside of list range");
        }

        Node<E> curr = tail.getNext();
        for (int j = 0; j < i - 1; j++) {
            curr = curr.getNext();
        }
        curr.next = new Node<E>(e, curr.getNext());
        size++;
    }

    @Override
    public E remove(int i) {
        if (i > size - 1) {
            throw new IllegalArgumentException("Index outside of list range");
        }
        Node<E> curr = tail.getNext();
        for (int j = 0; j < i - 1; j++) {
            curr = curr.getNext();
        }
        var n = curr.getNext();
        if (n == tail) {
            tail = tail.getNext();
        }
        curr.next = n.getNext();
        size--;
        return n.getData();
    }

    public void rotate() {
        tail = tail.getNext();
    }

    private class CircularlyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) tail;

        @Override
        public boolean hasNext() {
            return curr != tail;
        }

        @Override
        public E next() {
            E res = curr.data;
            curr = curr.next;
            return res;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new CircularlyLinkedListIterator<E>();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        Node<E> n = tail.getNext();
        tail.next = n.getNext();
        size--;
        return n.getData();
    }

    @Override
    public E removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        Node<E> curr = tail.getNext();
        E n = tail.getData();
        while (curr.getNext() != tail) {
            curr = curr.getNext();
        }
        tail = tail.getNext();
        curr.next = tail;
        size--;
        return n;
    }

    @Override
    public void addFirst(E e) {
        if (this.isEmpty()) {
            tail = new Node<>(e, null);
            tail.next = tail;
            size++;
            return;
        }
        tail.next = new Node<>(e, tail.getNext());
        size++;
    }

    @Override
    public void addLast(E e) {
        if (this.isEmpty()) {
            tail = new Node<>(e, null);
            tail.next = tail;
            size++;
            return;
        }
        tail.next = new Node<>(e, tail.getNext());
        tail = tail.getNext();
        size++;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = tail;
        do {
            curr = curr.next;
            sb.append(curr.data);
            if (curr != tail) {
                sb.append(", ");
            }
        } while (curr != tail);
        sb.append("]");
        return sb.toString();
    }


    public static void main(String[] args) {
        CircularlyLinkedList<Integer> ll = new CircularlyLinkedList<Integer>();
        for (int i = 10; i < 20; ++i) {
            ll.addLast(i);
        }
        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.removeLast();
        System.out.println(ll);

        ll.rotate();
        System.out.println(ll);

        ll.removeFirst();
        ll.rotate();
        System.out.println(ll);

        ll.removeLast();
        ll.rotate();
        System.out.println(ll);
    }
}
