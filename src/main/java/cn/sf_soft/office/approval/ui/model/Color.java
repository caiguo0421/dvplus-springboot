package cn.sf_soft.office.approval.ui.model;


public enum Color {
	
	// BLACK
	BLACK(0, "BLACK"),
	// RED
	RED( 16711680, "RED"),
	//GRAY
	GRAY( 15921906, "GRAY"),
	// YELLOW
	YELLOW(16777164,"YELLOW");
	

	private final int code;
	private final String text;

	private Color(int code, String text) {
		this.code = code;
		this.text = text;
	}

	public int getCode() {
		return this.code;
	}

	public String getText() {
		return this.text;
	}

	

	@Override
	public String toString() {
		return this.code + "(" + this.text + ")";
	}


}
