package net.steelphoenix.nbtlib;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * A base collection tag implementation.
 *
 * @param <E> Element type.
 * @author SteelPhoenix
 */
public abstract class AbstractCollectionNBTTag<E extends INBTTag<?>> extends AbstractNBTTag<List<E>> implements ICollectionNBTTag<E> {

	protected AbstractCollectionNBTTag(NBTTagType type, List<E> value) {
		super(type, value);
	}

	@Override
	public byte getElementTypeId() {
		return getElementType().getId();
	}

	@Override
	public int size() {
		return getValue0().size();
	}

	@Override
	public boolean isEmpty() {
		return getValue0().isEmpty();
	}

	@Override
	public E get(int index) {
		return getValue0().get(index);
	}

	@Override
	public E set(int index, E element) {
		return getValue0().set(index, element);
	}

	@Override
	public boolean add(E element) {
		return getValue0().add(element);
	}

	@Override
	public void add(int index, E element) {
		getValue0().add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends E> collection) {
		return getValue0().addAll(collection);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> collection) {
		return getValue0().addAll(index, collection);
	}

	@Override
	public boolean remove(Object object) {
		return getValue0().remove(object);
	}

	@Override
	public E remove(int index) {
		return getValue0().remove(index);
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		return getValue0().removeAll(collection);
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		return getValue0().retainAll(collection);
	}

	@Override
	public void clear() {
		getValue0().clear();
	}

	@Override
	public boolean contains(Object object) {
		return getValue0().contains(object);
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		return getValue0().containsAll(collection);
	}

	@Override
	public int indexOf(Object object) {
		return getValue0().indexOf(object);
	}

	@Override
	public int lastIndexOf(Object object) {
		return getValue0().lastIndexOf(object);
	}

	@Override
	public Iterator<E> iterator() {
		return getValue0().iterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		return getValue0().listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return getValue0().listIterator(index);
	}

	@Override
	public Spliterator<E> spliterator() {
		return getValue0().spliterator();
	}

	@Override
	public Stream<E> stream() {
		return getValue0().stream();
	}

	@Override
	public Stream<E> parallelStream() {
		return getValue0().parallelStream();
	}

	@Override
	public void forEach(Consumer<? super E> action) {
		getValue0().forEach(action);
	}

	@Override
	public void replaceAll(UnaryOperator<E> operator) {
		getValue0().replaceAll(operator);
	}

	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		return getValue0().removeIf(filter);
	}

	@Override
	public void sort(Comparator<? super E> comparator) {
		getValue0().sort(comparator);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return getValue0().subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return getValue0().toArray();
	}

	@Override
	public <T> T[] toArray(T[] array) {
		return getValue0().toArray(array);
	}

	@Override
	public boolean isValid() {
		for (E tag : this) {
			if (tag == null || tag.getType() != getElementType() || !tag.isValid()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = 1;
		for (E tag : this) {
			result = 31 * result + (tag == null ? 0 : tag.hashCode());
		}
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ICollectionNBTTag)) {
			return false;
		}
		ICollectionNBTTag<?> other = (ICollectionNBTTag<?>) object;

		if (getElementType() != other.getElementType()) {
			return false;
		}

		Iterator<E> iterator0 = iterator();
		Iterator<? extends INBTTag<?>> iterator1 = other.iterator();
		while (iterator0.hasNext() && iterator1.hasNext()) {
			E element0 = iterator0.next();
			INBTTag<?> element1 = iterator1.next();
			if (element0 == null ? element1 != null : !element0.equals(element1)) {
				return false;
			}
        }
        return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder
				.append("NBTTag[type=")
				.append(getType().getName())
				.append(", value=List[");
		Iterator<E> iterator = iterator();
		boolean first = true;
		while (iterator.hasNext()) {
			if (!first) {
				builder
						.append(',')
						.append(' ');
			}
			first = false;
			E element = iterator.next();
			builder.append(element);
		}
		builder
				.append(']')
				.append(']');
		return builder.toString();
	}

	protected String asSNBT(boolean pretty, String prefix, String suffix, BiFunction<? super E, ? super Boolean, ? extends String> stringFunction) {
		// Preconditions
		if (prefix == null) {
			throw new NullPointerException("Prefix cannot be null");
		}
		if (suffix == null) {
			throw new NullPointerException("Suffix cannot be null");
		}
		if (stringFunction == null) {
			throw new NullPointerException("String function cannot be null");
		}
		if (!isValid()) {
			throw new MalformedNBTException("Tag is not valid");
		}

		// Empty
		if (isEmpty()) {
			return prefix + suffix;
		}

		String newLine = pretty ? System.lineSeparator() : "";

		StringJoiner joiner = new StringJoiner("," + newLine, prefix + newLine, newLine + suffix);
		for (E tag : this) {
			String snbt = stringFunction.apply(tag, pretty);
			if (pretty) {
				snbt = '\t' + snbt.replaceAll("\\R", System.lineSeparator() + '\t');
			}
			joiner.add(snbt);
		}
		return joiner.toString();
	}
}