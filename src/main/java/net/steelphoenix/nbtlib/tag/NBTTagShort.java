package net.steelphoenix.nbtlib.tag;

import java.io.DataOutput;
import java.io.IOException;

import net.steelphoenix.nbtlib.AbstractNumericNBTTag;
import net.steelphoenix.nbtlib.MalformedNBTException;
import net.steelphoenix.nbtlib.NBTTagType;

/**
 * A short tag.
 * This tag is always valid.
 *
 * @author SteelPhoenix
 */
public class NBTTagShort extends AbstractNumericNBTTag<Short> {

	public static final NBTTagType TYPE = NBTTagType.SHORT;

	public NBTTagShort() {
		this((short) 0);
	}

	public NBTTagShort(short value) {
		super(TYPE, Short.valueOf(value));
	}

	@Override
	public byte getAsByte() {
		// This is what Minecraft does
		return (byte) (getValue().shortValue() & 0xFF);
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

		output.writeShort(getValue().shortValue());
	}

	@Override
	public NBTTagShort copy() {
		NBTTagShort tag = new NBTTagShort();
		tag.setValue0(getValue());
		return tag;
	}

	@Override
	public String asSNBT(boolean pretty) {
		// Preconditions
		if (!isValid()) {
			throw new MalformedNBTException("Tag is not valid");
		}

		return getValue().toString() + "s";
	}
}