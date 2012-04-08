package cn.kevin.rollbox.data_model;

import java.util.ArrayList;

import cn.kevin.rollbox.utils.Constants;

public class Switch {
	
	private int row;
	private int col;
	private boolean isPressed;
	private int type;
	private ArrayList<Bridge> bridges;
	
	public Switch(int i, int j, int t, ArrayList<Bridge> b){
		this.row = i;
		this.col = j;
		this.isPressed = false;
		this.bridges = b;
		this.type = t;
	}
	
	public Switch(){
		this(-1, -1, 2, null);
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public ArrayList<Bridge> getBridges() {
		return bridges;
	}

	public void setBridges(ArrayList<Bridge> bridges) {
		this.bridges = bridges;
	}

	public void press(ArrayList<String[]> gameMap){
		switch(type){
		case Constants.SWITCH_CLOSE:
			for(Bridge b: bridges){
				gameMap.get(b.getRow())[b.getCol()] = "0";
			}
			break;
		case Constants.SWITCH_OPEN:
			for(Bridge b: bridges){
				gameMap.get(b.getRow())[b.getCol()] = "5";
			}
			break;
		case Constants.SWITCH_CLOSE_OR_OPEN:
			for(Bridge b: bridges){
				if(b.blocked(gameMap)){
					gameMap.get(b.getRow())[b.getCol()] = "5";
				}else{
					gameMap.get(b.getRow())[b.getCol()] = "0";
				}
			}
			break;
		}
		
		this.isPressed = !isPressed;
	}
	
}
