package net.steelphoenix.nbtlib.tag;

import java.io.DataOutput;
import java.io.IOException;

import net.steelphoenix.nbtlib.AbstractNumericNBTTag;
import net.steelphoenix.nbtlib.MalformedNBTException;
import net.steelphoenix.nbtlib.NBTTagType;

/**
 * An integer tag.
 * This tag is always valid.
 *
 * @author SteelPhoenix
 */
public class NBTTagInt extends AbstractNumericNBTTag<Integer> {

	public static final NBTTagType TYPE = NBTTagType.INT;

	public NBTTagInt() {
		this(0);
	}

	public NBTTagInt(int value) {
		super(TYPE, Integer.valueOf(value));
	}

	@Override
	public byte getAsByte() {
		// This is what Minecraft does
		return (byte) (getValue().intValue() & 0xFF);
	}

	@Override
	public short getAsShort() {
		// This is what Minecraft does
		return (short) (getValue().intValue() & 0xFFFF);
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

		output.writeInt(getValue().intValue());
	}

	@Override
	public NBTTagInt copy() {
		NBTTagInt tag = new NBTTagInt();
		tag.setValue0(getValue());
		return tag;
	}

	@Override
	public String asSNBT(boolean pretty) {
		// Preconditions
		if (!isValid()) {
			throw new MalformedNBTException("Tag is not valid");
		}

		return getValue().toString();
	}
}