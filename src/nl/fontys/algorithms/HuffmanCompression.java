package nl.fontys.algorithms;

import nl.fontys.utilities.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanCompression {

    private PriorityQueue<Node> nodePriorityQueue;
    private HashMap<Character, String> filledTreeHashMap;

    public HuffmanCompression() {
        filledTreeHashMap = new HashMap<>();
    }

    /**
     * This method will encode the given text by the use of the HuffmanCompression.
     * @param text The text that should be encoded.
     *             This parameter is not allowed to be null.
     */
    public void encode(final String text) {
        if (text == null) throw new IllegalArgumentException("The text is not allowed to be null.");

        nodePriorityQueue = getPrioritizedNodes(text);
        generateBitcodeForNode(generateTree(nodePriorityQueue), "");

        for (Map.Entry<Character, String> entry : filledTreeHashMap.entrySet()) {
            Character key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + " " + value);
        }
    }

    /**
     * This method will return a PriorityQueue containing objects of the Node class from the given text.
     * The nodes within the queue will be ordered by the their corresponding frequency in the given text.
     * @param text The text that the nodes should be calculated for.
     *             This parameter is not allowed to be null.
     * @return Returns an instance of the PriorityQueue class, containing objects of the Node class.
     *         The nodes will be ordered by their frequency.
     */
    private PriorityQueue<Node> getPrioritizedNodes(final String text) {
        if (text == null) throw new IllegalArgumentException("The text is not allowed to be null.");

        final HashMap<Character, Integer> mapOfCharacters = new HashMap<>();
        final PriorityQueue<Node> queue = new PriorityQueue<>();

        for (final Character character : text.toCharArray()) {
            if (mapOfCharacters.get(character) != null) { // The map contains the character. Let's make sure we'll increment it's current frequency count.
                mapOfCharacters.put(character, mapOfCharacters.get(character).intValue() + 1);
            } else {
                mapOfCharacters.put(character, 1);
            }
        }

        // Adding the nodes to the PriorityQueue to make sure they're prioritized in the order of their frequency.
        mapOfCharacters.forEach((k, v) -> queue.add(new Node(k, v)));
        return queue;
    }

    /**
     * This method generates a decision-tree for the given nodes.
     * @param nodes The nodes that the decision-tree should be generated for.
     *              This parameter is an instance of the PriorityQueue class, filled with objects of the Node class.
     *              This parameter is not allowed to be null.
     * @return Returns an object of the Node class. This object is the first node in the tree that has just been generated.
     */
    private Node generateTree(final PriorityQueue<Node> nodes) {
        if (nodes == null) throw new IllegalArgumentException("The priorityQueue of nodes is not allowed to be null.");

        /* This poll method retrieves and removes the head of the queue.
           By using this method we're able to generate all the left and right nodes for each node,
           which will result in us being able to generate and build the 'tree'. */
        while (nodes.size() > 1) {
            nodes.add(new Node(nodes.poll(), nodes.poll()));
        }
        return nodes.peek(); // This method will returns the first element without removing it.
    }

    /**
     * This method will generate all the bitcodes for the nodes within the priorityqueue of nodes.
     * The generated bitcodes will be inserted in a HashMap called filledTreeHashMap, which is a representation of all the nodes with their according bitcodes.
     * This HashMap will be the actual 'tree'.
     *
     * The 'tree' that will be filled with the characters and their generated bitcodes will be a HashMap.
     * This will be a HashMap because a HashMap is generally the most efficient implementation of the Map class.
     *
     * @param node The node on which the generation of the bitcode should be applied on.
     *             This parameter is not allowed to be null.
     * @param currentBitcode The already generated part of the bitcode for the current node.
     *                       This parameter is not allowed to be null.
     *                       This parameter is indeed allowed to be an empty String as there won't be a currentBitcode when starting the entire generation process.
     */
    private void generateBitcodeForNode(final Node node, final String currentBitcode) {
        if (node == null) throw new IllegalArgumentException("The node is not allowed to be null.");
        else if (currentBitcode == null) throw new IllegalArgumentException("The currentBitcode is not allowed to be null. If there is no currentBitcode available, use an empty String as parameter.");

        /* The current node has a right node as well. Let's generate the bitcode for it's right node. */
        if (node.getRightNode() != null) {
            /* We need to make sure that we're giving the current generated bitcode to the generation of this right node.
               We should make sure to do this as the right node's bitcode will be an extension on the bitcode of this current node.

               We've chosen that going to the right in the 'tree' will value a 1 whereas going to the left will value a 0.
               We're going to the right in the tree so we're extending the currentBitcode by adding a '1' to it. */
            generateBitcodeForNode(node.getRightNode(), currentBitcode + "1");
        }
        /* The current node has a left node as well. Let's generate the bitcode for it's left node. */
        if (node.getLeftNode() != null) {
            /* We need to make sure that we're giving the current generated bitcode to the generation of this left node.
               We should make sure to do this as the left node's bitcode will be an extension on the bitcode of this current node.

               We've chosen that going to the right in the 'tree' will value a 1 whereas going to the left will value a 0.
               We're going to the left in the tree so we're extending the currentBitcode by adding a '0' to it. */
            generateBitcodeForNode(node.getLeftNode(), currentBitcode + "0");
        }
        /* The current node has no left nor right nodes. This means that we're at the end of this branch.
           Being at the end of the branch, means that our current generated currentBitcode is the bitcode for our current node within the tree.
           We should therefore add the character that this node represents to our filledTreeHashMap with it's according generated bitcode. */
        if (node.getLeftNode() == null && node.getRightNode() == null) {
            filledTreeHashMap.put(node.getCharacter(), currentBitcode);
        }
    }
}
