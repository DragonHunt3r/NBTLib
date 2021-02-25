package net.steelphoenix.nbtlib;

/**
 * A base NBT tag implementation.
 *
 * @author SteelPhoenix
 */
public abstract class AbstractNBTTag<T> implements INBTTag<T> {

	private final NBTTagType type;
	private T value = null;

	protected AbstractNBTTag(NBTTagType type) {
		if (type == null) {
			throw new NullPointerException("Type cannot be null");
		}

		this.type = type;
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
		// Preconditions
		if (value == null) {
			throw new IllegalStateException("No value set");
		}

		return getValue0();
	}

	@Override
	public void setValue(T value) {
		// Preconditions
		if (value == null) {
			throw new NullPointerException("Value cannot be null");
		}

		setValue0(value);
	}

	@Override
	public boolean isValid() {
		return value != null;
	}

	@Override
	public int hashCode() {
		return value == null ? 0 : value.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof AbstractNBTTag)) {
			return false;
		}
		AbstractNBTTag<?> other = (AbstractNBTTag<?>) object;
		return type == other.getType() && (value == null ? other.getValue0() == null : value.equals(other.getValue0()));
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
		this.value = value;
	}
}
