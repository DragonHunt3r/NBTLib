package net.steelphoenix.nbtlib;

import java.util.Map;

/**
 * A map NBT tag.
 *
 * @param <V> Value type.
 * @author SteelPhoenix
 */
public interface IMapNBTTag<V extends INBTTag<?>> extends INBTTag<Map<String, V>>, Map<String, V> {
	// Nothing
}