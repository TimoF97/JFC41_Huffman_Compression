package nl.fontys.algorithms;

import nl.fontys.utilities.Constants;
import org.junit.Before;
import org.junit.Test;

public class HuffmanCompressionPerformanceTest {

    private static String TEN_THOUSAND_WORDS;
    private static String ONE_MILLION_WORDS;

    private HuffmanCompression huffmanCompression;

    @Before
    public void setUp() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 100; i++) {
            stringBuilder.append(Constants.LOREM_IPSUM);
        }
        TEN_THOUSAND_WORDS = stringBuilder.toString();

        stringBuilder = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            stringBuilder.append(Constants.LOREM_IPSUM);
        }
        ONE_MILLION_WORDS = stringBuilder.toString();

        huffmanCompression = new HuffmanCompression();
    }


    @Test
    public void encodeTenThousandTest() {
        long totalTime = 0;

        for (int i = 0; i < 50; i++) {
            final long startTime = System.nanoTime();
            huffmanCompression.encode(TEN_THOUSAND_WORDS);
            totalTime += (System.nanoTime() - startTime);
        }
        System.out.println("encode 10_000: " + totalTime / 50L / 1000L + " microseconds");
    }

    @Test
    public void encodeOneMillionTest() {
        long totalTime = 0;

        for (int i = 0; i < 50; i++) {
            final long startTime = System.nanoTime();
            huffmanCompression.encode(ONE_MILLION_WORDS);
            totalTime += (System.nanoTime() - startTime);
        }
        System.out.println("encode 10_000_000: " + totalTime / 50L / 1000L + " microseconds");
    }

    @Test
    public void decodeTenThousandTest() {
        long totalTime = 0;

        for (int i = 0; i < 50; i++) {
            final long startTime = System.nanoTime();
            huffmanCompression.encode(TEN_THOUSAND_WORDS);
            totalTime += (System.nanoTime() - startTime);
        }
        System.out.println("decode 10_000: " + totalTime / 50L / 1000L + " microseconds");
    }

    @Test
    public void decodeOneMillionTest() {
        long totalTime = 0;

        for (int i = 0; i < 50; i++) {
            final long startTime = System.nanoTime();
            huffmanCompression.encode(ONE_MILLION_WORDS);
            totalTime += (System.nanoTime() - startTime);
        }
        System.out.println("decode 10_000_000: " + totalTime / 50L / 1000L + " microseconds");
    }
}
