package net.steelphoenix.nbtlib;

import java.util.List;

/**
 * A collection NBT tag.
 *
 * @param <E> Element type.
 * @author SteelPhoenix
 */
public interface ICollectionNBTTag<E extends INBTTag<?>> extends INBTTag<List<E>>, List<E> {

	/**
	 * Get the element type.
	 *
	 * @return the element type.
	 */
	public NBTTagType getElementType();

	/**
	 * Get the element type id.
	 *
	 * @return the element type id.
	 */
	public byte getElementTypeId();
}