package net.steelphoenix.nbtlib;

/**
 * A lexer for SNBT formatted strings.
 *
 * @author SteelPhoenix
 */
public class Lexer {

	private final char[] string;
	private int cursor = 0;

	public Lexer(String string) {
		if (string == null) {
			throw new NullPointerException("String cannot be null");
		}
		this.string = string.toCharArray();
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

		return cursor + amount <= string.length;
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
		if (cursor + offset >= string.length) {
			throw new IndexOutOfBoundsException("Invalid index for array of size " + string.length + ": " + (cursor + offset));
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
	 * Get the character at the current position and increment the cursor.
	 * The read character must be equal to the target character.
	 *
	 * @param c Target character.
	 */
	public void expect(char c) {
		if (!canRead() || peek() != c) {
			throw new MalformedNBTException("Expected '" + c + "' at index " + cursor + " for: " + getFullString());
		}
		read();
	}

	/**
	 * Read a string.
	 *
	 * @return the read string.
	 */
	public String readString() {
		// End of stream
		if (!canRead()) {
			return "";
		}

		// Quoted string
		char quote = peek();
		if (quote == '\"' || quote == '\'') {
			// Skip quote
			read();

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
					throw new MalformedNBTException("Expected '" + quote + "' or '\' at index " + cursor + " for: " + getFullString());
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
			throw new MalformedNBTException("Expected '" + quote + "' at index " + cursor + " for: " + getFullString());
		}

		// Simple string
		int start = cursor;
		while (canRead() && isSimple(peek())) {
			read();
		}
		return new String(string, start, cursor - start);
	}

	/**
	 * Get if the given character is a simple character.
	 * Matches A-Z, a-z, 0-9, ., _, +, -.
	 *
	 * @param c Target character.
	 * @return if the character is simple.
	 */
	private boolean isSimple(char c) {
		return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '.' || c == '_' || c == '+' || c == '-';
	}
}
