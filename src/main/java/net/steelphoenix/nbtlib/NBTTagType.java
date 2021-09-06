package net.steelphoenix.nbtlib;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.steelphoenix.nbtlib.tag.NBTTagByte;
import net.steelphoenix.nbtlib.tag.NBTTagByteArray;
import net.steelphoenix.nbtlib.tag.NBTTagCompound;
import net.steelphoenix.nbtlib.tag.NBTTagDouble;
import net.steelphoenix.nbtlib.tag.NBTTagEnd;
import net.steelphoenix.nbtlib.tag.NBTTagFloat;
import net.steelphoenix.nbtlib.tag.NBTTagInt;
import net.steelphoenix.nbtlib.tag.NBTTagIntArray;
import net.steelphoenix.nbtlib.tag.NBTTagList;
import net.steelphoenix.nbtlib.tag.NBTTagLong;
import net.steelphoenix.nbtlib.tag.NBTTagLongArray;
import net.steelphoenix.nbtlib.tag.NBTTagShort;
import net.steelphoenix.nbtlib.tag.NBTTagString;

/**
 * NBT Tag types.
 *
 * @author SteelPhoenix
 */
public enum NBTTagType {

	/**
	 * TAG_End
	 */
	END ((byte) 0, "TAG_End") {

		@Override
		public INBTTag<?> read(DataInput input, int depth, NBTSizeLimiter limiter) throws IOException {
			// Preconditions
			if (input == null) {
				throw new NullPointerException("Input cannot be null");
			}
			if (limiter == null) {
				throw new NullPointerException("Limiter cannot be null");
			}
			if (depth < 0) {
				throw new IllegalArgumentException("Depth cannot be negative");
			}

			limiter.addBytesRead(8L);
			return NBTTagEnd.getInstance();
		}
	},


	/**
	 * TAG_Byte
	 */
	BYTE ((byte) 1, "TAG_Byte") {

		@Override
		public INBTTag<?> read(DataInput input, int depth, NBTSizeLimiter limiter) throws IOException {
			// Preconditions
			if (input == null) {
				throw new NullPointerException("Input cannot be null");
			}
			if (limiter == null) {
				throw new NullPointerException("Limiter cannot be null");
			}
			if (depth < 0) {
				throw new IllegalArgumentException("Depth cannot be negative");
			}

			limiter.addBytesRead(9L);
			NBTTagByte tag = new NBTTagByte();
			tag.setValue0(Byte.valueOf(input.readByte()));
			return tag;
		}
	},


	/**
	 * TAG_Short
	 */
	SHORT ((byte) 2, "TAG_Short") {

		@Override
		public INBTTag<?> read(DataInput input, int depth, NBTSizeLimiter limiter) throws IOException {
			// Preconditions
			if (input == null) {
				throw new NullPointerException("Input cannot be null");
			}
			if (limiter == null) {
				throw new NullPointerException("Limiter cannot be null");
			}
			if (depth < 0) {
				throw new IllegalArgumentException("Depth cannot be negative");
			}

			limiter.addBytesRead(10L);
			NBTTagShort tag = new NBTTagShort();
			tag.setValue0(Short.valueOf(input.readShort()));
			return tag;
		}
	},


	/**
	 * TAG_Int
	 */
	INT ((byte) 3, "TAG_Int") {

		@Override
		public INBTTag<?> read(DataInput input, int depth, NBTSizeLimiter limiter) throws IOException {
			// Preconditions
			if (input == null) {
				throw new NullPointerException("Input cannot be null");
			}
			if (limiter == null) {
				throw new NullPointerException("Limiter cannot be null");
			}
			if (depth < 0) {
				throw new IllegalArgumentException("Depth cannot be negative");
			}

			limiter.addBytesRead(12L);
			NBTTagInt tag = new NBTTagInt();
			tag.setValue0(Integer.valueOf(input.readInt()));
			return tag;
		}
	},


	/**
	 * TAG_Long
	 */
	LONG ((byte) 4, "TAG_Long") {

		@Override
		public INBTTag<?> read(DataInput input, int depth, NBTSizeLimiter limiter) throws IOException {
			// Preconditions
			if (input == null) {
				throw new NullPointerException("Input cannot be null");
			}
			if (limiter == null) {
				throw new NullPointerException("Limiter cannot be null");
			}
			if (depth < 0) {
				throw new IllegalArgumentException("Depth cannot be negative");
			}

			limiter.addBytesRead(16L);
			NBTTagLong tag = new NBTTagLong();
			tag.setValue0(Long.valueOf(input.readLong()));
			return tag;
		}
	},


