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
 * A Byte array tag.
 * This tag is valid if a value is set, the array does not contain null elements and all values are valid.
 *
 * @author SteelPhoenix
 */
public class NBTTagByteArray extends AbstractCollectionNBTTag<NBTTagByte> {

	public static final NBTTagType TYPE = NBTTagType.BYTE_ARRAY;

	public NBTTagByteArray() {
		super(TYPE);
	}

	public NBTTagByteArray(byte[] value) {
		super(TYPE);
		if (value == null) {
			throw new NullPointerException("Value cannot be null");
		}
		List<NBTTagByte> list = new ArrayList<>();
		for (int i = 0; i < value.length; i++) {
			list.add(new NBTTagByte(value[i]));
		}
		setValue0(list);
	}

	@Override
	public NBTTagType getElementType() {
		return NBTTagType.BYTE;
	}

	@Override
	public List<NBTTagByte> getValue() {
		List<NBTTagByte> list = new ArrayList<>();
		forEach(e -> list.add(e.copy()));
		return list;
	}

	@Override
	public void setValue(List<NBTTagByte> value) {
		// Preconditions
		if (value == null) {
			throw new NullPointerException("Value cannot be null");
		}

		List<NBTTagByte> list = new ArrayList<>();
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

		output.writeInt(size());
		for (int i = 0; i < size(); i++) {
			output.writeByte(get(i).getAsByte());
		}
	}

	@Override
	public NBTTagByteArray copy() {
		NBTTagByteArray tag = new NBTTagByteArray();

		// Create a copy if there is a value set
		tag.setValue0(getValue0() == null ? null : getValue());

		return tag;
	}

	@Override
	public String asSNBT() {
		// Preconditions
		if (!isValid()) {
			throw new MalformedNBTException("Tag is not valid");
		}

		StringBuilder builder = new StringBuilder();
		builder.append("[B;");
		Iterator<NBTTagByte> iterator = iterator();
		boolean first = true;
		while (iterator.hasNext()) {
			if (!first) {
				builder.append(',');
			}
			first = false;
			NBTTagByte tag = iterator.next();
			builder
					.append(tag.getValue().toString())
					.append('B');
		}
		builder.append(']');
		return builder.toString();
	}
}
