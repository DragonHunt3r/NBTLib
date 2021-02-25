package net.steelphoenix.nbtlib;

/**
 * A size limiter.
 * Note that instances cannot be reused.
 *
 * @author SteelPhoenix
 */
public class NBTSizeLimiter {

	public static final NBTSizeLimiter UNLIMITED = new NBTSizeLimiter(0L) {
		@Override
		public void addBytesRead(long read) {
			// Nothing
		}
	};
	private final long max;
	private long count = 0;

	public NBTSizeLimiter(long max) {
		if (max < 0) {
			throw new IllegalArgumentException("Maximum cannot be negative");
		}

		this.max = max;
	}

	/**
	 * Tell the limiter the amount of bytes you read.
	 *
	 * @param read Target count.
	 */
	public void addBytesRead(long read) {
		// Preconditions
		if (read < 0L) {
			throw new IllegalArgumentException("Count cannot be negative");
		}

		// It is highly unlikely to overflow
		// It will overflow past 8 billion gibibytes read.
		count += read;
		if (count > max) {
			throw new MalformedNBTException("NBT tag is larger than allowed (" + max + " byte(s))");
		}
	}
}
