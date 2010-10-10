package huffmancoding;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Implements the Huffman tree, constructed from some provided character String.
 */
public class HuffmanTree {

    //Actual character String to encode
    private String string;
    //Each char in the String to encode
    private ArrayList<Character> characters;
    //Frequency of each char: key is character, element is integer
    private HashMap frequencies;            
    //Resulting code of each char: key is character, element is encoding
    private HashMap encodings;
    //Binary tree for implementation of Huffman tree
    private BinaryTree binaryTree;
    //List of characters with frequencies and encodings
    private ArrayList<Code> codeList;

    /**
     * Constructor using a given String. 
     */
    public HuffmanTree(String str) {
        this.string = str;
        characters = new ArrayList<Character>();
        frequencies = new HashMap();
        encodings = new HashMap();

        frequencies = generateFrequencies(str); //Determine frequencies 
        buildBinaryTree(); //Constructs the complete Binary tree
        generateEncodings(binaryTree, ""); //Generates the Huffman codes
    }

    //////////////////////////////ACCESSORS/////////////////////////////////////

    /**
     * Returns all characters in the String.
     * @return characters
     */
    public ArrayList<Character> getCharacters() { 
        return this.characters;
    }
    /**
     * Returns the frequency for the given character.
     * @param c - Character
     * @return character's frequency
     */
    public int getCharacterFrequency(Character c) {
        Integer freq = (Integer) frequencies.get(c);
        if (freq == null) return 0;
        else return freq.intValue();
    }
    /**
     * Returns the Huffman code for the given character.
     * @param c - Character
     * @return character's encoding
     */
    public String getCharacterEncoding(Character c) {
        return (String) encodings.get(c);
    }


    /////////////////////////////////HELPERS////////////////////////////////////

    /**
     * Analyzes String to determine frequency of each character in the String.
     * Generates a HashMap with characters as keys and frequencies as elements.
     * @param str - String
     * @return HashMap
     */
    private HashMap generateFrequencies(String str) {
        HashMap freq = new HashMap();
        //Iterate through String, char by char
        for (int i=0; i < str.length(); i++) {
            Character c = new Character(str.charAt(i));
            int frequency = 1;
            //Character already in HashMap, so iterate its frequency
            if (freq.containsKey(c))
                frequency = (((Integer) freq.get(c)).intValue()) + 1;
            //Character not in HashMap, add to char list, keep freq as 1
            else 
                characters.add(c);

            freq.put(c, new Integer(frequency)); //put overwrites key if prev.
        }
        return freq;
    }

    /**
     * Builds an ascending priority queue from the characters of the String.
     * @return priority queue
     */
    private PriorityQueue buildPriorityQueue() {
        PriorityQueue pq = new PriorityQueue();
        
        int count = characters.size();
        //For each unique character, make an object out of its frequency
        for (int i=0; i<count; i++) {
            Character c = (Character) characters.get(i);
            int index = ((Integer) frequencies.get(c)).intValue();
            //Insert frequency into the queue with subtree with char as root
            pq.insert(index, new BinaryTree(new Node(String.valueOf(c)),
                    null, null));
        }
        
        return pq;
    }

    /**
     * Constructs the complete Binary tree corresponding to the characters of
     * the String. Begins by building a priority queue and then combining the
     * two lowest frequency elements at a time until the queue is emptied.
     */
    private void buildBinaryTree() {
        PriorityQueue pq = buildPriorityQueue();

        while (pq.getSize() > 1) {
            //Get the two lowest frequency elements and their indices
            int min = pq.getMinimumFrequency();
            BinaryTree minTree = (BinaryTree) pq.removeMinimum();
            int nextMin = pq.getMinimumFrequency();
            BinaryTree nextMinTree = (BinaryTree) pq.removeMinimum();

            /* Concatenate the two elements and add the sum to the queue
             * as we work our way back up the tree toward the root.
             */
            pq.insert(min + nextMin, new BinaryTree(
                    new Node(String.valueOf(min + nextMin)),
                    minTree, nextMinTree));
        }
        
        /* Set the full Binary tree of the Huffman tree,
         * If the placeholder is gone, the tree was empty so return as such
         */
        binaryTree = pq.isEmpty() ?
            new BinaryTree() : (BinaryTree) pq.removeMinimum();
    }
    
    /**
     * Generates encodings for each character in the Huffman tree. Call with
     * the fully constructed Binary tree and the encodings HashMap will be
     * populated with the codes by character.
     * @param tree
     * @param currentCode
     */
    private void generateEncodings(BinaryTree tree, String currentCode) {
        //If there is no left child, then we are ready to put the current code
        if (tree.getLeftChild().isEmpty()) {
            //Get the character of the current node and update its encoding
            Character c = new Character(tree.getRoot().getElement().charAt(0));
            encodings.put(c, currentCode);
        }
        //Is a left child (and possibly right) so recurse to left/both children
        else {
            //Add a 0 to the coding if its the left child, a 1 if its the right
            generateEncodings(tree.getLeftChild(), currentCode + "0");
            generateEncodings(tree.getRightChild(), currentCode + "1");
        }
    }

    /**
     * Returns a list of Code objects, with each element in the list
     * corresponding to a character and its frequency and encoding.
     * @return code list
     */
    public ArrayList<Code> getCodeList() {
        ArrayList<Code> codes = new ArrayList<Code>();

        int count = characters.size();
        //Iterate through characters, unifying with frequencies and encodings
        for (int i=0; i<count; i++) {
            Character c = (Character) characters.get(i);
            //Create new Code object
            codes.add(new Code(c, ((Integer) frequencies.get(c)).intValue(),
                    (String) encodings.get(c)));
        }
        return codes;
    }

    /**
     * Returns the original String completely encoded using the previously
     * generated Huffman codings. Pass in the String to be encoded and encodings
     * are retrieved from the encoding HashMap.
     * @param str - String
     * @return encoded String
     */
    public String getEncodedString() {
        String encoded = "";
        int count = string.length();
        for (int i=0; i<count; i++) 
            encoded += getCharacterEncoding(string.charAt(i)) + " ";
        return encoded.substring(0, encoded.length()-1); //Drop trailing space
    }

}
