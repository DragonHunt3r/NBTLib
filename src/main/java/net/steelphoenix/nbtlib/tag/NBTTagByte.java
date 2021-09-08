package net.steelphoenix.nbtlib.tag;

import java.io.DataOutput;
import java.io.IOException;

import net.steelphoenix.nbtlib.AbstractNumericNBTTag;
import net.steelphoenix.nbtlib.MalformedNBTException;
import net.steelphoenix.nbtlib.NBTTagType;

/**
 * A byte tag.
 * This tag is always valid.
 * Booleans are also stored in a byte tag.
 *
 * @author SteelPhoenix
 */
public class NBTTagByte extends AbstractNumericNBTTag<Byte> {

	public static final NBTTagType TYPE = NBTTagType.BYTE;

	public NBTTagByte() {
		this((byte) 0);
	}

	public NBTTagByte(boolean value) {
		this(value ? (byte) 1 : (byte) 0);
	}

	public NBTTagByte(byte value) {
		super(TYPE, Byte.valueOf(value));
	}

	/**
	 * Get the value as a boolean.
	 * Note that 0 = false, 1 = true, other values are not allowed.
	 *
	 * @return the boolean value.
	 */
	public boolean getAsBoolean() {
		switch (getValue().byteValue()) {
			case 0:
				return false;
			case 1:
				return true;
			default:
				throw new IllegalStateException("Value is not 0 or 1");
		}
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

		output.writeByte(getValue().byteValue());
	}

	@Override
	public NBTTagByte copy() {
		NBTTagByte tag = new NBTTagByte();
		tag.setValue0(getValue());
		return tag;
	}

	@Override
	public String asSNBT(boolean pretty) {
		// Preconditions
		if (!isValid()) {
			throw new MalformedNBTException("Tag is not valid");
		}

		return getValue().toString() + 'b';
	}
}
