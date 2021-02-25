package net.steelphoenix.nbtlib;

/**
 * An exception for when NBT data is malformed.
 *
 * @author SteelPhoenix
 */
public class MalformedNBTException extends RuntimeException {

	private static final long serialVersionUID = 2294326450763923963L;

	public MalformedNBTException() {
		super();
	}

	public MalformedNBTException(String message) {
		super(message);
	}

	public MalformedNBTException(Throwable cause) {
		super(cause);
	}

	public MalformedNBTException(String message, Throwable cause) {
		super(message, cause);
	}
}
