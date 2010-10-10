package huffmancoding;

/**
 * Bare bones BinaryTree, suitable only for this Huffman implementation. Does
 * not feature all standard methods of a typical Binary Tree.
 */
public class BinaryTree {

    //If this tree is empty or not
    private boolean ifEmpty;
    //Root node of this tree
    private Node rootNode;
    
    /* Left and right child sub-trees;
     * Uses entire Binary trees for children (instead of just nodes) for ease of
     * recursive traversal in our encoding algorithm
     */
    private BinaryTree leftChild, rightChild;

    /**
     * Empty constructor to build a Binary tree with no elements.
     */
    public BinaryTree() {
        this.ifEmpty = true;
    }

    /**
     * Constructor for a Binary tree using a given root Node and left and right
     * children (trees).
     * @param root - Node
     * @param leftChild - BinaryTree
     * @param rightChild - BinaryTree
     */
    public BinaryTree(Node root, BinaryTree leftChild, BinaryTree rightChild)
    throws NullPointerException {
        this.ifEmpty = false;
        this.rootNode = root;
        this.leftChild = (leftChild == null) ? new BinaryTree() : leftChild;
        this.rightChild = (rightChild == null) ? new BinaryTree() : rightChild;
    }


    //////////////////////////////ACCESSORS/////////////////////////////////////

    /**
     * Returns if this tree is empty.
     * @return ifEmpty
     */
    public boolean isEmpty() {
        return this.ifEmpty;
    }

    /**
     * Returns the root of this tree.
     * @return root node
     */
    public Node getRoot() {
        if (ifEmpty) return null;
        else return this.rootNode;
    }

    /**
     * Returns the left sub-tree of this tree, including all children of said
     * child.
     * @return left child tree
     */
    public BinaryTree getLeftChild() {
        if (ifEmpty) return null;
        else return leftChild;
    }

    /**
     * Returns the right sub-tree of this tree, including all children of said
     * child.
     * @return right child tree
     */
    public BinaryTree getRightChild() {
        if (ifEmpty) return null;
        else return rightChild;
    }
}
