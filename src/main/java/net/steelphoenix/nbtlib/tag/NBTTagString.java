package net.steelphoenix.nbtlib.tag;

import java.io.DataOutput;
import java.io.IOException;

import net.steelphoenix.nbtlib.AbstractNBTTag;
import net.steelphoenix.nbtlib.MalformedNBTException;
import net.steelphoenix.nbtlib.NBTTagType;

/**
 * A string tag.
 * This tag is always valid.
 *
 * @author SteelPhoenix
 */
public class NBTTagString extends AbstractNBTTag<String> {

	public static final NBTTagType TYPE = NBTTagType.STRING;

	public NBTTagString() {
		this("");
	}

	public NBTTagString(String value) {
		super(TYPE, value);
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

		output.writeUTF(getValue());
	}

	@Override
	public NBTTagString copy() {
		NBTTagString tag = new NBTTagString();
		tag.setValue(getValue0());
		return tag;
	}

	@Override
	public String asSNBT(boolean pretty) {
		// Preconditions
		if (!isValid()) {
			throw new MalformedNBTException("Tag is not valid");
		}

		String value = getValue();
		StringBuilder builder = new StringBuilder()
				.append(' ');
		char quote = '\0';
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if (c == '\\') {
				builder.append('\\');
			}
			else if (c == '\"' || c == '\'') {
				if (quote == '\0') {
					quote = c == '\"' ? '\'' : '\"';
				}
				if (quote == c) {
					builder.append('\\');
				}
			}
			builder.append(c);
		}
		if (quote == '\0') {
			quote = '\"';
		}
		builder.setCharAt(0, quote);
		return builder
				.append(quote)
				.toString();
	}
}
