package net.steelphoenix.nbtlib.tag;

import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.steelphoenix.nbtlib.AbstractCollectionNBTTag;
import net.steelphoenix.nbtlib.MalformedNBTException;
import net.steelphoenix.nbtlib.NBTTagType;

/**
 * An integer array tag.
 * This tag is valid if the array does not contain null elements and all values are valid.
 *
 * @author SteelPhoenix
 */
public class NBTTagIntArray extends AbstractCollectionNBTTag<NBTTagInt> {

	public static final NBTTagType TYPE = NBTTagType.INT_ARRAY;

	public NBTTagIntArray() {
		this(new int[0]);
	}

	public NBTTagIntArray(int[] value) {
		super(TYPE, toList(value));
	}

	@Override
	public NBTTagType getElementType() {
		return NBTTagType.INT;
	}

	@Override
	public List<NBTTagInt> getValue() {
		List<NBTTagInt> list = new ArrayList<>(size());
		for (NBTTagInt tag : this) {
			list.add(tag == null ? null : tag.copy());
		}
		return list;
	}

	@Override
	public void setValue(List<NBTTagInt> value) {
		// Preconditions
		if (value == null) {
			throw new NullPointerException("Value cannot be null");
		}

		List<NBTTagInt> list = new ArrayList<>(value.size());
		for (NBTTagInt tag : value) {
			list.add(tag == null ? null : tag.copy());
		}
		setValue0(list);
	}

	@Override
	public void write(DataOutput output) throws IOException {
		// Preconditions
		if (output == null) {
			throw new NullPointerException("Output cannot be null");
		}
		if (!isValid()) {
			throw new MalformedNBTException("Tag is not valid");
		}

		output.writeInt(size());
		for (NBTTagInt tag : this) {
			output.writeInt(tag.getAsInt());
		}
	}

	@Override
	public NBTTagIntArray copy() {
		NBTTagIntArray tag = new NBTTagIntArray();
		tag.setValue0(getValue());
		return tag;
	}

	@Override
	public String asSNBT(boolean pretty) {
		return asSNBT(pretty, "[I;", "]");
	}

	/**
	 * Convert an array to a tag list.
	 *
	 * @param array Target array.
	 * @return the created list.
	 */
	private static List<NBTTagInt> toList(int[] array) {
		// Preconditions
		if (array == null) {
			throw new NullPointerException("Array cannot be null");
		}

		List<NBTTagInt> list = new ArrayList<>(array.length);
		for (int i = 0; i < array.length; i++) {
			list.add(new NBTTagInt(array[i]));
		}
		return list;
	}
}
