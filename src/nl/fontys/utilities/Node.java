package nl.fontys.utilities;

public class Node implements Comparable<Node> {

    private Character character;
    private int frequency;

    private Node leftNode, rightNode;

    /**
     * Constructor for this class.
     * @param character The character of this node.
     * @param frequency The frequency of this node.
     */
    public Node(final Character character, final int frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    /**
     * Constructor for this class.
     * @param leftNode The (if applicable) left node of this node.
     *                 This parameter is allowed to be null.
     * @param rightNode The (if applicable) right node of this node.
     *                  This parameter is allowed to be null.
     */
    public Node(final Node leftNode, final Node rightNode) {
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    /**
     * @return Returns the character of this node.
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * @return Returns the frequency of this node.
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * @return Returns the left node of this node.
     */
    public Node getLeftNode() {
        return leftNode;
    }

    /**
     * @return Returns the right node of this node.
     */
    public Node getRightNode() {
        return rightNode;
    }

    /**
     * This is the compare method that will be used by collections.
     * @param o The node object that this current node object is being compared with.
     * @return Returns a integer-value representing the comparation value.
     */
    @Override
    public int compareTo(Node o) {
        return this.frequency < o.getFrequency() ? 1 : -1;
    }
}
