package net.steelphoenix.nbtlib;

/**
 * A numeric NBT tag.
 *
 * @author SteelPhoenix
 */
public interface INumericNBTTag<T extends Number> extends INBTTag<T> {

	/**
	 * Get the number as a byte.
	 *
	 * @return the byte value.
	 */
	public byte getAsByte();

	/**
	 * Get the number as a short.
	 *
	 * @return the short value.
	 */
	public short getAsShort();

	/**
	 * Get the number as an integer.
	 *
	 * @return the integer value.
	 */
	public int getAsInt();

	/**
	 * Get the number as a long.
	 *
	 * @return the long value.
	 */
	public long getAsLong();

	/**
	 * Get the number as a float.
	 *
	 * @return the float value.
	 */
	public float getAsFloat();

	/**
	 * Get the number as a double.
	 *
	 * @return the double value.
	 */
	public double getAsDouble();

	/**
	 * Get the number.
	 *
	 * @return the number.
	 */
	public Number getAsNumber();
}
