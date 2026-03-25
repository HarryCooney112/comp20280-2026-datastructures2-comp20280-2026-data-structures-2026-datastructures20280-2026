package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class DoublyLinkedList<E> implements List<E> {

    private static class Node<E> {
        private final E data;
        private Node<E> next;
        private Node<E> prev;

        public Node(E e, Node<E> p, Node<E> n) {
            data = e;
            prev = p;
            next = n;
        }

        public E getData() {
            return data;
        }

        public Node<E> getNext() {
            return next;
        }

        public Node<E> getPrev() {
            return prev;
        }

    }

    private final Node<E> head;
    private final Node<E> tail;
    private final int size = 0;

    public DoublyLinkedList() {
        head = new Node<E>(null, null, null);
        tail = new Node<E>(null, head, null);
        head.next = tail;
    }

    private void addBetween(E e, Node<E> pred, Node<E> succ) {
        Node<E> n = new Node<>(e, pred, succ);
        pred.next = n;
        succ.prev = n;
    }

    @Override
    public int size() {
        int i = 0;
        //skips head node
        Node<E> curr = head.next;
        while (curr != null) {
            curr = curr.getNext();
            i++;
        }
        //return i-1 because it goes to tail and tail shouldn't be included in size
        return i-1;
    }

    @Override
    public boolean isEmpty() {
        // TODO
        return head.next == tail && tail.prev == head;
    }

    @Override
    public E get(int i) {
        Node<E> curr = head;
        for (int j = 0; j <= i; j++) {
            curr = curr.getNext();
        }
        // TODO
        return curr.getData();
    }

    @Override
    public void add(int i, E e) {
        Node<E> curr = head;
        for (int j = 0; j < i; j++) {
            curr = curr.next;
        }
        Node<E> nodeAfter = curr.next;
        Node<E> n = new Node<>(e, curr, nodeAfter);
        nodeAfter.prev = n;
        curr.next = n;
    }

    @Override
    public E remove(int i) {
        // TODO
        Node<E> curr = head;
        for (int j = 0; j <= i; j++) {
            curr = curr.getNext();
        }
        E e = curr.getData();
        curr.getPrev().next = curr.getNext();
        curr.getNext().prev = curr.getPrev();
        return e;
    }

    private class DoublyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) head.next;

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
        return new DoublyLinkedListIterator<E>();
    }

    private E remove(Node<E> n) {
        // TODO
        return null;
    }

    public E first() {
        if (isEmpty()) {
            return null;
        }
        return head.next.getData();
    }

    public E last() {
        // TODO
        if (this.isEmpty()) {
            return null;
        }
        return tail.getPrev().getData();
    }

    @Override
    public E removeFirst() {
        // TODO
        E e = head.getNext().getData();
        Node<E> next = head.getNext().getNext();
        head.next = next;
        next.prev = head;
        return e;
    }

    @Override
    public E removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        E e = tail.getPrev().getData();
        tail.prev = tail.getPrev().getPrev();
        tail.getPrev().next = tail;
        return e;
    }

    @Override
    public void addLast(E e) {
        Node<E> temp = tail.getPrev();
        Node<E> n = new Node<>(e, temp, tail);
        temp.next = n;
        tail.prev = n;
        // TODO
    }

    @Override
    public void addFirst(E e) {
        Node<E> temp = head.getNext();
        Node<E> n = new Node<>(e, head, temp);
        head.next = n;
        temp.prev = n;
        // TODO
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head.next;
        while (curr != tail) {
            sb.append(curr.data);
            curr = curr.next;
            if (curr != tail) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        DoublyLinkedList<Integer> ll = new DoublyLinkedList<Integer>();
        ll.addFirst(0);
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addLast(-1);
        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.removeLast();
        System.out.println(ll);

        for (Integer e : ll) {
            System.out.println("value: " + e);
        }
    }
}