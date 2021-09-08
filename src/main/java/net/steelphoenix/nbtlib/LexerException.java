package net.steelphoenix.nbtlib;

/**
 * An exception for when a {@link Lexer} fails to read and/or parse data.
 *
 * @author SteelPhoenix
 */
public class LexerException extends RuntimeException {

	private static final long serialVersionUID = 6509932443611768538L;

	public LexerException() {
		super();
	}

	public LexerException(String message) {
		super(message);
	}

	public LexerException(Throwable cause) {
		super(cause);
	}

	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}
}
