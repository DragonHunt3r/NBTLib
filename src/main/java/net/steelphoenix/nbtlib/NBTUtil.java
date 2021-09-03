package net.steelphoenix.nbtlib;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.steelphoenix.nbtlib.tag.NBTTagByte;
import net.steelphoenix.nbtlib.tag.NBTTagByteArray;
import net.steelphoenix.nbtlib.tag.NBTTagCompound;
import net.steelphoenix.nbtlib.tag.NBTTagDouble;
import net.steelphoenix.nbtlib.tag.NBTTagFloat;
import net.steelphoenix.nbtlib.tag.NBTTagInt;
import net.steelphoenix.nbtlib.tag.NBTTagIntArray;
import net.steelphoenix.nbtlib.tag.NBTTagList;
import net.steelphoenix.nbtlib.tag.NBTTagLong;
import net.steelphoenix.nbtlib.tag.NBTTagLongArray;
import net.steelphoenix.nbtlib.tag.NBTTagShort;
import net.steelphoenix.nbtlib.tag.NBTTagString;

/**
 * Utilities for NBT data.
 *
 * @author SteelPhoenix
 */
public class NBTUtil {

	private NBTUtil() {
		// Nothing
	}

	/**
	 * Read a compound tag from a data input.
	 *
	 * @param input Target input.
	 * @return the read compound tag.
	 * @throws IOException if an I/O error occurs.
	 */
	public static NBTTagCompound read(DataInput input) throws IOException {
		return read(input, NBTSizeLimiter.UNLIMITED);
	}

	/**
	 * Read a compound tag from a data input.
	 *
	 * @param input Target input.
	 * @param limiter Tag size limiter.
	 * @return the read compound tag.
	 * @throws IOException if an I/O error occurs.
	 */
	public static NBTTagCompound read(DataInput input, NBTSizeLimiter limiter) throws IOException {
		// Preconditions
		if (input == null) {
			throw new NullPointerException("Input cannot be null");
		}
		if (limiter == null) {
			throw new NullPointerException("Limiter cannot be null");
		}

		byte id = input.readByte();
		NBTTagType type = NBTTagType.fromId(id);

		if (type == null) {
			throw new MalformedNBTException("Unknown root type: " + id);
		}
		if (type != NBTTagType.COMPOUND) {
			throw new MalformedNBTException("Root is not of type " + NBTTagType.COMPOUND.getName());
		}

		// Skip root tag name
		input.readUTF();

		return (NBTTagCompound) type.read(input, 0, limiter);
	}

	/**
	 * Write a compound tag to a data output.
	 *
	 * @param output Target output.
	 * @param tag Target tag.
	 * @throws IOException if an I/O error occurs.
	 */
	public static void write(DataOutput output, NBTTagCompound tag) throws IOException {
		write(output, tag, "");
	}

	/**
	 * Write a named compound tag to a data output.
	 *
	 * @param output Target output.
	 * @param tag Target tag.
	 * @param name Target name.
	 * @throws IOException if an I/O error occurs.
	 */
	public static void write(DataOutput output, NBTTagCompound tag, String name) throws IOException {
		// Preconditoins
		if (output == null) {
			throw new NullPointerException("Output cannot be null");
		}
		if (tag == null) {
			throw new NullPointerException("Tag cannot be null");
		}
		if (name == null) {
			throw new NullPointerException("Name cannot be null");
		}

		output.writeByte(tag.getTypeId());
		output.writeUTF(name);
		tag.write(output);
	}

	/**
	 * Parse a stringified NBT tag.
	 * Note that the top level needs to be a compound tag.
	 *
	 * @param text Target text.
	 * @return the parsed tag.
	 */
	public static NBTTagCompound parse(String text) {
		// Preconditions
		if (text == null) {
			throw new NullPointerException("Text cannot be null");
		}

		Lexer lexer = new Lexer(text);
		NBTTagCompound tag = readCompound(lexer);
		if (lexer.canRead()) {
			throw new MalformedNBTException("Unexpected trailing data at index " + lexer.getCursor() + " for: " + lexer.getFullString());
		}
		return tag;
	}

	/**
	 * Read a compound tag.
	 *
	 * @param lexer Target lexer.
	 * @return the read tag.
	 */
	private static NBTTagCompound readCompound(Lexer lexer) {
		// Preconditions
		if (lexer == null) {
			throw new NullPointerException("Lexer cannot be null");
		}

		Map<String, INBTTag<?>> map = new LinkedHashMap<>();
		lexer.expect('{');
		while (lexer.canRead() && lexer.peek() != '}') {
			int i = lexer.getCursor();
			String key;
			if (!lexer.canRead() || (key = lexer.readString()).isEmpty()) {
				lexer.setCursor(i);
				throw new MalformedNBTException("Expected key at index " + lexer.getCursor() + " for: " + lexer.getFullString());
			}
			lexer.expect(':');
			map.put(key, readValue(lexer));

			// If there are more elements
			if (lexer.canRead() && lexer.peek() == ',') {
				lexer.read();
			}
			else {
				break;
			}
		}
		lexer.expect('}');
		NBTTagCompound tag = new NBTTagCompound();
		tag.setValue0(map);
		return tag;
	}

