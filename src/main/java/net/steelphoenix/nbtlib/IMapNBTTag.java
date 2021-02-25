package net.steelphoenix.nbtlib;

import java.util.Map;

/**
 * A map NBT tag.
 *
 * @param <K> Key type.
 * @param <V> Value type.
 * @author SteelPhoenix
 */
public interface IMapNBTTag<K, V> extends INBTTag<Map<K, V>>, Map<K, V> {
	// Nothing
}