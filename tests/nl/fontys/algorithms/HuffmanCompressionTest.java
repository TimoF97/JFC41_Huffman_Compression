package nl.fontys.algorithms;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class HuffmanCompressionTest {

    private static final String LOREM_IPSUM = "This is a test!";
    private final HuffmanCompression huffmanCompression;

    public HuffmanCompressionTest() {
        huffmanCompression = new HuffmanCompression();
    }

    @Test(expected = IllegalArgumentException.class)
    public void encode_Null_Test() throws Exception {
        huffmanCompression.encode(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void encode_Empty_Text_Test() throws Exception {
        huffmanCompression.encode("");
    }

    @Test
    public void encode_Correct_Test() throws Exception {
        final HashMap<Character, String> hashMap = huffmanCompression.encode(LOREM_IPSUM);

        Assert.assertEquals("11", hashMap.get('t'));
        Assert.assertEquals("0000", hashMap.get('!'));
    }

    @Test
    public void decode_Correct_Test() throws Exception {
        huffmanCompression.encode(LOREM_IPSUM);
        Assert.assertEquals(LOREM_IPSUM, huffmanCompression.decode());
    }
}
