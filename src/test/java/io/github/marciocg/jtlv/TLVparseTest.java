package io.github.marciocg.jtlv;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class TLVparseTest {

	@Test
	void testParseSimpleString() {
		List<TLV> tlv = TLV.parse("01021234");
		
		assertEquals(tlv.get(0).getTag(), "01", "Invalid Tag name");
		assertEquals(tlv.get(0).getLength(), "02", "Invalid Tag length");
		assertEquals(tlv.get(0).getValue(), "1234", "Invalid Value data");
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
		
		assertThrows(IllegalArgumentException.class, 
		() -> {TLV.parse("9f2081c871111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111117");});
	}

	@Test
	void testParseStringWithThreeBytesLength() {
		assertThrows(IllegalArgumentException.class, 
		//() -> {List<TLV> tlv = TLV.parse("9f40820190facacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacace");});
		() -> {TLV.parse("9f40820190facacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacacace");});
		
	}
	
}
