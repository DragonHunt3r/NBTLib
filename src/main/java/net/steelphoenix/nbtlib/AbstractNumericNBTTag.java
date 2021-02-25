package net.steelphoenix.nbtlib;

/**
 * A base numeric NBT tag implementation.
 *
 * @param <E> Number type.
 * @author SteelPhoenix
 */
public abstract class AbstractNumericNBTTag<T extends Number> extends AbstractNBTTag<T> implements INumericNBTTag<T> {

	protected AbstractNumericNBTTag(NBTTagType type) {
		super(type);
	}

	@Override
	public byte getAsByte() {
		return getAsNumber().byteValue();
	}

	@Override
	public short getAsShort() {
		return getAsNumber().shortValue();
	}

	@Override
	public int getAsInt() {
		return getAsNumber().intValue();
	}

	@Override
	public long getAsLong() {
		return getAsNumber().longValue();
	}

	@Override
	public float getAsFloat() {
		return getAsNumber().floatValue();
	}

	@Override
	public double getAsDouble() {
		return getAsNumber().doubleValue();
	}

	@Override
	public Number getAsNumber() {
		return getValue();
	}
}
