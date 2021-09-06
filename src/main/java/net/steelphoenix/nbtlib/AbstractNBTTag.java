package net.steelphoenix.nbtlib;

/**
 * A base NBT tag implementation.
 *
 * @author SteelPhoenix
 */
public abstract class AbstractNBTTag<T> implements INBTTag<T> {

	private final NBTTagType type;
	private T value;

	protected AbstractNBTTag(NBTTagType type, T value) {
		if (type == null) {
			throw new NullPointerException("Type cannot be null");
		}
		this.type = type;
		setValue0(value);
	}

	@Override
	public NBTTagType getType() {
		return type;
	}

	@Override
	public byte getTypeId() {
		return getType().getId();
	}

	@Override
	public T getValue() {
		return getValue0();
	}

	@Override
	public void setValue(T value) {
		setValue0(value);
	}

	@Override
	public String asSNBT() {
		return asSNBT(false);
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof INBTTag<?>)) {
			return false;
		}
		INBTTag<?> other = (INBTTag<?>) object;
		return type == other.getType() && value.equals(other.getValue());
	}

	@Override
	public String toString() {
		return "NBTTag[type=" + type.getName() + ", value=" + value + "]";
	}

	/**
	 * Get the tag value.
	 * Note that this will return the field value directly.
	 *
	 * @return the tag value.
	 */
	protected T getValue0() {
		return value;
	}

	/**
	 * Set the tag value.
	 * Note that this will set the field value directly.
	 *
	 * @param value Target value.
	 */
	protected void setValue0(T value) {
		// Preconditions
		if (value == null) {
			throw new NullPointerException("Value cannot be null");
		}

		this.value = value;
	}
}