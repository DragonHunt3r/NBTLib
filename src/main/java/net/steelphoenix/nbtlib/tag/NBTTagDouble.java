package net.steelphoenix.nbtlib.tag;

import java.io.DataOutput;
import java.io.IOException;

import net.steelphoenix.nbtlib.AbstractNumericNBTTag;
import net.steelphoenix.nbtlib.MalformedNBTException;
import net.steelphoenix.nbtlib.NBTTagType;

/**
 * A double tag.
 * This tag is valid if a value is set.
 *
 * @author SteelPhoenix
 */
public class NBTTagDouble extends AbstractNumericNBTTag<Double> {

	public static final NBTTagType TYPE = NBTTagType.DOUBLE;

	public NBTTagDouble() {
		super(TYPE);
	}

	public NBTTagDouble(double value) {
		super(TYPE);
		setValue(Double.valueOf(value));
	}

	@Override
	public byte getAsByte() {
		// This is what Minecraft does
		return (byte) (floor(getValue().doubleValue()) & 0xFF);
	}

	@Override
	public short getAsShort() {
		// This is what Minecraft does
		return (short) (floor(getValue().doubleValue()) & 0xFFFF);
	}

	@Override
	public int getAsInt() {
		// This is what Minecraft does
		return floor(getValue().doubleValue());
	}

	@Override
	public long getAsLong() {
		// This is what Minecraft does
		return (long) Math.floor(getValue().doubleValue());
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

		output.writeDouble(getValue().doubleValue());
	}

	@Override
	public NBTTagDouble copy() {
		NBTTagDouble tag = new NBTTagDouble();
		tag.setValue0(getValue0());
		return tag;
	}

	@Override
	public String asSNBT() {
		// Preconditions
		if (!isValid()) {
			throw new MalformedNBTException("Tag is not valid");
		}

		return getValue().toString() + "d";
	}

	/**
	 * Floor a double precision floating point value.
	 *
	 * @param d Target value.
	 * @return the floored value.
	 */
	private static int floor(double d) {
		int i = (int) d;
		return d < i ? i - 1 : i;
	}
}