	/**
	 * TAG_Float
	 */
	FLOAT ((byte) 5, "TAG_Float") {

		@Override
		public INBTTag<?> read(DataInput input, int depth, NBTSizeLimiter limiter) throws IOException {
			// Preconditions
			if (input == null) {
				throw new NullPointerException("Input cannot be null");
			}
			if (limiter == null) {
				throw new NullPointerException("Limiter cannot be null");
			}
			if (depth < 0) {
				throw new IllegalArgumentException("Depth cannot be negative");
			}

			limiter.addBytesRead(12L);
			NBTTagFloat tag = new NBTTagFloat();
			tag.setValue0(Float.valueOf(input.readFloat()));
			return tag;
		}
	},

	/**
	 * TAG_Double
	 */
	DOUBLE ((byte) 6, "TAG_Double") {

		@Override
		public INBTTag<?> read(DataInput input, int depth, NBTSizeLimiter limiter) throws IOException {
			// Preconditions
			if (input == null) {
				throw new NullPointerException("Input cannot be null");
			}
			if (limiter == null) {
				throw new NullPointerException("Limiter cannot be null");
			}
			if (depth < 0) {
				throw new IllegalArgumentException("Depth cannot be negative");
			}

			limiter.addBytesRead(16L);
			NBTTagDouble tag = new NBTTagDouble();
			tag.setValue0(Double.valueOf(input.readDouble()));
			return tag;
		}
	},

	/**
	 * TAG_Byte_Array
	 */
	BYTE_ARRAY ((byte) 7, "TAG_Byte_Array") {

		@Override
		public INBTTag<?> read(DataInput input, int depth, NBTSizeLimiter limiter) throws IOException {
			// Preconditions
			if (input == null) {
				throw new NullPointerException("Input cannot be null");
			}
			if (limiter == null) {
				throw new NullPointerException("Limiter cannot be null");
			}
			if (depth < 0) {
				throw new IllegalArgumentException("Depth cannot be negative");
			}

			limiter.addBytesRead(24L);
			int len = input.readInt();
			if (len < 0) {
				throw new MalformedNBTException("Negative array size");
			}
			limiter.addBytesRead(1L * len);
			List<NBTTagByte> list = new ArrayList<>(len);
			for (int i = 0; i < len; i++) {
				list.add(new NBTTagByte(input.readByte()));
			}
			NBTTagByteArray tag = new NBTTagByteArray();
			tag.setValue0(list);
			return tag;
		}
	},

	/**
	 * TAG_String
	 */
	STRING ((byte) 8, "TAG_String") {

		@Override
		public INBTTag<?> read(DataInput input, int depth, NBTSizeLimiter limiter) throws IOException {
			// Preconditions
			if (input == null) {
				throw new NullPointerException("Input cannot be null");
			}
			if (limiter == null) {
				throw new NullPointerException("Limiter cannot be null");
			}
			if (depth < 0) {
				throw new IllegalArgumentException("Depth cannot be negative");
			}

			limiter.addBytesRead(36L);
			String string = input.readUTF();
			limiter.addBytesRead(16L * string.length());
			NBTTagString tag = new NBTTagString();
			tag.setValue0(string);
			return tag;
		}
	},

	/**
	 * TAG_List
	 */
	LIST ((byte) 9, "TAG_List") {

		@Override
		public INBTTag<?> read(DataInput input, int depth, NBTSizeLimiter limiter) throws IOException {
			// Preconditions
			if (input == null) {
				throw new NullPointerException("Input cannot be null");
			}
			if (limiter == null) {
				throw new NullPointerException("Limiter cannot be null");
			}
			if (depth < 0) {
				throw new IllegalArgumentException("Depth cannot be negative");
			}
			if (depth > 512) {
				throw new MalformedNBTException("Depth exceeds max depth: " + depth);
			}

			limiter.addBytesRead(37L);
			byte id = input.readByte();
			int size = input.readInt();
			if (size < 0) {
				throw new MalformedNBTException("Negative list size");
			}
			limiter.addBytesRead(32L * size);
			NBTTagType type = NBTTagType.fromId(id);

			// Empty list may have an invalid type id or be of type END
			if ((type == null || type == NBTTagType.END) && size > 0) {
				throw new MalformedNBTException("Invalid list type: " + id);
			}

			List<INBTTag<?>> list = new ArrayList<>(size);
			for (int i = 0; i < size; i++) {
				list.add(type.read(input, depth + 1, limiter));
			}
			NBTTagList tag = new NBTTagList();
			tag.setElementType(type);
			tag.setValue0(list);
			return tag;
		}
	},

