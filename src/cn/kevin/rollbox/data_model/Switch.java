package cn.kevin.rollbox.data_model;

public class Switch {
	
	private int row;
	private int col;
	private boolean isPressed;
	private Bridge brige;
	
	public Switch(int i, int j, boolean flag, Bridge b){
		this.row = i;
		this.col = j;
		this.isPressed = flag;
		this.brige = b;
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

	public boolean isPressed() {
		return isPressed;
	}

	public void setPressed(boolean isPressed) {
		this.isPressed = isPressed;
	}

	public Bridge getBrige() {
		return brige;
	}

	public void setBrige(Bridge brige) {
		this.brige = brige;
	}
	
	
	
}
