package net.steelphoenix.nbtlib.tag;

import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.steelphoenix.nbtlib.AbstractCollectionNBTTag;
import net.steelphoenix.nbtlib.INBTTag;
import net.steelphoenix.nbtlib.MalformedNBTException;
import net.steelphoenix.nbtlib.NBTTagType;

/**
 * A list tag.
 * This tag is valid if the array does not contain null elements, the array elements are of the correct type and all values are valid.
 *
 * @author SteelPhoenix
 */
public class NBTTagList extends AbstractCollectionNBTTag<INBTTag<?>> {

	public static final NBTTagType TYPE = NBTTagType.LIST;
	private NBTTagType type = NBTTagType.END;

	public NBTTagList() {
		this(TYPE, new INBTTag<?>[0]);
	}

	public NBTTagList(NBTTagType type, INBTTag<?>[] value) {
		super(TYPE, toList(value));
		if (type == null) {
			throw new NullPointerException("Type cannot be null");
		}
		setElementType(type);
	}

	@Override
	public NBTTagType getElementType() {
		return type;
	}

	public void setElementType(NBTTagType type) {
		// Preconditions
		if (type == null) {
			throw new NullPointerException("Type cannot be null");
		}

		this.type = type;
	}

	@Override
	public List<INBTTag<?>> getValue() {
		List<INBTTag<?>> list = new ArrayList<>(size());
		for (INBTTag<?> tag : this) {
			list.add(tag == null ? null : tag.copy());
		}
		return list;
	}

	@Override
	public void setValue(List<INBTTag<?>> value) {
		// Preconditions
		if (value == null) {
			throw new NullPointerException("Value cannot be null");
		}

		List<INBTTag<?>> list = new ArrayList<>(value.size());
		for (INBTTag<?> tag : value) {
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

		output.writeByte(isEmpty() ? NBTTagType.END.getId() : type.getId());
		output.writeInt(size());
		for (INBTTag<?> tag : this) {
			tag.write(output);
		}
	}

	@Override
	public NBTTagList copy() {
		NBTTagList tag = new NBTTagList();
		tag.setElementType(getElementType());
		tag.setValue0(getValue());
		return tag;
	}

	@Override
	public String asSNBT(boolean pretty) {
		return asSNBT(pretty, "[", "]", (tag, flag) -> tag.asSNBT(flag));
	}

	/**
	 * Convert an array to a tag list.
	 *
	 * @param array Target array.
	 * @return the created list.
	 */
	private static List<INBTTag<?>> toList(INBTTag<?>[] array) {
		// Preconditions
		if (array == null) {
			throw new NullPointerException("Array cannot be null");
		}

		List<INBTTag<?>> list = new ArrayList<>(array.length);
		for (int i = 0; i < array.length; i++) {
			list.add(array[i] == null ? null : array[i].copy());
		}
		return list;
	}
}
