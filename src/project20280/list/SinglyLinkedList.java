package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class SinglyLinkedList<E> implements List<E> {

    private static class Node<E> {

        private final E element;            // reference to the element stored at this node

        /**
         * A reference to the subsequent node in the list
         */
        private Node<E> next;         // reference to the subsequent node in the list

        /**
         * Creates a node with the given element and next node.
         *
         * @param e the element to be stored
         * @param n reference to a node that should follow the new node
         */
        public Node(E e, Node<E> n) {
            this.element = e;
            this.next = n;
        }

        // Accessor methods

        /**
         * Returns the element stored at the node.
         *
         * @return the element stored at the node
         */
        public E getElement() {
            return this.element;
        }

        /**
         * Returns the node that follows this one (or null if no such node).
         *
         * @return the following node
         */
        public Node<E> getNext() {
            return this.next;
        }

        // Modifier methods

        /**
         * Sets the node's next reference to point to Node n.
         *
         * @param n the node that should follow this one
         */
        public void setNext(Node<E> n) {
            this.next = n;
        }
    } //----------- end of nested Node class -----------

    /**
     * The head node of the list
     */
    private Node<E> head = null;               // head node of the list (or null if empty)


    /**
     * Number of nodes in the list
     */
    private int size = 0;                      // number of nodes in the list

    public SinglyLinkedList() {
    }              // constructs an initially empty list

    //@Override
    public int size() {
        Node<E> curr = head;
        int i = 0;
        while (curr != null) {
            curr = curr.getNext();
            i++;
        }
        return i;
    }

    //@Override
    public boolean isEmpty() {
        return this.head == null;
    }

    @Override
    public E get(int position) {
        Node<E> curr = head;
        for (int i = 0; i < position; i++) {
            curr = curr.getNext();
        }
        return curr.getElement();
    }

    @Override
    public void add(int position, E e) {
        Node<E> curr = head;
        for (int i = 0; i < position - 1; i++) {
            curr = curr.getNext();
        }
        Node<E> n = new Node<>(e, curr.next);
        curr.next = n;
        size++;
        //Can also be written as follows:
        //curr.next = new Node<>(e, curr.next);
    }


    @Override
    public void addFirst(E e) {
        head = new Node<>(e, head);
        size++;
    }

    @Override
    public void addLast(E e) {
        Node<E> curr = head;
        if (curr == null) {
            this.addFirst(e);
            return;
        }
        while (curr.getNext() != null) {
            curr = curr.getNext();
        }
        curr.next = new Node<>(e, null);
        size++;
    }

    @Override
    public E remove(int position) {
        Node<E> curr = head;
        for (int i = 0; i < position - 1; i++) {
            curr = curr.getNext();
        }
        E e = curr.getNext().getElement();
        curr.next = curr.getNext().getNext();
        size--;
        return e;
    }

    @Override
    public E removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        E e = head.getElement();
        head = head.next;
        size--;
        return e;
    }

    @Override
    public E removeLast() {
        E e;
        if (this.size() <= 1) {
            return this.removeFirst();
        } else {
            Node<E> curr = head;
            while (curr.next.next != null) {
                curr = curr.getNext();
            }
            e = curr.getNext().getElement();
            curr.next = null;
        }
        size--;
        return e;

    }

    @Override
    public Iterator<E> iterator() {
        return new SinglyLinkedListIterator<E>();
    }

    private class SinglyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) head;

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public E next() {
            E res = curr.getElement();
            curr = curr.next;
            return res;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head;
        while (curr != null) {
            sb.append(curr.getElement());
            if (curr.getNext() != null)
                sb.append(", ");
            curr = curr.getNext();
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        SinglyLinkedList<Integer> ll = new SinglyLinkedList<Integer>();
        //System.out.println("ll " + ll + " isEmpty: " + ll.isEmpty());
        //LinkedList<Integer> ll = new LinkedList<Integer>();

        ll.addFirst(1);
        ll.addFirst(0);
        ll.addFirst(0);
        ll.addFirst(0);
        System.out.println(ll.size());
        //ll.removeFirst();
        //System.out.println("I accept your apology");
        //ll.add(3, 2);

    }
}
