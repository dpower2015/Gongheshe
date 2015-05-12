package com.gongheshe.model;

public class TypeClassMod {

	public int id;
	public String name;
	public int oneTypeIndex;
	public int parentTypeIndex;
	public String parentTypeIndexName;
	public int sort;
	@Override
	public String toString() {
		return "TypeClassMod [id=" + id + ", name=" + name + ", oneTypeIndex="
				+ oneTypeIndex + ", parentTypeIndex=" + parentTypeIndex
				+ ", parentTypeIndexName=" + parentTypeIndexName + ", sort="
				+ sort + "]";
	}
	
	
}
