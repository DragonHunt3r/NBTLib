package net.steelphoenix.nbtlib;

/**
 * A lexer for SNBT formatted strings.
 *
 * @author SteelPhoenix
 */
public class Lexer {

	// For some reason this does not allow a positive sign ('+') or an exponent ('e', 'E')
	private static final CharPredicate NUMBER = c -> c >= '0' && c <= '9' || c == '.' || c == '-';
	// For some reason this does not math non-alphanumeric non-control chars
	private static final CharPredicate SIMPLE_STRING = c -> (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '.' || c == '_' || c == '+' || c == '-';
	private static final CharPredicate WHITESPACE = Character::isWhitespace;
	private final char[] string;
	private int cursor = 0;

	public Lexer(String string) {
		if (string == null) {
			throw new NullPointerException("String cannot be null");
		}
		this.string = string.toCharArray();
	}

	/**
	 * Get the part of the string read.
	 *
	 * @return the read string.
	 */
	public String getReadString() {
		return new String(string, 0, cursor);
	}

	/**
	 * Get the part of the string left to read.
	 *
	 * @return the remaining string.
	 */
	public String getRemainingString() {
		return new String(string, cursor, string.length - cursor);
	}

	/**
	 * Get the string this lexer is using.
	 *
	 * @return the string.
	 */
	public String getFullString() {
		return new String(string);
	}

	/**
	 * Get the number of characters read.
	 *
	 * @return the read length.
	 */
	public int getReadLength() {
		return cursor;
	}

	/**
	 * Get the number of characters left to read.
	 *
	 * @return the remaining length.
	 */
	public int getRemainingLength() {
		return string.length - cursor;
	}

	/**
	 * Get the number of characters in the string.
	 *
	 * @return the total length.
	 */
	public int getTotalLength() {
		return string.length;
	}

	/**
	 * Get the lexer cursor position.
	 *
	 * @return the cursor position.
	 */
	public int getCursor() {
		return cursor;
	}

	/**
	 * Set the lexer cursor position.
	 *
	 * @param cursor Target cursor position.
	 */
	public void setCursor(int cursor) {
		// Preconditions
		if (cursor < 0 || cursor > string.length) {
			throw new IndexOutOfBoundsException("Invalid index for array of size " + string.length + ": " + cursor);
		}

		this.cursor = cursor;
	}

	/**
	 * Get if the lexer can read one character.
	 *
	 * @return if the lexer can read at least one character.
	 */
	public boolean canRead() {
		return canRead(1);
	}

	/**
	 * Get if the lexer can read a given amount of characters.
	 *
	 * @param amount Target amount.
	 * @return if the lexer can read at least the amount of characters.
	 */
	public boolean canRead(int amount) {
		// Preconditions
		if (amount < 0) {
			throw new IllegalArgumentException("Amount cannot be negative");
		}

		// Overflow conscious
		return string.length - cursor >= amount;
	}

	/**
	 * Get the character at the current cursor position.
	 *
	 * @return the character at the current position.
	 */
	public char peek() {
		return peek(0);
	}

	/**
	 * Get the character at the given offset from the current cursor position.
	 *
	 * @param offset Target offset.
	 * @return the character at the given position.
	 */
	public char peek(int offset) {
		// Preconditions
		if (offset < 0) {
			throw new IllegalArgumentException("Offset cannot be negative");
		}
		if (!canRead(offset)) {
			// Cast to long in case of overflows
			throw new IndexOutOfBoundsException("Invalid index for array of size " + string.length + ": " + ((long) cursor + (long) offset));
		}

		return string[cursor + offset];
	}

	/**
	 * Get the character at the current position and increment the cursor.
	 *
	 * @return the character at the current position.
	 */
	public char read() {
		// Preconditions
		if (cursor >= string.length) {
			throw new IndexOutOfBoundsException("Invalid index for array of size " + string.length + ": " + cursor);
		}

		return string[cursor++];
	}

	/**
	 * Skip the character at the current position and increment the cursor.
	 */
	public void skip() {
		read();
	}

	/**
	 * Skip whitespace characters.
	 */
	public void skipWhitespace() {
		readWhile(WHITESPACE, false, null);
	}

	/**
	 * Get the character at the current position and increment the cursor.
	 * The read character must be equal to the target character.
	 *
	 * @param c Target character.
	 */
	public void expect(char c) {
		if (!canRead() || peek() != c) {
			throw new LexerException("Expected '" + c + "' at index " + cursor);
		}
		skip();
	}

	/**
	 * Read an int.
	 *
	 * @return the read int.
	 */
	public int readInt() {
		int start = cursor;
		String string = readWhile(NUMBER, true, "Expected integer at index " + start);
		try {
			return Integer.parseInt(string);
		} catch (NumberFormatException exception) {
			throw new LexerException("Invalid integer at index " + start + ": " + string);
		}
	}

	/**
	 * Read a long.
	 *
	 * @return the read long.
	 */
	public long readLong() {
		int start = cursor;
		String string = readWhile(NUMBER, true, "Expected long at index " + start);
		try {
			return Long.parseLong(string);
		} catch (NumberFormatException exception) {
			throw new LexerException("Invalid long at index " + start + ": " + string);
		}
	}

	/**
	 * Read a float.
	 *
	 * @return the read float.
	 */
	public float readFloat() {
		int start = cursor;
		String string = readWhile(NUMBER, true, "Expected float at index " + start);
		try {
			return Float.parseFloat(string);
		} catch (NumberFormatException exception) {
			throw new LexerException("Invalid float at index " + start + ": " + string);
		}
	}

	/**
	 * Read a double.
	 *
	 * @return the read double.
	 */
	public double readDouble() {
		int start = cursor;
		String string = readWhile(NUMBER, true, "Expected double at index " + start);
		try {
			return Double.parseDouble(string);
		} catch (NumberFormatException exception) {
			throw new LexerException("Invalid double at index " + start + ": " + string);
		}
	}

	/**
	 * Read a boolean.
	 *
	 * @return the read boolean.
	 */
	public boolean readBoolean() {
		int start = cursor;
		String string = readString();

		// #readString() returns an empty string if no match was made or end of stream was reached
		if (string.isEmpty()) {
			throw new LexerException("Expected boolean at index " + start);
		}

		// Dirty parsing
		if (string.equals("true")) {
			return true;
		}
		if (string.equals("false")) {
			return false;
		}

		throw new LexerException("Invalid boolean at index " + start + ": " + string);
	}

	/**
	 * Read a string.
	 *
	 * @return the read string.
	 */
	public String readString() {
		// End of stream
		// For some reason this does not throw an exception
		if (!canRead()) {
			return "";
		}

		// Quoted string
		char quote = peek();
		if (quote == '\"' || quote == '\'') {
			// Skip quote
			skip();

			StringBuilder builder = new StringBuilder();
			boolean escaped = false;
			while (canRead()) {
				char c = read();

				// Next character should be an escapable character
				if (escaped) {
					if (c == quote || c == '\\') {
						builder.append(c);
						escaped = false;
						continue;
					}
					setCursor(cursor - 1);
					throw new LexerException("Expected '" + quote + "' or '\' at index " + cursor);
				}

				// If it is an escape we skip over it
				if (c == '\\') {
					escaped = true;
					continue;
				}

				// End of string
				if (c == quote) {
					return builder.toString();
				}

				// Regular character
				builder.append(c);
			}

			// No end quote found
			throw new LexerException("Expected '" + quote + "' at index " + cursor);
		}

		// Simple string
		return readWhile(SIMPLE_STRING, false, null);
	}

	/**
	 * Read a string while a predicate gets matched.
	 *
	 * @param predicate Target predicate.
	 * @param required If at least one character needs to be matched.
	 * @param message Exception message if no match is found or {@code null} for default message.
	 * @return the read string.
	 */
	private String readWhile(CharPredicate predicate, boolean required, String message) {
		// Preconditions
		if (predicate == null) {
			throw new NullPointerException("Predicate cannot be null");
		}

		int start = cursor;
		while (canRead() && predicate.test(peek())) {
			skip();
		}

		if (start == cursor && required) {
			throw new LexerException(message == null ? "No match found" : message);
		}

		return new String(string, start, cursor - start);
	}

	/**
	 * A predicate for the primitive {@code char}.
	 *
	 * @author SteelPhoenix
	 */
	@FunctionalInterface
	private static interface CharPredicate {

		/**
		 * Test a given character against the predicate.
		 *
		 * @param c Target character.
		 * @return
		 */
		public boolean test(char c);
	}
}