	/**
	 * Read a list tag.
	 *
	 * @param lexer Target lexer.
	 * @return the read tag.
	 */
	private static INBTTag<?> readList(Lexer lexer) {
		// Preconditions
		if (lexer == null) {
			throw new NullPointerException("Lexer cannot be null");
		}

		// List start
		lexer.expect('[');

		ICollectionNBTTag<?> tag;
		NBTTagType type;

		// [<type>;
		if (lexer.canRead(2) && (lexer.peek() == '\"' || lexer.peek() == '\'') && lexer.peek(1) == ';') {
			int i = lexer.getCursor();
			char c = lexer.read();

			// Skip semicolon
			lexer.read();

			// Create tag
			switch (c) {
				case 'B':
					tag = new NBTTagByteArray();
					break;
				case 'I':
					tag = new NBTTagIntArray();
					break;
				case 'L':
					tag = new NBTTagLongArray();
					break;
				default:
					lexer.setCursor(i);
					throw new MalformedNBTException("Unknown array type '" + c + "' at index " + lexer.getCursor() + " for: " + lexer.getFullString());
			}

			type = tag.getElementType();
		}

		// Unspecific array
		else {
			tag = new NBTTagList();
			type = null;
		}

		List<INBTTag<?>> list = new ArrayList<>();

		// No more data
		if (!lexer.canRead()) {
			throw new MalformedNBTException("Expected value at index " + lexer.getCursor() + " for: " + lexer.getFullString());
		}

		while (lexer.canRead() && lexer.peek() != ']') {
			int i = lexer.getCursor();
			INBTTag<?> element = readValue(lexer);

			// We say the list type is the first type encountered
			if (type == null) {
				type = element.getType();
			}

			// Incorrect type
			else if (element.getType() != type) {
				lexer.setCursor(i);
				throw new MalformedNBTException("Expected tag of type " + type.getName() + " for: " + lexer.getFullString());
			}

			list.add(element);

			// If there are more elements
			if (lexer.canRead() && lexer.peek() == ',') {
				lexer.read();
			}
			else {
				break;
			}
		}

		// List end
		lexer.expect(']');

		// Set type for unspecific lists
		if (tag instanceof NBTTagList) {
			((NBTTagList) tag).setElementType(type == null ? NBTTagType.END : type);
		}

		((AbstractCollectionNBTTag<?>) tag).setValue0(list);
		return tag;
	}

	/**
	 * Read a tag.
	 *
	 * @param lexer Target lexer.
	 * @return the value.
	 */
	private static INBTTag<?> readValue(Lexer lexer) {
		// Preconditions
		if (lexer == null) {
			throw new NullPointerException("Lexer cannot be null");
		}

		// End of stream
		if (!lexer.canRead()) {
			throw new MalformedNBTException("Expected value at index " + lexer.getCursor() + " for: " + lexer.getFullString());
		}

		char c = lexer.peek();

		// Compound tag
		if (c == '{') {
			return readCompound(lexer);
		}

		// List or array tag
		if (c == '[') {
			return readList(lexer);
		}

		int i = lexer.getCursor();
		boolean quote = c == '\"' || c == '\'';
		String string = lexer.readString();

		// Explicitly a string because it is quoted
		if (quote) {
			return new NBTTagString(string);
		}

		if (string.isEmpty()) {
			lexer.setCursor(i);
			throw new MalformedNBTException("Expected value at index " + lexer.getCursor() + " for: " + lexer.getFullString());
		}

		char suffix = string.charAt(string.length() - 1);

		// Try to parse based on suffix
		switch (suffix) {
			case 'b':
			case 'B':
				try {
					return new NBTTagByte(Byte.parseByte(string.substring(0, string.length() - 1)));
				} catch (NumberFormatException exception) {
					break;
				}
			case 'd':
			case 'D':
				try {
					return new NBTTagDouble(Byte.parseByte(string.substring(0, string.length() - 1)));
				} catch (NumberFormatException exception) {
					break;
				}
			case 'f':
			case 'F':
				try {
					return new NBTTagFloat(Float.parseFloat(string.substring(0, string.length() - 1)));
				} catch (NumberFormatException exception) {
					break;
				}
			case 'l':
			case 'L':
				try {
					return new NBTTagLong(Long.parseLong(string.substring(0, string.length() - 1)));
				} catch (NumberFormatException exception) {
					break;
				}
			case 's':
			case 'S':
				try {
					return new NBTTagShort(Short.parseShort(string.substring(0, string.length() - 1)));
				} catch (NumberFormatException exception) {
					break;
				}
			default:
				break;
		}

		// Integer does not have a specific suffix
		try {
			return new NBTTagInt(Integer.parseInt(string));
		} catch (NumberFormatException exception) {
			// Nothing
		}

		// Boolean parsing
		if (string.equalsIgnoreCase("true")) {
			return new NBTTagByte(true);
		}
		if (string.equalsIgnoreCase("false")) {
			return new NBTTagByte(false);
		}

		// Fall back on string
		return new NBTTagString(string);
	}
}
