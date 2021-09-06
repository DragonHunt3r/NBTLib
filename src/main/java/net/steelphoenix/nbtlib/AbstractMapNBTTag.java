package net.steelphoenix.nbtlib;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;

import net.steelphoenix.nbtlib.tag.NBTTagString;

/**
 * A base map NBT tag implementation.
 *
 * @param <V> Value type.
 * @author SteelPhoenix
 */
public abstract class AbstractMapNBTTag<V extends INBTTag<?>> extends AbstractNBTTag<Map<String, V>> implements IMapNBTTag<V> {

	private static final Pattern SIMPLE = Pattern.compile("[A-Za-z0-9._+-]+");

	protected AbstractMapNBTTag(NBTTagType type, Map<String, V> value) {
		super(type, value);
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
	public V get(Object key) {
		return getValue0().get(key);
	}

	@Override
	public V getOrDefault(Object key, V def) {
		return getValue0().getOrDefault(key, def);
	}

	@Override
	public V put(String key, V value) {
		return getValue0().put(key, value);
	}

	@Override
	public V putIfAbsent(String key, V value) {
		return getValue0().putIfAbsent(key, value);
	}

	@Override
	public void putAll(Map<? extends String, ? extends V> map) {
		getValue0().putAll(map);
	}

	@Override
	public V replace(String key, V value) {
		return getValue0().replace(key, value);
	}

	@Override
	public boolean replace(String key, V oldValue, V newValue) {
		return getValue0().replace(key, oldValue, newValue);
	}

	@Override
	public V remove(Object key) {
		return getValue0().remove(key);
	}

	@Override
	public boolean remove(Object key, Object value) {
		return getValue0().remove(key, value);
	}

	@Override
	public void clear() {
		getValue0().clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return getValue0().containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return getValue0().containsValue(value);
	}

	@Override
	public Set<String> keySet() {
		return getValue0().keySet();
	}

	@Override
	public Collection<V> values() {
		return getValue0().values();
	}

	@Override
	public Set<Entry<String, V>> entrySet() {
		return getValue0().entrySet();
	}

	@Override
	public void forEach(BiConsumer<? super String, ? super V> action) {
		getValue0().forEach(action);
	}

	@Override
	public void replaceAll(BiFunction<? super String, ? super V, ? extends V> function) {
		getValue0().replaceAll(function);
	}

	@Override
	public V merge(String key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		return getValue0().merge(key, value, remappingFunction);
	}

	@Override
	public V compute(String key, BiFunction<? super String, ? super V, ? extends V> remappingFunction) {
		return getValue0().compute(key, remappingFunction);
	}

	@Override
	public V computeIfAbsent(String key, Function<? super String, ? extends V> mappingFunction) {
		return getValue0().computeIfAbsent(key, mappingFunction);
	}

	@Override
	public V computeIfPresent(String key, BiFunction<? super String, ? super V, ? extends V> remappingFunction) {
		return getValue0().computeIfPresent(key, remappingFunction);
	}

	@Override
	public boolean isValid() {
		for (Entry<String, V> entry : entrySet()) {
			if (entry.getKey() == null || entry.getValue() == null || !entry.getValue().isValid()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = 0;
		for (Entry<String, V> entry : entrySet()) {
			result += ((entry.getKey() == null ? 0 : entry.getKey().hashCode()) ^ (entry.getValue() == null ? 0 : entry.getValue().hashCode()));
		}
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof IMapNBTTag)) {
			return false;
		}
		IMapNBTTag<?> other = (IMapNBTTag<?>) object;

		if (size() != other.size()) {
			return false;
		}

		for (Entry<String, V> entry : entrySet()) {
			V value0 = entry.getValue();

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
				.append(", value=Map[");
		Iterator<Entry<String, V>> iterator = entrySet().iterator();
		boolean first = true;
		while (iterator.hasNext()) {
			if (!first) {
				builder
						.append(',')
						.append(' ');
			}
			first = false;
			Entry<String, V> entry = iterator.next();
			builder
					.append("Entry[key=")
					.append(entry.getKey())
					.append(", value=")
					.append(entry.getValue())
					.append(']');
		}
		builder
				.append(']')
				.append(']');
		return builder.toString();
	}

	protected String asSNBT(boolean pretty, String prefix, String suffix) {
		// Preconditions
		if (!isValid()) {
			throw new MalformedNBTException("Tag is not valid");
		}

		// Empty
		if (isEmpty()) {
			return prefix + suffix;
		}


		String newLine = pretty ? System.lineSeparator() : "";

		StringJoiner joiner = new StringJoiner("," + newLine, prefix + newLine, newLine + suffix);
		for (Entry<String, V> entry : entrySet()) {
			// Cheeky NBTTagString so we do not have to rewrite formatting logic
			String snbt = (SIMPLE.matcher(entry.getKey()).matches() ? entry.getKey() : new NBTTagString(entry.getKey()).asSNBT(pretty)) + ':' + (pretty ? " " : "") + entry.getValue().asSNBT(pretty);
			if (pretty) {
				snbt = '\t' + snbt.replaceAll("\\R", System.lineSeparator() + '\t');
			}
			joiner.add(snbt);
		}
		return joiner.toString();
	}
}