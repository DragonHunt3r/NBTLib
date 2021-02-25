package net.steelphoenix.nbtlib;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A base map NBT tag implementation.
 *
 * @author SteelPhoenix
 */
public abstract class AbstractMapNBTTag extends AbstractNBTTag<Map<String, INBTTag<?>>> implements IMapNBTTag<String, INBTTag<?>> {

	protected AbstractMapNBTTag(NBTTagType type) {
		super(type);
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
	public INBTTag<?> get(Object key) {
		return super.getValue().get(key);
	}

	@Override
	public INBTTag<?> getOrDefault(Object key, INBTTag<?> def) {
		return super.getValue().getOrDefault(key, def);
	}

	@Override
	public INBTTag<?> put(String key, INBTTag<?> value) {
		return super.getValue().put(key, value);
	}

	@Override
	public INBTTag<?> putIfAbsent(String key, INBTTag<?> value) {
		return super.getValue().putIfAbsent(key, value);
	}

	@Override
	public void putAll(Map<? extends String, ? extends INBTTag<?>> map) {
		super.getValue().putAll(map);
	}

	@Override
	public INBTTag<?> replace(String key, INBTTag<?> value) {
		return super.getValue().replace(key, value);
	}

	@Override
	public boolean replace(String key, INBTTag<?> oldValue, INBTTag<?> newValue) {
		return super.getValue().replace(key, oldValue, newValue);
	}

	@Override
	public INBTTag<?> remove(Object key) {
		return super.getValue().remove(key);
	}

	@Override
	public boolean remove(Object key, Object value) {
		return super.getValue().remove(key, value);
	}

	@Override
	public void clear() {
		super.getValue().clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return super.getValue().containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return super.getValue().containsValue(value);
	}

	@Override
	public Set<String> keySet() {
		return super.getValue().keySet();
	}

	@Override
	public Collection<INBTTag<?>> values() {
		return super.getValue().values();
	}

	@Override
	public Set<Entry<String, INBTTag<?>>> entrySet() {
		return super.getValue().entrySet();
	}

	@Override
	public void forEach(BiConsumer<? super String, ? super INBTTag<?>> action) {
		super.getValue().forEach(action);
	}

	@Override
	public void replaceAll(BiFunction<? super String, ? super INBTTag<?>, ? extends INBTTag<?>> function) {
		super.getValue().replaceAll(function);
	}

	@Override
	public INBTTag<?> merge(String key, INBTTag<?> value, BiFunction<? super INBTTag<?>, ? super INBTTag<?>, ? extends INBTTag<?>> remappingFunction) {
		return super.getValue().merge(key, value, remappingFunction);
	}

	@Override
	public INBTTag<?> compute(String key, BiFunction<? super String, ? super INBTTag<?>, ? extends INBTTag<?>> remappingFunction) {
		return super.getValue().compute(key, remappingFunction);
	}

	@Override
	public INBTTag<?> computeIfAbsent(String key, Function<? super String, ? extends INBTTag<?>> mappingFunction) {
		return super.getValue().computeIfAbsent(key, mappingFunction);
	}

	@Override
	public INBTTag<?> computeIfPresent(String key, BiFunction<? super String, ? super INBTTag<?>, ? extends INBTTag<?>> remappingFunction) {
		return super.getValue().computeIfPresent(key, remappingFunction);
	}

	@Override
	public boolean isValid() {
		if (!super.isValid()) {
			return false;
		}
		for (Entry<String, INBTTag<?>> entry : entrySet()) {
			if (entry.getKey() == null || entry.getValue() == null || !entry.getValue().isValid()) {
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
		int result = 0;
		for (Entry<String, INBTTag<?>> entry : entrySet()) {
			result += ((entry.getKey() == null ? 0 : entry.getKey().hashCode()) ^ (entry.getValue() == null ? 0 : entry.getValue().hashCode()));
		}
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof AbstractMapNBTTag)) {
			return false;
		}
		AbstractMapNBTTag other = (AbstractMapNBTTag) object;

		if (getValue0() == null ^ other.getValue0() == null) {
			return false;
		}

		if (getValue0() == null && other.getValue0() == null) {
			return true;
		}

		if (size() != other.size()) {
			return false;
		}

		for (Entry<String, INBTTag<?>> entry : entrySet()) {
			INBTTag<?> value0 = entry.getValue();

			if (!other.containsKey(entry.getKey())) {
				return false;
			}

			INBTTag<?> value1 = other.get(entry.getKey());
			if (value0 == null ? value1 != null : !value0.equals(value1)) {
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
			builder.append((Object) null);
		}
		else {
			builder.append("Map[");
			Iterator<Entry<String, INBTTag<?>>> iterator = entrySet().iterator();
			boolean first = true;
			while (iterator.hasNext()) {
				if (!first) {
					builder
							.append(',')
							.append(' ');
				}
				first = false;
				Entry<String, INBTTag<?>> entry = iterator.next();
				if (entry == null) {
					builder.append((Object) null);
				}
				else {
					builder
							.append("Entry[key=")
							.append(entry.getKey())
							.append(", value=")
							.append(entry.getValue())
							.append(']');
				}
			}
			builder.append(']');
		}
		builder.append(']');
		return builder.toString();
	}
}