package cn.kevin.rollbox.data_model;

import java.util.ArrayList;

public class Bridge {
	
	private int row;
	private int col;
	
	public Bridge(int _row, int _col){
		this.row = _row;
		this.col = _col;
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
	
	public String toString(){
		return "(" + row + ", " + col + ")";
	}
	
	//ÇÅÊÇ·ñ±»×è¶Ï
	public boolean blocked(ArrayList<String[]> gameMap){
		String[] line = gameMap.get(row);
		if(line[col].equals("5")){
			return false;
		}
		return true;
	}
	
	
}