	/**
	 * TAG_Compound
	 */
	COMPOUND ((byte) 10, "TAG_Compound") {

		@Override
		public INBTTag<?> read(DataInput input, int depth, NBTSizeLimiter limiter) throws IOException {
			// Preconditions
			if (input == null) {
				throw new NullPointerException("Input cannot be null");
			}
			if (limiter == null) {
				throw new NullPointerException("Limiter cannot be null");
			}
			if (depth < 0) {
				throw new IllegalArgumentException("Depth cannot be negative");
			}
			if (depth > 512) {
				throw new MalformedNBTException("Depth exceeds max depth: " + depth);
			}

			limiter.addBytesRead(48L);
			Map<String, INBTTag<?>> map = new LinkedHashMap<>();
			while (true) {
				byte id = input.readByte();
				NBTTagType type = NBTTagType.fromId(id);
				if (type == null) {
					throw new MalformedNBTException("Unknown tag type: " + id);
				}

				// Last entry
				if (type == NBTTagType.END) {
					break;
				}

				String key = input.readUTF();
				limiter.addBytesRead(28L + 2L * key.length());
				INBTTag<?> tag = type.read(input, depth + 1, limiter);
				if (map.put(key, tag) != null) {
					limiter.addBytesRead(36L);
				}
			}
			NBTTagCompound tag = new NBTTagCompound();
			tag.setValue0(map);
			return tag;
		}
	},


	/**
	 * TAG_Int_Array
	 */
	INT_ARRAY ((byte) 11, "TAG_Int_Array") {

		@Override
		public INBTTag<?> read(DataInput input, int depth, NBTSizeLimiter limiter) throws IOException {
			// Preconditions
			if (input == null) {
				throw new NullPointerException("Input cannot be null");
			}
			if (limiter == null) {
				throw new NullPointerException("Limiter cannot be null");
			}
			if (depth < 0) {
				throw new IllegalArgumentException("Depth cannot be negative");
			}

			limiter.addBytesRead(24L);
			int len = input.readInt();
			if (len < 0) {
				throw new MalformedNBTException("Negative array size");
			}
			limiter.addBytesRead(4L * len);
			List<NBTTagInt> list = new ArrayList<>(len);
			for (int i = 0; i < len; i++) {
				list.add(new NBTTagInt(input.readInt()));
			}
			NBTTagIntArray tag = new NBTTagIntArray();
			tag.setValue0(list);
			return tag;
		}
	},

	/**
	 * TAG_Long_Array
	 */
	LONG_ARRAY ((byte) 12, "TAG_Long_Array") {

		@Override
		public INBTTag<?> read(DataInput input, int depth, NBTSizeLimiter limiter) throws IOException {
			// Preconditions
			if (input == null) {
				throw new NullPointerException("Input cannot be null");
			}
			if (limiter == null) {
				throw new NullPointerException("Limiter cannot be null");
			}
			if (depth < 0) {
				throw new IllegalArgumentException("Depth cannot be negative");
			}

			// No clue why but this is what Minecraft does
			limiter.addBytesRead(24L);
			int len = input.readInt();
			if (len < 0) {
				throw new MalformedNBTException("Negative array size");
			}
			limiter.addBytesRead(8L * len);
			List<NBTTagLong> list = new ArrayList<>(len);
			for (int i = 0; i < len; i++) {
				list.add(new NBTTagLong(input.readLong()));
			}
			NBTTagLongArray tag = new NBTTagLongArray();
			tag.setValue0(list);
			return tag;
		}
	};

	private final byte id;
	private final String name;

	private NBTTagType(byte id, String name) {
		if (name == null) {
			throw new NullPointerException("Name cannot be null");
		}

		this.id = id;
		this.name = name;
	}

	/**
	 * Get the type id.
	 *
	 * @return the id.
	 */
	public byte getId() {
		return id;
	}

	/**
	 * Get the type name.
	 *
	 * @return the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Read a tag from data input.
	 *
	 * @param input Target input.
	 * @param depth Current depth.
	 * @param limiter Size limiter.
	 * @return the created instance.
	 * @throws IOException if an I/O error occurs.
	 */
	public abstract INBTTag<?> read(DataInput input, int depth, NBTSizeLimiter limiter) throws IOException;

	/**
	 * Get a type from id.
	 *
	 * @param id Target id.
	 * @return the type or null if no type with the given id exists.
	 */
	public static NBTTagType fromId(byte id) {
		for (NBTTagType type : values()) {
			if (id == type.getId()) {
				return type;
			}
		}
		return null;
	}

	/**
	 * Get a type from name.
	 *
	 * @param name Target name.
	 * @return the type or null if no type with the given name exists.
	 */
	public static NBTTagType fromName(String name) {
		// Preconditions
		if (name == null) {
			throw new NullPointerException("Name cannot be null");
		}

		for (NBTTagType type : values()) {
			if (name.equals(type.getName())) {
				return type;
			}
		}
		return null;
	}
}
