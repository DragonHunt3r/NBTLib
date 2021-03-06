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
public class NBTTagEnd extends AbstractNBTTag<Object> {

	public static final NBTTagType TYPE = NBTTagType.END;
	private static final Object OBJECT = new Object();

	public NBTTagEnd() {
		super(TYPE, OBJECT);
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
	public String toString() {
		// No value on an end tag
		return "NBTTag[type=" + getType().getName() + "]";
	}

	@Override
	protected void setValue0(Object value) {
		throw new UnsupportedOperationException("Cannot set data for " + getType().getName());
	}
}
