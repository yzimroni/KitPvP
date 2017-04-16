package net.yzimroni.kitpvp.kit;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MaterialData {

	private final Material type;
	private byte data = 0;

	public MaterialData(Material type) {
		this(type, (byte) 0);
	}

	public MaterialData(Material type, byte data) {
		this.type = type;
		this.data = data;
	}

	public byte getData() {
		return data;
	}

	public Material getType() {
		return type;
	}

	public ItemStack toItemStack() {
		return new ItemStack(type, 1, data);
	}

	@Override
	public String toString() {
		return "MaterialData [type=" + type + ", data=" + data + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + data;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MaterialData other = (MaterialData) obj;
		if (data != other.data)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
