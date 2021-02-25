package net.steelphoenix.nbtlib.tag;

import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.steelphoenix.nbtlib.AbstractCollectionNBTTag;
import net.steelphoenix.nbtlib.INBTTag;
import net.steelphoenix.nbtlib.MalformedNBTException;
import net.steelphoenix.nbtlib.NBTTagType;

/**
 * A list tag.
 * This tag is valid if a value is set, the array does not contain null elements, the array elements are of the correct type and all values are valid.
 *
 * @author SteelPhoenix
 */
public class NBTTagList extends AbstractCollectionNBTTag<INBTTag<?>> {

	public static final NBTTagType TYPE = NBTTagType.LIST;
	private NBTTagType type = NBTTagType.END;

	public NBTTagList() {
		super(TYPE);
	}

	public NBTTagList(NBTTagType type, INBTTag<?>[] value) {
		super(TYPE);
		if (type == null) {
			throw new NullPointerException("Type cannot be null");
		}
		if (value == null) {
			throw new NullPointerException("Value cannot be null");
		}
		List<INBTTag<?>> list = new ArrayList<>();
		for (int i = 0; i < value.length; i++) {
			list.add(value[i] == null ? null : value[i].copy());
		}
		setElementType(type);
		setValue0(list);
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
		List<INBTTag<?>> list = new ArrayList<>();
		forEach(e -> list.add(e.copy()));
		return list;
	}

	@Override
	public void setValue(List<INBTTag<?>> value) {
		// Preconditions
		if (value == null) {
			throw new NullPointerException("Value cannot be null");
		}

		List<INBTTag<?>> list = new ArrayList<>();
		value.forEach(e -> list.add(e.copy()));
		super.setValue(list);
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
		for (int i = 0; i < size(); i++) {
			get(i).write(output);
		}
	}

	@Override
	public NBTTagList copy() {
		NBTTagList tag = new NBTTagList();
		tag.setElementType(getElementType());

		// Create a copy if there is a value set
		tag.setValue0(getValue0() == null ? null : getValue());

		return tag;
	}

	@Override
	public String asSNBT() {
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		Iterator<INBTTag<?>> iterator = iterator();
		boolean first = true;
		while (iterator.hasNext()) {
			if (!first) {
				builder.append(',');
			}
			first = false;
			INBTTag<?> tag = iterator.next();
			builder.append(tag.asSNBT());
		}
		builder.append(']');
		return builder.toString();
	}
}
