package cn.kevin.rollbox.data_model;

public class Switch {
	
	private int row;
	private int col;
	private int type;
	private boolean isPressed;
	
	public Switch(int i, int j, int t, boolean flag){
		this.row = i;
		this.col = j;
		this.type = t;
		isPressed = flag;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isPressed() {
		return isPressed;
	}

	public void setPressed(boolean isPressed) {
		this.isPressed = isPressed;
	}
	
	
}
