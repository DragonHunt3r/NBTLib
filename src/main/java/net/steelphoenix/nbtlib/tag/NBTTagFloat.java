package net.steelphoenix.nbtlib.tag;

import java.io.DataOutput;
import java.io.IOException;

import net.steelphoenix.nbtlib.AbstractNumericNBTTag;
import net.steelphoenix.nbtlib.MalformedNBTException;
import net.steelphoenix.nbtlib.NBTTagType;

/**
 * A float tag.
 * This tag is valid if a value is set.
 *
 * @author SteelPhoenix
 */
public class NBTTagFloat extends AbstractNumericNBTTag<Float> {

	public static final NBTTagType TYPE = NBTTagType.FLOAT;

	public NBTTagFloat() {
		super(TYPE);
	}

	public NBTTagFloat(float value) {
		super(TYPE);
		setValue(Float.valueOf(value));
	}

	@Override
	public byte getAsByte() {
		// This is what Minecraft does
		return (byte) (floor(getValue().byteValue()) & 0xFF);
	}

	@Override
	public short getAsShort() {
		// This is what Minecraft does
		return (short) (floor(getValue().shortValue()) & 0xFFFF);
	}

	@Override
	public int getAsInt() {
		// This is what Minecraft does
		return floor(getValue().intValue());
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

		output.writeFloat(getValue().floatValue());
	}

	@Override
	public NBTTagFloat copy() {
		NBTTagFloat tag = new NBTTagFloat();
		tag.setValue0(getValue0());
		return tag;
	}

	@Override
	public String asSNBT() {
		// Preconditions
		if (!isValid()) {
			throw new MalformedNBTException("Tag is not valid");
		}

		return getValue().toString() + "f";
	}

	/**
	 * Floor a floating point value.
	 *
	 * @param f Target value.
	 * @return the floored value.
	 */
	private static int floor(float f) {
		int i = (int) f;
		return f < i ? i - 1 : i;
	}
}