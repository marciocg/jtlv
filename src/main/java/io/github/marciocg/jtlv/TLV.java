package io.github.marciocg.jtlv;
/* 
Copyright 2022 Márcio Conceição Goulart

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. 
*/

import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;

/**
 * A simple TLV Data Structure that handles tag, length and value as Strings.
 * @author marciocg
 * @version v0.1.1
 * @since 01/05/2022
 */
public final class TLV {

	private final String tag;
	private final String length;
	private final String value;

	/**
	 * Parameters are Strings as Hexadecimals
	 * @param tag Tag name as Hexadecimal {@link String}
	 * @param length Tag Length as Hexadecimal {@link String}
	 * @param value Value as Hexadecimal {@link String}
	 */
	public TLV(String tag, String length, String value) {
		this.tag = tag;
		this.length = length;
		this.value = value;
	}
/**
 * 
 * @return tag
 */
	public String getTag() {
		return tag;
	}
/**
 * 
 * @return length
 */
	public String getLength() {
		return length;
	}

	/**
	 * 
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "TLV [tag=" + tag + ", length=" + length + ", value=" + value + "]";
	}	
	/**
	 * Concatenates the tag, length and value into a primitive TLV as {@link String}
	 * @return TLV {@link String}
	 */
	public String toStringPrimitive() {
		return tag + length + value;
	}
/**
 * Parses an unstructured {@link String} of BER-TLV
 * @param input A {@link String} that may contain one or more BER-TLV data
 * @return a {@link List} of {@link TLV}
 */
	public static List<TLV> parse(String input) {
		byte[] input_byte = HexFormat.of().parseHex(input);
		return parse(input_byte);
	}
/**
 * Parses an unstructured byte array of BER-TLV
 * @param input A byte[] array that may contain one or more BER-TLV data
 * @return a {@link List} of {@link TLV}
 */
	public static List<TLV> parse(byte[] input) {
		int i = 0;
		int lk = 0;
		int lk_end = 0;

		ArrayList<TLV> list_tlv = new ArrayList<TLV>();
	
		while (i < input.length) {

			if (input[i] == 0) { // valor x'00' deve ser ignorado e pulado, conforme definição
				i++;
				continue;
			}
			
			StringBuilder tag = new StringBuilder();
			StringBuilder len = new StringBuilder();
			StringBuilder val = new StringBuilder();

			if ((31 & input[i]) == 31) { // b5-b1 = 1
				tag.append(HexFormat.of().toHexDigits(input[i]));
				i++;
				for (lk = i; ((128 & input[lk]) == (128)); ++lk) { // b8 = 1
					tag.append(HexFormat.of().toHexDigits(input[lk]));
					i++;
				}
				tag.append(HexFormat.of().toHexDigits(input[i]));
				i++;
			} else {
				tag.append(HexFormat.of().toHexDigits(input[i]));
				i++;
			}

			if ((128 & input[i]) == (128)) {
				// se b8 = 1 os valor dos demais 7bits indicam o tamanho, em bytes,  do campo tamanho do TLV
				throw new IllegalArgumentException("Long lengths not implemented, must be less than 128.");
				// i++;
				// for (lk = i; ((128 & input[lk]) == (128)); lk++) { // b8 = 1
				// 	len.append(HexFormat.of().toHexDigits(input[lk]));
				// 	i++;
				// }
			} else {
				len.append(HexFormat.of().toHexDigits(input[i]));
				i++;

			}

			lk_end = i + HexFormat.fromHexDigits(len);
			val.append(HexFormat.of().toHexDigits(input[i]));
			i++;
			for (lk = i; lk < lk_end; lk++) {
				val.append(HexFormat.of().toHexDigits(input[lk]));
				i++;
			}

			list_tlv.add(new TLV(tag.toString(), len.toString(), val.toString()));
			// aqui o i está no próximo byte, pronto para recomeçar o loop
		}

		return list_tlv;

	}
/**
 * Formats a {@link List} of {@link TLV} into a byte array of BER-TLV
 * @param tlvList a {@link List} of {@link TLV}
 * @return A byte[] array
 */
	public static byte[] format(List<TLV> tlvList) {
		
		StringBuilder tlvStringBuilder = new StringBuilder();

		for (TLV tlv : tlvList) {
			tlvStringBuilder.append(tlv.toStringPrimitive());
		}
		byte[] b = HexFormat.of().parseHex(tlvStringBuilder.toString());
		return b;
	}

}
