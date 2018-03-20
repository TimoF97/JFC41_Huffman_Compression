package nl.fontys.utilities;

public class Node implements Comparable<Node> {

    private Character character;
    private int frequency;

    private Node leftNode, rightNode;

    public Node(final Character character, final int frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    public Node(final Node leftNode, final Node rightNode) {
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    public Character getCharacter() {
        return character;
    }

    public int getFrequency() {
        return frequency;
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }

    @Override
    public int compareTo(Node o) {
        return this.frequency < o.getFrequency() ? 1 : -1;
    }
}
