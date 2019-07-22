package cn.sf_soft.mobile.documentBuffer.model;

public enum AppBoardFieldType {
	// Property
	Property((short) 1, "Property"),
	// Label
	Label((short) 2, "Label"),
	// Tail
	Tail((short) 3, "Tail"),
	// Subobject
	Subobject((short) 4, "Subobject"),
	// Attachment
	Attachment((short) 5, "Attachment"),
	// Row
	Row((short) 6, "Row");

	private final short code;
	private final String text;

	private AppBoardFieldType(short code, String text) {
		this.code = code;
		this.text = text;
	}

	public short getCode() {
		return this.code;
	}

	public String getText() {
		return this.text;
	}

	public static AppBoardFieldType valueOf(short code) {
		switch (code) {
		case 1:
			return Property;
		case 2:
			return Label;
		case 3:
			return Tail;
		case 4:
			return Subobject;
		case 5:
			return Attachment;
		case 6:
			return Row;
		default:
			return null;
		}
	}

	@Override
	public String toString() {
		return this.code + "(" + this.text + ")";
	}
}
