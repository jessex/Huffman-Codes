package huffmancoding;

/**
 * An object representing a unique Huffman code, with data fields corresponding
 * to the character and its frequency and encoding.
 */
public class Code {

    private Character character;
    private int frequency;
    private String encoding;

    public Code(Character c, int freq, String code) {
        this.character = c;
        this.frequency = freq;
        this.encoding = code;
    }

    //////////////////////////////ACCESSORS/////////////////////////////////////

    public Character getCharacter() {
        return this.character;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public String getEncoding() {
        return this.encoding;
    }
    
}
