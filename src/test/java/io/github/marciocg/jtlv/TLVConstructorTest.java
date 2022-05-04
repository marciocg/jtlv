package io.github.marciocg.jtlv;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TLVConstructorTest {
    @Test
    void testSimpleConstructor() {
        TLV tlv = new TLV("01", "02", "1234");

        assertEquals("01", tlv.getTag(), "Invalid Tag name");
        assertEquals("02", tlv.getLength(), "Invalid Length");
        assertEquals("1234", tlv.getValue(), "Invalid Value data");
    }

    @Test
    void testConstructorWithFourBytesLength() {
        var value = new StringBuilder();
        for (int i = 0; i < 4097; i++) {
             value.append("BE"); 
        }

		assertThrows(IllegalArgumentException.class, 
        () -> { new TLV("04", value.toString());});
      
    }

    @Test
    void testConstructorWithThreeBytesLength() {
        var value = new StringBuilder();
        for (int i = 0; i < 257; i++) {
             value.append("CA"); 
        }
        TLV tlv = new TLV("03", "820101", value.toString());

        assertEquals("03", tlv.getTag(), "Invalid Tag name");
        assertEquals("820101", tlv.getLength(), "Invalid Length");
        assertEquals(value.toString(), tlv.getValue(), "Invalid Value data");

    }

    @Test
    void testConstructorWithTwoBytesLength() {
        var value = new StringBuilder();
        for (int i = 0; i < 129; i++) {
             value.append("DA"); 
        }
        TLV tlv = new TLV("02", "8181", value.toString());

        assertEquals("02", tlv.getTag(), "Invalid Tag name");
        assertEquals("8181", tlv.getLength(), "Invalid Length");
        assertEquals(value.toString(), tlv.getValue(), "Invalid Value data");

    }

    @Test
    void testConstructorWithOddLength() {
        var value = "FAFAFAE";

		assertThrows(IllegalArgumentException.class, 
        () -> { new TLV("07", value.toString());});

    }

}
