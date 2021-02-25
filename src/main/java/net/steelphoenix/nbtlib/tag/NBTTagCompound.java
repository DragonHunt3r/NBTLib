package net.steelphoenix.nbtlib.tag;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import net.steelphoenix.nbtlib.AbstractMapNBTTag;
import net.steelphoenix.nbtlib.INBTTag;
import net.steelphoenix.nbtlib.MalformedNBTException;
import net.steelphoenix.nbtlib.NBTTagType;

/**
 * A compound tag.
 * This tag is valid if a value is set, all entries have non-null keys and values and all values are valid.
 *
 * @author SteelPhoenix
 */
public class NBTTagCompound extends AbstractMapNBTTag {

	public static final NBTTagType TYPE = NBTTagType.COMPOUND;
	private static final Pattern SIMPLE = Pattern.compile("[A-Za-z0-9._+-]+");

	public NBTTagCompound() {
		super(TYPE);
	}

	public NBTTagCompound(Map<String, INBTTag<?>> value) {
		super(TYPE);
		setValue(value);
	}

	@Override
	public Map<String, INBTTag<?>> getValue() {
		Map<String, INBTTag<?>> map = new LinkedHashMap<>();
		forEach((k, v) -> map.put(k, v.copy()));
		return map;
	}

	@Override
	public void setValue(Map<String, INBTTag<?>> value) {
		// Preconditions
		if (value == null) {
			throw new NullPointerException("Value cannot be null");
		}

		Map<String, INBTTag<?>> map = new LinkedHashMap<>();
		value.forEach((k, v) -> map.put(k, v.copy()));
		setValue(map);
	}

	@Override
	public void write(DataOutput output) throws IOException {
		// Preconditions
		if (output == null) {
			throw new NullPointerException("Output cannot be null");
		}
		if (!isValid()) {
			throw new MalformedNBTException("Tag is not valid");
		}

		for (Entry<String, INBTTag<?>> entry : super.getValue().entrySet()) {
			INBTTag<?> tag = entry.getValue();
			output.writeByte(tag.getTypeId());

			// This should not happen when used properly
			// The last tag is an end tag to mark the end of the compound tag.
			// We should not encounter it while iterating over the tag entries.
			if (tag.getType() == NBTTagType.END) {
				continue;
			}

			output.writeUTF(entry.getKey());
			tag.write(output);
		}

		// Last entry
		output.writeByte(NBTTagType.END.getId());
	}

	@Override
	public NBTTagCompound copy() {
		NBTTagCompound tag = new NBTTagCompound();

		// Create a copy if there is a value set
		tag.setValue0(getValue0() == null ? null : getValue());

		return tag;
	}

	@Override
	public String asSNBT() {
		// Preconditions
		if (!isValid()) {
			throw new MalformedNBTException("Tag is not valid");
		}

		StringBuilder builder = new StringBuilder();
		builder.append('{');
		Iterator<Entry<String, INBTTag<?>>> iterator = entrySet().iterator();
		boolean first = true;
		while (iterator.hasNext()) {
			if (!first) {
				builder.append(',');
			}
			first = false;
			Entry<String, INBTTag<?>> entry = iterator.next();
			builder
					// Cheeky NBTTagString so we do not have to rewrite formatting logic
					.append(SIMPLE.matcher(entry.getKey()).matches() ? entry.getKey() : new NBTTagString(entry.getKey()).asSNBT())
					.append(':')
					.append(entry.getValue().asSNBT());
		}
		builder.append('}');
		return builder.toString();
	}
}