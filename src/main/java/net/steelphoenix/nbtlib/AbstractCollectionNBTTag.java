package net.steelphoenix.nbtlib;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
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

	protected AbstractCollectionNBTTag(NBTTagType type) {
		super(type);
	}

	@Override
	public byte getElementTypeId() {
		return getElementType().getId();
	}

	@Override
	public int size() {
		return super.getValue().size();
	}

	@Override
	public boolean isEmpty() {
		return super.getValue().isEmpty();
	}

	@Override
	public E get(int index) {
		return super.getValue().get(index);
	}

	@Override
	public E set(int index, E element) {
		return super.getValue().set(index, element);
	}

	@Override
	public boolean add(E element) {
		return super.getValue().add(element);
	}

	@Override
	public void add(int index, E element) {
		super.getValue().add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends E> collection) {
		return super.getValue().addAll(collection);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> collection) {
		return super.getValue().addAll(index, collection);
	}

	@Override
	public boolean remove(Object object) {
		return super.getValue().remove(object);
	}

	@Override
	public E remove(int index) {
		return super.getValue().remove(index);
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		return super.getValue().removeAll(collection);
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		return super.getValue().retainAll(collection);
	}

	@Override
	public void clear() {
		super.getValue().clear();
	}

	@Override
	public boolean contains(Object object) {
		return super.getValue().contains(object);
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		return super.getValue().containsAll(collection);
	}

	@Override
	public int indexOf(Object object) {
		return super.getValue().indexOf(object);
	}

	@Override
	public int lastIndexOf(Object object) {
		return super.getValue().lastIndexOf(object);
	}

	@Override
	public Iterator<E> iterator() {
		return super.getValue().iterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		return super.getValue().listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return super.getValue().listIterator(index);
	}

	@Override
	public Spliterator<E> spliterator() {
		return super.getValue().spliterator();
	}

	@Override
	public Stream<E> stream() {
		return super.getValue().stream();
	}

	@Override
	public Stream<E> parallelStream() {
		return super.getValue().parallelStream();
	}

	@Override
	public void forEach(Consumer<? super E> action) {
		super.getValue().forEach(action);
	}

	@Override
	public void replaceAll(UnaryOperator<E> operator) {
		super.getValue().replaceAll(operator);
	}

	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		return super.getValue().removeIf(filter);
	}

	@Override
	public void sort(Comparator<? super E> comparator) {
		super.getValue().sort(comparator);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return super.getValue().subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return super.getValue().toArray();
	}

	@Override
	public <T> T[] toArray(T[] array) {
		return super.getValue().toArray(array);
	}

	@Override
	public boolean isValid() {
		if (!super.isValid()) {
			return false;
		}
		for (INBTTag<?> tag : this) {
			if (tag == null || tag.getType() != getElementType() || !tag.isValid()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		if (getValue0() == null) {
			return 0;
		}
		int result = 1;
		for (INBTTag<?> tag : this) {
			result = 31 * result + (tag == null ? 0 : tag.hashCode());
		}
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof AbstractCollectionNBTTag)) {
			return false;
		}
		AbstractCollectionNBTTag<?> other = (AbstractCollectionNBTTag<?>) object;

		if (getElementType() != other.getElementType()) {
			return false;
		}

		if (getValue0() == null ^ other.getValue0() == null) {
			return false;
		}

		if (getValue0() == null && other.getValue0() == null) {
			return true;
		}


		Iterator<E> iterator0 = iterator();
		Iterator<?> iterator1 = other.iterator();
		while (iterator0.hasNext() && iterator1.hasNext()) {
			INBTTag<?> element0 = iterator0.next();
			Object element1 = iterator1.next();
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
				.append(", value=");

		if (getValue0() == null) {
			builder.append("null");
		}
		else {
			builder.append("List[");
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
			builder.append(']');
		}
		builder.append(']');
		return builder.toString();
	}
}