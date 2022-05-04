package io.github.marciocg.jtlv;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HexFormat;
import java.util.List;

import org.junit.jupiter.api.Test;

class TLVformatTest {

    @Test
    void testFormatSimpleString() {
        List<TLV> tlvList = TLV.parse("01021234");
        byte[] tlvFormat = TLV.format(tlvList);

        assertArrayEquals(tlvFormat, HexFormat.of().parseHex("01021234"), "Invalid TLV format");
    }

    @Test
    void testFormatSimpleStringWithNullStart() {
        List<TLV> tlvList = TLV.parse("0001021234");
        byte[] tlvFormat = TLV.format(tlvList);

        // the zero x'00' is removed from tag name
        assertArrayEquals(tlvFormat, HexFormat.of().parseHex("01021234"), "Invalid TLV format");
        // the zero x'00' is appended to the next tag name
        //assertArrayEquals(tlvFormat, HexFormat.of().parseHex("0001021234"), "Invalid TLV format");
    }
	
    @Test
	void testFormatStringWithZeroLengthTagStart() {
		List<TLV> tlvList = TLV.parse("0102123403009f260411223344300211222500");
        byte[] tlvFormat = TLV.format(tlvList);
		
        assertArrayEquals(tlvFormat, HexFormat.of().parseHex("0102123403009f260411223344300211222500"), "Invalid TLV format");
	}
	
    @Test
    void testFormatStringWithLongTagName() {
        List<TLV> tlvList = TLV.parse("9F8A26020909");
        byte[] tlvFormat = TLV.format(tlvList);

        assertArrayEquals(tlvFormat, HexFormat.of().parseHex("9F8A26020909"), "Invalid TLV format");

    }

    @Test
    void testFormatStringWithMultipleTLV() {
        List<TLV> tlvList = TLV.parse("010212340203AEBABA9f03031122339F520A11223344556677889900");
        byte[] tlvFormat = TLV.format(tlvList);

        assertArrayEquals(tlvFormat, HexFormat.of().parseHex("010212340203AEBABA9f03031122339F520A11223344556677889900"),
               "Invalid TLV format");

    }

    @Test
    void testFormatStringWithTwoBytesLength() {
        String str_head = "9f2081c8";
		String str_val = "7111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111731111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111113"; 
		StringBuilder sb = new StringBuilder();
		sb.append(str_head);
		sb.append(str_val);
        List<TLV> tlvList = TLV.parse(sb.toString());
        byte[] tlvFormat = TLV.format(tlvList);

        assertArrayEquals(tlvFormat, HexFormat.of().parseHex(sb.toString()),
               "Invalid TLV format");

    }

    @Test
    void testFormatStringWithThreeBytesLength() {
		String str_head = "9f30820190";
		String str_val = "facacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacefacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacace"; 
		StringBuilder sb = new StringBuilder();
		sb.append(str_head);
		sb.append(str_val);
        List<TLV> tlvList = TLV.parse(sb.toString());
        byte[] tlvFormat = TLV.format(tlvList);

        assertArrayEquals(tlvFormat, HexFormat.of().parseHex(sb.toString()),
               "Invalid TLV format");

    }

    @Test
    void testFormatStringWithMultipleTLVlikeEMVbit55FieldAndHavingRepeatedTagNames() {
		String str_val = "9F3303E02080950500000000009F3704A5CC06CF9F100706010A03A0B0009F26080562599501ED00039F3602010C82025C009F3704B5CC06CF9F3704C5CC06CF9F3704D5CC06CF9F02060000000010005F2A0208409F0306000000000000"; 
        List<TLV> tlvList = TLV.parse(str_val);
        byte[] tlvFormat = TLV.format(tlvList);

        assertArrayEquals(tlvFormat, HexFormat.of().parseHex(str_val),
               "Invalid TLV format");

    }

}
