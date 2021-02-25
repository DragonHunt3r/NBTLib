package net.steelphoenix.nbtlib;

import java.io.DataOutput;
import java.io.IOException;

/**
 * A named binary tag.
 *
 * @param <T> Tag type.
 * @author SteelPhoenix
 */
public interface INBTTag<T> {

	/**
	 * Get the tag type.
	 *
	 * @return the tag type.
	 */
	public NBTTagType getType();

	/**
	 * Get the tag type id.
	 *
	 * @return the tag type id.
	 */
	public byte getTypeId();

	/**
	 * Get the tag value.
	 * Note that this returns a snapshot.
	 *
	 * @return the value.
	 */
	public T getValue();

	/**
	 * Set the tag value.
	 * Note that this may not be null.
	 *
	 * @param value Target value.
	 */
	public void setValue(T value);

	/**
	 * Write the tag to a data output.
	 *
	 * @param output Target data output.
	 * @throws IOException if an I/O error occurs.
	 */
	public void write(DataOutput output) throws IOException;

	/**
	 * Create a copy of this tag.
	 *
	 * @return the copy.
	 */
	public INBTTag<T> copy();

	/**
	 * Get a stringified NBT representation of this tag.
	 *
	 * @return the SNBT representation.
	 */
	public String asSNBT();

	/**
	 * Check if the tag is valid.
	 *
	 * @return if the tag is valid.
	 */
	public boolean isValid();
}
