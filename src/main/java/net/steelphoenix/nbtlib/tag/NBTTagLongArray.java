package net.steelphoenix.nbtlib.tag;

import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.steelphoenix.nbtlib.AbstractCollectionNBTTag;
import net.steelphoenix.nbtlib.MalformedNBTException;
import net.steelphoenix.nbtlib.NBTTagType;

/**
 * A long array tag.
 * This tag is valid if the array does not contain null elements and all values are valid.
 *
 * @author SteelPhoenix
 */
public class NBTTagLongArray extends AbstractCollectionNBTTag<NBTTagLong> {

	public static final NBTTagType TYPE = NBTTagType.LONG_ARRAY;

	public NBTTagLongArray() {
		this(new long[0]);
	}

	public NBTTagLongArray(long[] value) {
		super(TYPE, toList(value));
	}

	@Override
	public NBTTagType getElementType() {
		return NBTTagType.LONG;
	}

	@Override
	public List<NBTTagLong> getValue() {
		List<NBTTagLong> list = new ArrayList<>(size());
		for (NBTTagLong tag : this) {
			list.add(tag == null ? null : tag.copy());
		}
		return list;
	}

	@Override
	public void setValue(List<NBTTagLong> value) {
		// Preconditions
		if (value == null) {
			throw new NullPointerException("Value cannot be null");
		}

		List<NBTTagLong> list = new ArrayList<>(value.size());
		for (NBTTagLong tag : value) {
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
		for (NBTTagLong tag : this) {
			output.writeLong(tag.getAsLong());
		}
	}

	@Override
	public NBTTagLongArray copy() {
		NBTTagLongArray tag = new NBTTagLongArray();
		tag.setValue0(getValue());
		return tag;
	}

	@Override
	public String asSNBT() {
		// Preconditions
		if (!isValid()) {
			throw new MalformedNBTException("Tag is not valid");
		}

		StringBuilder builder = new StringBuilder();
		builder.append("[L;");
		Iterator<NBTTagLong> iterator = iterator();
		boolean first = true;
		while (iterator.hasNext()) {
			if (!first) {
				builder.append(',');
			}
			first = false;
			NBTTagLong tag = iterator.next();
			builder
					.append(tag.getValue().toString())
					.append('L');
		}
		builder.append(']');
		return builder.toString();
	}

	/**
	 * Convert an array to a tag list.
	 *
	 * @param array Target array.
	 * @return the created list.
	 */
	private static List<NBTTagLong> toList(long[] array) {
		// Preconditions
		if (array == null) {
			throw new NullPointerException("Array cannot be null");
		}

		List<NBTTagLong> list = new ArrayList<>(array.length);
		for (int i = 0; i < array.length; i++) {
			list.add(new NBTTagLong(array[i]));
		}
		return list;
	}
}