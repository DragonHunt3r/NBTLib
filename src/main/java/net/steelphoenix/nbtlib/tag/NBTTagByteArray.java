package net.steelphoenix.nbtlib.tag;

import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.steelphoenix.nbtlib.AbstractCollectionNBTTag;
import net.steelphoenix.nbtlib.MalformedNBTException;
import net.steelphoenix.nbtlib.NBTTagType;

/**
 * A Byte array tag.
 * This tag is valid if the array does not contain null elements and all values are valid.
 *
 * @author SteelPhoenix
 */
public class NBTTagByteArray extends AbstractCollectionNBTTag<NBTTagByte> {

	public static final NBTTagType TYPE = NBTTagType.BYTE_ARRAY;

	public NBTTagByteArray() {
		this(new byte[0]);
	}

	public NBTTagByteArray(byte[] value) {
		super(TYPE, toList(value));
	}

	@Override
	public NBTTagType getElementType() {
		return NBTTagType.BYTE;
	}

	@Override
	public List<NBTTagByte> getValue() {
		List<NBTTagByte> list = new ArrayList<>(size());
		for (NBTTagByte tag : this) {
			list.add(tag == null ? null : tag.copy());
		}
		return list;
	}

	@Override
	public void setValue(List<NBTTagByte> value) {
		// Preconditions
		if (value == null) {
			throw new NullPointerException("Value cannot be null");
		}

		List<NBTTagByte> list = new ArrayList<>(value.size());
		for (NBTTagByte tag : value) {
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
		for (NBTTagByte tag : this) {
			output.writeByte(tag.getAsByte());
		}
	}

	@Override
	public NBTTagByteArray copy() {
		NBTTagByteArray tag = new NBTTagByteArray();
		tag.setValue0(getValue());
		return tag;
	}

	@Override
	public String asSNBT(boolean pretty) {
		return asSNBT(pretty, "[B;", "]");
	}

	/**
	 * Convert an array to a tag list.
	 *
	 * @param array Target array.
	 * @return the created list.
	 */
	private static List<NBTTagByte> toList(byte[] array) {
		// Preconditions
		if (array == null) {
			throw new NullPointerException("Array cannot be null");
		}

		List<NBTTagByte> list = new ArrayList<>(array.length);
		for (int i = 0; i < array.length; i++) {
			list.add(new NBTTagByte(array[i]));
		}
		return list;
	}
}
