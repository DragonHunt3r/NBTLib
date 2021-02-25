package net.steelphoenix.nbtlib.tag;

import java.io.DataOutput;
import java.io.IOException;

import net.steelphoenix.nbtlib.AbstractNBTTag;
import net.steelphoenix.nbtlib.NBTTagType;

/**
 * An end tag.
 * This tag is always valid.
 *
 * @author SteelPhoenix
 */
public class NBTTagEnd extends AbstractNBTTag<Void> {

	public static final NBTTagType TYPE = NBTTagType.END;

	public NBTTagEnd() {
		super(TYPE);
	}

	@Override
	public Void getValue() {
		return null;
	}

	@Override
	public void setValue(Void value) {
		throw new UnsupportedOperationException("Cannot set data for " + getType().getName());
	}

	@Override
	public void write(DataOutput output) throws IOException {
		// Nothing
	}

	@Override
	public NBTTagEnd copy() {
		return this;
	}

	@Override
	public String asSNBT() {
		return "END";
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public String toString() {
		// No value on an end tag
		return "NBTTag[type=" + getType().getName() + "]";
	}

	@Override
	protected Void getValue0() {
		return null;
	}

	@Override
	protected void setValue0(Void value) {
		throw new UnsupportedOperationException("Cannot set data for " + getType().getName());
	}
}
