package io.github.marciocg.jtlv;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class TLVparseTest {

	@Test
	void testParseSimpleString() {
		List<TLV> tlv = TLV.parse("9f010B1122334455667788990011");
		
		assertEquals(tlv.get(0).getTag(), "9f01", "Invalid Tag name");
		assertEquals(tlv.get(0).getLength(), "0b", "Invalid Tag length");
		assertEquals(tlv.get(0).getValue(), "1122334455667788990011", "Invalid Value data");
	}

	@Test
	void testParseSimpleStringWithNullStart() {
		List<TLV> tlv = TLV.parse("0001021234");
		// the zero x'00' is removed from tag name
		assertEquals(tlv.get(0).getTag(), "01", "Invalid Tag name");
		assertEquals(tlv.get(0).getLength(), "02", "Invalid Tag length");
		assertEquals(tlv.get(0).getValue(), "1234", "Invalid Value data");
	}
	
	@Test
	void testParseStringWithZeroLengthTagStart() {
		List<TLV> tlv = TLV.parse("0102123403009f260411223344300211222500");
		// the zero x'00' is removed from tag name
		assertEquals(tlv.get(0).getTag(), "01", "Invalid Tag name");
		assertEquals(tlv.get(0).getLength(), "02", "Invalid Tag length");
		assertEquals(tlv.get(0).getValue(), "1234", "Invalid Value data");
		assertEquals(tlv.get(1).getTag(), "03", "Invalid Tag name");
		assertEquals(tlv.get(1).getLength(), "00", "Invalid Tag length");
		assertEquals(tlv.get(1).getValue(), null, "Invalid Value data");
		assertEquals(tlv.get(2).getTag(), "9f26", "Invalid Tag name");
		assertEquals(tlv.get(2).getLength(), "04", "Invalid Tag length");
		assertEquals(tlv.get(2).getValue(), "11223344", "Invalid Value data");
		assertEquals(tlv.get(3).getTag(), "30", "Invalid Tag name");
		assertEquals(tlv.get(3).getLength(), "02", "Invalid Tag length");
		assertEquals(tlv.get(3).getValue(), "1122", "Invalid Value data");
		assertEquals(tlv.get(4).getTag(), "25", "Invalid Tag name");
		assertEquals(tlv.get(4).getLength(), "00", "Invalid Tag length");
		assertEquals(tlv.get(4).getValue(), null, "Invalid Value data");
		assertTrue(tlv.size() == 5);

	}
	
	@Test
	void testParseStringWithLongTagName() {
		List<TLV> tlv = TLV.parse("9F8A26020909");
		
		assertEquals(tlv.get(0).getTag(), "9f8a26", "Invalid Tag name");
		assertEquals(tlv.get(0).getLength(), "02", "Invalid Tag length");
		assertEquals(tlv.get(0).getValue(), "0909", "Invalid Value data");
	}

	@Test
	void testParseStringWithMultipleTLV() {
		List<TLV> tlv = TLV.parse("010212340203AEBABA9f03031122339F520A11223344556677889900");
		
		assertEquals(tlv.get(0).getTag(), "01", "Invalid Tag name");
		assertEquals(tlv.get(0).getLength(), "02", "Invalid Tag length");
		assertEquals(tlv.get(0).getValue(), "1234", "Invalid Value data");
		assertEquals(tlv.get(1).getTag(), "02", "Invalid Tag name");
		assertEquals(tlv.get(1).getLength(), "03", "Invalid Tag length");
		assertEquals(tlv.get(1).getValue(), "aebaba", "Invalid Value data");
		assertEquals(tlv.get(2).getTag(), "9f03", "Invalid Tag name");
		assertEquals(tlv.get(2).getLength(), "03", "Invalid Tag length");
		assertEquals(tlv.get(2).getValue(), "112233", "Invalid Value data");
		assertEquals(tlv.get(3).getTag(), "9f52", "Invalid Tag name");
		assertEquals(tlv.get(3).getLength(), "0a", "Invalid Tag length");
		assertEquals(tlv.get(3).getValue(), "11223344556677889900", "Invalid Value data");
		assertTrue(tlv.size() == 4);
	}

	@Test
	void testParseStringWithTwoBytesLength() {
		String str_head = "9f2081c8";
		String str_val = "7111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111731111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111113"; 
		StringBuilder sb = new StringBuilder();
		sb.append(str_head);
		sb.append(str_val);
		List<TLV> tlv = TLV.parse(sb.toString());
		assertEquals(tlv.get(0).getTag(), "9f20", "Invalid Tag name");
		assertEquals(tlv.get(0).getLength(), "81c8", "Invalid Tag length");
		assertEquals(tlv.get(0).getValue(), str_val, "Invalid Value data");

		
		// assertThrows(IllegalArgumentException.class, 
		// () -> {TLV.parse("9f2081c871111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111117");});
	}

	@Test
	void testParseStringWithThreeBytesLength() {
		String str_head = "9f30820190";
		String str_val = "facacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacefacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacace"; 
		StringBuilder sb = new StringBuilder();
		sb.append(str_head);
		sb.append(str_val);
		List<TLV> tlv = TLV.parse(sb.toString());
		assertEquals(tlv.get(0).getTag(), "9f30", "Invalid Tag name");
		assertEquals(tlv.get(0).getLength(), "820190", "Invalid Tag length");
		assertEquals(tlv.get(0).getValue(), str_val, "Invalid Value data");		
	}

	@Test
	void testParseStringWithFourBytesLength() {
		assertThrows(IllegalArgumentException.class, 
		//() -> {List<TLV> tlv = TLV.parse("9f40820190facacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacace");});
		() -> {TLV.parse("9f4084019044facacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacace");});
		
	}
	
}
