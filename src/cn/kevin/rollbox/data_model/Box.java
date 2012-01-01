package cn.kevin.rollbox.data_model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import cn.kevin.rollbox.utils.Constants;

public class Box {
	
	private int state; 
	private int[] rows = new int[2]; //方块所占单元格行数
	private int[] cols = new int[2]; //方块所占单元格列数
	private Bitmap currentBox;
	private int x = 100;
	private int y = 50;
	
	
	public Box(){
		this.state = Constants.BOX_STATE_VERTICAL;

	}
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int[] getRows() {
		return rows;
	}
	public void setRows(int row1, int row2) {
		this.rows[0] = row1;
		this.rows[1] = row2;
	}
	public int[] getCols() {
		return cols;
	}
	public void setCols(int col1, int col2) {
		this.cols[0] = col1;
		this.cols[1] = col2;
	}
	public Bitmap getCurrentBox() {
		return currentBox;
	}
	public void setCurrentBox(Bitmap currentBox) {
		this.currentBox = currentBox;
	}	

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void drawMySelf(Canvas c, Paint p){
		c.drawBitmap(this.currentBox, x, y, p);
	}
	
	public String toString(){
		return "row{" + rows[0] + ", " + rows[1] + "}  col{" + cols[0] + ", " + cols[1] + "}";
	}
	
}
