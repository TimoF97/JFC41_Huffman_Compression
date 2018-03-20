package nl.fontys.algorithms;

import nl.fontys.utilities.Constants;
import nl.fontys.utilities.Node;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.BitSet;
import java.util.HashMap;
import java.util.PriorityQueue;

public class HuffmanCompression {

    private PriorityQueue<Node> nodePriorityQueue;
    private HashMap<Character, String> filledTreeHashMap;

    public HuffmanCompression() {
        filledTreeHashMap = new HashMap<>();
    }

    /**
     * This method will encode the given text by the use of the HuffmanCompression.
     *
     * @param text The text that should be encoded.
     *             This parameter is not allowed to be null.
     */
    public void encode(final String text) {
        if (text == null) throw new IllegalArgumentException("The text is not allowed to be null.");

        nodePriorityQueue = getPrioritizedNodes(text);
        generateBitCodeForNode(generateTree(nodePriorityQueue), "");
        saveBitSetAndTree(filledTreeHashMap, getBitsetFromEncodedString(getEncodedString(text)), Constants.ENCODED_TEXT_FILE_PATH);
    }

    /**
     * This method will return a PriorityQueue containing objects of the Node class from the given text.
     * The nodes within the queue will be ordered by the their corresponding frequency in the given text.
     *
     * @param text The text that the nodes should be calculated for.
     *             This parameter is not allowed to be null.
     * @return Returns an instance of the PriorityQueue class, containing objects of the Node class.
     * The nodes will be ordered by their frequency.
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
     *
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
     * <p>
     * The 'tree' that will be filled with the characters and their generated bitcodes will be a HashMap.
     * This will be a HashMap because a HashMap is generally the most efficient implementation of the Map class.
     *
     * @param node           The node on which the generation of the bitcode should be applied on.
     *                       This parameter is not allowed to be null.
     * @param currentBitcode The already generated part of the bitcode for the current node.
     *                       This parameter is not allowed to be null.
     *                       This parameter is indeed allowed to be an empty String as there won't be a currentBitcode when starting the entire generation process.
     */
    private void generateBitCodeForNode(final Node node, final String currentBitcode) {
        if (node == null) throw new IllegalArgumentException("The node is not allowed to be null.");
        else if (currentBitcode == null)
            throw new IllegalArgumentException("The currentBitcode is not allowed to be null. If there is no currentBitcode available, use an empty String as parameter.");

        /* The current node has a right node as well. Let's generate the bitcode for it's right node. */
        if (node.getRightNode() != null) {
            /* We need to make sure that we're giving the current generated bitcode to the generation of this right node.
               We should make sure to do this as the right node's bitcode will be an extension on the bitcode of this current node.

               We've chosen that going to the right in the 'tree' will value a 1 whereas going to the left will value a 0.
               We're going to the right in the tree so we're extending the currentBitcode by adding a '1' to it. */
            generateBitCodeForNode(node.getRightNode(), currentBitcode + "1");
        }
        /* The current node has a left node as well. Let's generate the bitcode for it's left node. */
        if (node.getLeftNode() != null) {
            /* We need to make sure that we're giving the current generated bitcode to the generation of this left node.
               We should make sure to do this as the left node's bitcode will be an extension on the bitcode of this current node.

               We've chosen that going to the right in the 'tree' will value a 1 whereas going to the left will value a 0.
               We're going to the left in the tree so we're extending the currentBitcode by adding a '0' to it. */
            generateBitCodeForNode(node.getLeftNode(), currentBitcode + "0");
        }
        /* The current node has no left nor right nodes. This means that we're at the end of this branch.
           Being at the end of the branch, means that our current generated currentBitcode is the bitcode for our current node within the tree.
           We should therefore add the character that this node represents to our filledTreeHashMap with it's according generated bitcode. */
        if (node.getLeftNode() == null && node.getRightNode() == null) {
            System.out.println(node.getCharacter() + " | " + currentBitcode);
            filledTreeHashMap.put(node.getCharacter(), currentBitcode);
        }
    }

    /**
     * This method will encode a given String by the use of the generated tree.
     *
     * @param text The text that should be encoded.
     *             This parameter is not allowed to be null.
     * @return Returns the encoded String. This value is a String.
     */
    private String getEncodedString(final String text) {
        if (text == null) throw new IllegalArgumentException("The text is not allowed to be null.");

        String encodedString = "";

        for (final char character : text.toCharArray()) {
            encodedString += filledTreeHashMap.get(character);
        }
        return encodedString;
    }

    /**
     * This method will transform a given encodedString into an object of the BitSet class.
     *
     * @param encodedString The encodedString that should be transformed into a BitSet.
     *                      This parameter is not allowed to be null.
     * @return Returns an object of the BitSet class containing the bits of the given encodedString.
     */
    private BitSet getBitsetFromEncodedString(final String encodedString) {
        if (encodedString == null) throw new IllegalArgumentException("The encodedString is not allowed to be null.");

        final BitSet bitSet = new BitSet(encodedString.length());
        int indexCounter = 0;

        for (final Character character : encodedString.toCharArray()) {
            /* A bit within the BitSet has a default value of 0.
               To transform our encodedString to a BitSet we'll have to loop through all the 'characters', which are basically 0's and 1's within our String object.
               Because the default value for a bit within a BitSet is 0 we should only make sure to turn the value of a bit to 1 whenever the 'character' within our encodedString equals "1". */
            if (character.equals('1')) {
                bitSet.set(indexCounter);
            }
            indexCounter++;
        }
        return bitSet;
    }

    /**
     * This method will save the generated tree and BitSet to a file for the given filepath.
     * @param tree The 'tree' that should be saved.
     *             The 'tree' is an object of the HashMap class using HashMap<Character, String>.
     *             This parameter is not allowed to be null.
     * @param bitSet The BitSet that should be saved. The BitSet should be the encoded text.
     *               This parameter is not allowed to be null.
     * @param filepath The filepath that the file will have to be saved in.
     *                 This parameter is not allowed to be null nor an empty String.
     */
    private void saveBitSetAndTree(final HashMap<Character, String> tree, final BitSet bitSet, final String filepath) {
        if (tree == null) throw new IllegalArgumentException("The tree is not allowed to be null.");
        else if (bitSet == null) throw new IllegalArgumentException("The bitSet is not allowed to be null.");
        else if (filepath == null || filepath.isEmpty()) throw new IllegalArgumentException("The filepath is not allowed to be null nor an empty String.");

        try (final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filepath))) {
            objectOutputStream.writeObject(tree);
            objectOutputStream.writeObject(bitSet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
