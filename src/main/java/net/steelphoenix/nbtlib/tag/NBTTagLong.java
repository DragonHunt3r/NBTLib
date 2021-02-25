package net.steelphoenix.nbtlib.tag;

import java.io.DataOutput;
import java.io.IOException;

import net.steelphoenix.nbtlib.AbstractNumericNBTTag;
import net.steelphoenix.nbtlib.MalformedNBTException;
import net.steelphoenix.nbtlib.NBTTagType;

/**
 * A long tag.
 * This tag is valid if a value is set.
 *
 * @author SteelPhoenix
 */
public class NBTTagLong extends AbstractNumericNBTTag<Long> {

	public static final NBTTagType TYPE = NBTTagType.LONG;

	public NBTTagLong() {
		super(TYPE);
	}

	public NBTTagLong(long value) {
		super(TYPE);
		setValue(Long.valueOf(value));
	}

	@Override
	public byte getAsByte() {
		// This is what Minecraft does
		return (byte) (getValue().longValue() & 0xFFL);
	}

	@Override
	public short getAsShort() {
		// This is what Minecraft does
		return (short) (getValue().longValue() & 0xFFFFL);
	}

	@Override
	public int getAsInt() {
		// This is what Minecraft does
		// This logical AND does not make any sense
		// They probably meant to use 0xFFFFFFFFL
		return (int) (getValue().longValue() & 0xFFFFFFFFFFFFFFFFL);
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

		output.writeLong(getValue().longValue());
	}

	@Override
	public NBTTagLong copy() {
		NBTTagLong tag = new NBTTagLong();
		tag.setValue0(getValue0());
		return tag;
	}

	@Override
	public String asSNBT() {
		// Preconditions
		if (!isValid()) {
			throw new MalformedNBTException("Tag is not valid");
		}

		return getValue().toString() + "L";
	}
}
