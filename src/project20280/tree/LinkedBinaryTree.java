package project20280.tree;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import project20280.interfaces.Position;

import java.util.ArrayList;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import java.io.*;

/**
 * Concrete implementation of a binary tree using a node-based, linked
 * structure.
 */
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

    static java.util.Random rnd = new java.util.Random();
    protected Node<E> root = null; // root of the tree
    // LinkedBinaryTree instance variables
    private int size = 0; // number of nodes in the tree

    /**
     * Constructs an empty binary tree.
     */
    public LinkedBinaryTree() {
    } // constructs an empty binary tree



    public static LinkedBinaryTree<Integer> makeRandom(int n) {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<>();
        bt.root = randomTree(null, 1, n);
        return bt;
    }

    // nonpublic utility

    public static <T extends Integer> Node<T> randomTree(Node<T> parent, Integer first, Integer last) {
        if (first > last) return null;
        else {
            Integer treeSize = last - first + 1;
            Integer leftCount = rnd.nextInt(treeSize);
            Integer rightCount = treeSize - leftCount - 1;
            Node<T> root = new Node<T>((T) ((Integer) (first + leftCount)), parent, null, null);
            root.setLeft(randomTree(root, first, first + leftCount - 1));
            root.setRight(randomTree(root, first + leftCount + 1, last));
            return root;
        }
    }

    private int maxDiam = 0;
    // accessor methods (not already implemented in AbstractBinaryTree)

    public static void main(String [] args) {
        LinkedBinaryTree<String> bt = new LinkedBinaryTree<>();
        LinkedBinaryTree <String> bt2 = new LinkedBinaryTree <>();
        String[] arr = { "A", "B", "C", "D", "E", null, "F", null, null, "G", "H", null, null, null, null };
        bt2.createLevelOrder(arr);
        System.out.println(bt2.toBinaryTreeString());
        String[] inorder =  {"A", "B", "C", "D", "E", "F", "F", "G", "H"};
        String[] preorder =  {"F", "B", "A", "D", "C", "E", "F", "G", "H"};
        //Integer[] inorder =  {2, 7, 5, 6, 4, 3, 1, 8, 9, 11, 12, 20, 10, 18, 13, 14, 15, 16};
        //Integer[] preorder = {18, 1, 2, 3, 4, 5, 7, 6, 9, 8, 10, 11, 12, 20, 16, 14, 13, 15};
        //Integer [] inorder = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22};
        //Integer [] preorder = {6, 5, 3, 2, 1, 0, 4, 17, 10, 9, 8, 7, 16, 14, 13, 12, 11, 15, 21, 20, 19, 18, 22};

        bt.construct(inorder, preorder);

        System.out.println(bt.toBinaryTreeString());
        System.out.println(bt.rootToLeafPaths());
        System.out.println(bt.diameter());
        bt.printLeaf();
        ArrayList<Long> out = inorderTest();
        System.out.println(out);



        /*
        ArrayList<Integer> heights = randomTreeHeights();
        try (FileWriter io = new FileWriter("output.txt")){
            heights.forEach( obj -> {
                try {
                    io.write(obj.toString());
                    io.write(", ");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
         */
    }


    /**
     * Factory function to create a new node storing element e.
     */
    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<E>(e, parent, left, right);
    }

    public static ArrayList<Integer> randomTreeHeights() {
        ArrayList<Integer> heights = new ArrayList<>();
        int temp;
        int size = 50;
        while (size <= 50000) {
            temp = 0;
            for (int i = 0; i < 100; i++) {
                LinkedBinaryTree<Integer> bt2 = makeRandom(size);
                temp += bt2.height();
            }
            temp = temp / 100;
            heights.add(temp);
            size+=50;
        }
        return heights;
    }

    public static ArrayList<Long> inorderTest() {
        ArrayList<Long> res = new ArrayList<>();
        ArrayList<LinkedBinaryTree> trees = new ArrayList<>();
        long start;
        long end;
        int n = 100;
        while (n < 100000) {
            trees.add(makeRandom(n));
            n += 100;
        }
        for (LinkedBinaryTree tree : trees) {
            start = System.nanoTime();
            tree.inorder();
            end = System.nanoTime();
            res.add(end - start);
        }
        return res;
    }
    /**
     * Verifies that a Position belongs to the appropriate class, and is not one
     * that has been previously removed. Note that our current implementation does
     * not actually verify that the position belongs to this particular list
     * instance.
     *
     * @param p a Position (that should belong to this tree)
     * @return the underlying Node instance for the position
     * @throws IllegalArgumentException if an invalid position is detected
     */
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node<E> node)) throw new IllegalArgumentException("Not valid position type");
        // safe cast
        if (node.getParent() == node) // our convention for defunct node
            throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    }

    /**
     * Returns the number of nodes in the tree.
     *
     * @return number of nodes in the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the root Position of the tree (or null if tree is empty).
     *
     * @return root Position of the tree (or null if tree is empty)
     */
    @Override
    public Position<E> root() {
        return root;
    }
    // update methods supported by this class

    /**
     * Returns the Position of p's parent (or null if p is root).
     *
     * @param p A valid Position within the tree
     * @return Position of p's parent (or null if p is root)
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    @Override
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getParent();
    }

    public void construct(E[] inorder, E[] preorder) {
        if (inorder.length != preorder.length) {
            throw new IllegalArgumentException("Inorder and preorder are not same length");
        }
        root = constructHelper(inorder, preorder, 0, 0, inorder.length - 1);
    }

    public Node<E> constructHelper(E[] inorder, E[] preorder ,int inoStart, int preOrdStart, int inoEnd) {
        if (preOrdStart > preorder.length - 1 || inoStart > inoEnd) { return null; }
        int inoIndex = 0;
        for (int i = inoStart; i <= inoEnd; i++) {
            if (inorder[i] == preorder[preOrdStart]) {
                inoIndex = i;
            }
        }
        return new Node<E>(preorder[preOrdStart], null,
               constructHelper(inorder, preorder, inoStart , preOrdStart + 1, inoIndex - 1),
               constructHelper(inorder, preorder, inoIndex + 1, preOrdStart + inoIndex - inoStart + 1, inoEnd));
    }
    /**
     * Returns the Position of p's left child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the left child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> left(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getLeft();
    }

    /**
     * Returns the Position of p's right child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the right child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> right(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getRight();
    }

    /**
     * Places element e at the root of an empty tree and returns its new Position.
     *
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalStateException if the tree is not empty
     */
    public Position<E> addRoot(E e) throws IllegalStateException {
        if (size != 0) {
            throw new IllegalStateException();
        }
        root = createNode(e, null, null, null);
        size++;
        return root;
    }

    public void insert(E e) {

    }

    // recursively add Nodes to binary tree in proper position
    private Node<E> addRecursive(Node<E> p, E e) {
        // TODO
        return null;
    }

    /**
     * Creates a new left child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the left of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p already has a left child
     */
    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
        if (left(p) != null) {
            throw new IllegalArgumentException("Already has left child");
        }
        var n = validate(p);
        var x = new Node<>(e, n, null, null);
        n.setLeft(x);
        size++;
        return x;
    }

    /**
     * Creates a new right child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the right of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p already has a right child
     */
    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
        if (right(p) != null) {
            throw new IllegalArgumentException("Already has left child");
        }
        var n = validate(p);
        var x = new Node<>(e, n, null, null);
        n.setRight(x);
        size++;
        return x;
    }

    /**
     * Replaces the element at Position p with element e and returns the replaced
     * element.
     *
     * @param p the relevant Position
     * @param e the new element
     * @return the replaced element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> n = validate(p);
        E tmp = n.getElement();
        n.setElement(e);
        return tmp;
    }

    /**
     * Attaches trees t1 and t2, respectively, as the left and right subtree of the
     * leaf Position p. As a side effect, t1 and t2 are set to empty trees.
     *
     * @param p  a leaf of the tree
     * @param t1 an independent tree whose structure becomes the left child of p
     * @param t2 an independent tree whose structure becomes the right child of p
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p is not a leaf
     */
    public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws IllegalArgumentException {
        Node<E> n = validate(p);
        if (left(p) != null || right(p) != null) {
            throw new IllegalArgumentException("P is not a leaf");
        }
        t1.root.setParent(n);
        t2.root.setParent(n);
        n.setLeft(validate(t1.root()));
        n.setRight(validate(t2.root()));
    }

    /**
     * Removes the node at Position p and replaces it with its child, if any.
     *
     * @param p the relevant Position
     * @return element that was removed
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p has two children.
     */
    public E remove(Position<E> p) throws IllegalArgumentException {
        validate(p);
        if (left(p) != null && right(p) != null) {
            throw new IllegalArgumentException("P has two children");
        }
        var x = validate(p);
        E curr = x.getElement();
        if (left(x) != null) {
            x = x.getLeft();
        } else {
            x = x.getLeft();
        }
        size--;
        return curr;
    }

    public String toString() {
        return positions().toString();
    }

    public void createLevelOrder(ArrayList<E> l) {
        root = createLevelOrderHelper(l, root, 0);
    }

    private Node<E> createLevelOrderHelper(java.util.ArrayList<E> l, Node<E> p, int i) {
        if (i < l.size()) {
            Node<E> n = createNode(l.get(i), p, null, null);
            n.left = createLevelOrderHelper(l, n.left, 2 * i + 1);
            n.right = createLevelOrderHelper(l, n.right, 2 * i + 2);
            ++size;
            return n;
        }
        return p;
    }

    public void createLevelOrder(E[] arr) {
        root = createLevelOrderHelper(arr, root, 0);
    }

    private Node<E> createLevelOrderHelper(E[] arr, Node<E> p, int i) {
        if (i < arr.length) {
            Node<E> n = createNode(arr[i], p, null, null);
            n.left = createLevelOrderHelper(arr, n.left, 2 * i + 1);
            n.right = createLevelOrderHelper(arr, n.right, 2 * i + 2);
            ++size;
            return n;
        }
        return p;
    }
    public ArrayList<ArrayList<E>> rootToLeafPaths() {
        ArrayList<ArrayList<E>> snapshot = new ArrayList<>();
        ArrayList<E> path = new ArrayList<>();

        rootToLeafHelper(root, path, snapshot);
        return snapshot;
    }
    public void rootToLeafHelper(Node<E> curr, ArrayList<E> path, ArrayList<ArrayList<E>> snapshot) {
        if (curr == null) {
            return;
        }
        path.add(curr.getElement());
        if (curr.getLeft() == null && curr.getRight() == null) {
            snapshot.add(new ArrayList<>(path));
        } else {
            rootToLeafHelper(curr.getLeft(), path, snapshot);
            rootToLeafHelper(curr.getRight(), path, snapshot);
        }
        path.removeLast();
    }
    public int diameter() {
        maxDiam = 0;
        diameterRecur(root);
        return 1 + maxDiam;
    }
    public int diameterRecur(Node<E> n) {
        if (n == null) {
            return 0;
        }
        int lHeight = diameterRecur(n.getLeft());
        int rHeight = diameterRecur(n.getRight());
        if (maxDiam < lHeight + rHeight) {
            maxDiam = lHeight + rHeight;
        }
        return 1 + Math.max(lHeight, rHeight);
    }

    public String toBinaryTreeString() {
        BinaryTreePrinter<E> btp = new BinaryTreePrinter<>(this);
        return btp.print();
    }

    /**
     * Nested static class for a binary tree node.
     */

    public void printLeaf() {
        ArrayList<E> leafs = new ArrayList<>();
        printLeafHelper(root, leafs);
        System.out.println(leafs);
    }
    public void printLeafHelper(Node<E> curr, ArrayList<E> leafs) {
        if (curr == null) {
            return;
        }
        if (curr.left == null && curr.right == null) {
            leafs.add(curr.getElement());
            return;
        }
        printLeafHelper(curr.getLeft(), leafs);
        printLeafHelper(curr.getRight(), leafs);
    }

    public static class Node<E> implements Position<E> {
        private E element;
        private Node<E> left, right, parent;

        public Node(E e, Node<E> p, Node<E> l, Node<E> r) {
            element = e;
            left = l;
            right = r;
            parent = p;
        }

        // accessor
        public E getElement() {
            return element;
        }

        // modifiers
        public void setElement(E e) {
            element = e;
        }

        public Node<E> getLeft() {
            return left;
        }

        public void setLeft(Node<E> n) {
            left = n;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> n) {
            right = n;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> n) {
            parent = n;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (element == null) {
                sb.append("\u29B0");
            } else {
                sb.append(element);
            }
            return sb.toString();
        }
    }

}