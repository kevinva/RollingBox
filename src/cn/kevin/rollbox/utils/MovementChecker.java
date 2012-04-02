package cn.kevin.rollbox.utils;

import java.util.ArrayList;

import cn.kevin.rollbox.activity.GameActivity;
import cn.kevin.rollbox.data_model.Box;

public class MovementChecker {
	
	private static MovementChecker instance = null;
	private GameActivity gameActivity = null;
	
	private MovementChecker(){

	}
	
	public static MovementChecker getInstance(GameActivity activity ){
		synchronized(MovementChecker.class){
			if(instance == null){
				instance = new MovementChecker();
			}
			if(instance.gameActivity != activity){
				instance.gameActivity = activity;
			}
			return instance;
		}
	}
	
	public boolean canMove_v2(int boxState, int preDirection){	
		Box box = this.gameActivity.box;
		int[] rows = box.getRows();
		int[] cols = box.getCols();
		int[] tempRows = new int[]{rows[0], rows[1]};
		int[] tempCols = new int[]{cols[0], cols[1]};
		if(boxState == Constants.BOX_STATE_VERTICAL){
			switch(preDirection){
			case Constants.DIRECTION_UP:
				tempRows[0]--;
				tempRows[1] -= 2;
				break;
			case Constants.DIRECTION_DOWN:
				tempRows[0]++;
				tempRows[1] += 2;
				break;				
			case Constants.DIRECTION_LEFT:
				tempCols[0]--;
				tempCols[1] -= 2;
				break;
			case Constants.DIRECTION_RIGHT:
				tempCols[0]++;
				tempCols[1] += 2;
				break;
			}
		}else if(boxState == Constants.BOX_STATE_HORIZONTAL_X){
			switch(preDirection){
			case Constants.DIRECTION_UP:
				tempRows[0]--;
				tempRows[1]--;
				break;
			case Constants.DIRECTION_DOWN:
				tempRows[0]++;
				tempRows[1]++;
				break;				
			case Constants.DIRECTION_LEFT:
				if(tempCols[0] < tempCols[1]){
					tempCols[0]--;
					tempCols[1] -= 2;
				}else{
					tempCols[0] -= 2;
					tempCols[1]--;
				}
				break;
			case Constants.DIRECTION_RIGHT:
				if(tempCols[0] < tempCols[1]){
					tempCols[0] += 2;
					tempCols[1]++;
				}else{
					tempCols[0]++;
					tempCols[1] += 2;
				}
				break;
			}
		}else if(boxState == Constants.BOX_STATE_HORIZONTAL_Z){
			switch(preDirection){
			case Constants.DIRECTION_UP:
				if(tempRows[0] < tempRows[1]){
					tempRows[0]--;
					tempRows[1] -= 2;
				}else{
					tempRows[0] -= 2;
					tempRows[1]--;
				}
				break;
			case Constants.DIRECTION_DOWN:
				if(tempRows[0] < tempRows[1]){
					tempRows[0] += 2;
					tempRows[1]++;
				}else{
					tempRows[0]++;
					tempRows[1] += 2;
				}
				break;				
			case Constants.DIRECTION_LEFT:
				tempCols[0]--;
				tempCols[1]--;
				break;
			case Constants.DIRECTION_RIGHT:
				tempCols[0]++;
				tempCols[1]++;
				break;
			}
		}
		
		ArrayList<String[]> currentMap = this.gameActivity.currentMap;
		String[] line1 = currentMap.get(tempRows[0]);
		String[] line2 = currentMap.get(tempRows[1]);
		//System.out.println(line1[tempCols[0]] + ", " + line1[tempCols[1]] + ", " + line2[tempCols[0]] + ", " + line2[tempCols[1]]);
		if(line1[tempCols[0]].equals("0") || line1[tempCols[1]].equals("0")	|| line2[tempCols[0]].equals("0") || line2[tempCols[1]].equals("0")){
			return false;
		}

		return true;
	}
	
	//是否触碰了圆形开关
	public boolean moveToRoundSwitch(){
		Box box = this.gameActivity.box;
		int[] rows = box.getRows();
		int[] cols = box.getCols();
		ArrayList<String[]> currentMap = this.gameActivity.currentMap;
		String[] line1 = currentMap.get(rows[0]);
		String[] line2 = currentMap.get(rows[1]);
		if(line1[cols[0]].equals("2") || line1[cols[1]].equals("2") || line2[cols[0]].equals("2") || line2[cols[1]].equals("2")){
			return true;
		}
		return false;
	}
	
	//是否触碰了X型开关
	public boolean moveToXSwitch(){
		Box box = this.gameActivity.box;
		int[] rows = box.getRows();
		int[] cols = box.getCols();
		ArrayList<String[]> currentMap = this.gameActivity.currentMap;
		String[] line1 = currentMap.get(rows[0]);
		String[] line2 = currentMap.get(rows[1]);
		if(box.getState() == Constants.BOX_STATE_VERTICAL){
			if(line1[cols[0]].equals("3") || line1[cols[1]].equals("3") || line2[cols[0]].equals("3") || line2[cols[1]].equals("3")){
				return true;
			}else{
				return false;
			}			
		}else{
			return false;
		}
		
	}
	
	/*
	public boolean canMove(int boxState, int direction){
		boolean flag = true;
		Box box = this.gameActivity.box;
		ArrayList<String[]> currentMap = this.gameActivity.currentMap;
		int[] rows = box.getRows();
		int[] cols = box.getCols();
		String brick1;
		String brick2;
		String[] line1;
		String[] line2;
		String[] line;
		
		if(boxState == Constants.BOX_STATE_VERTICAL){
			switch(direction){
			case Constants.DIRECTION_UP:
				line1 = currentMap.get(rows[0] - 1);
				line2 = currentMap.get(rows[0] - 2);
				brick1 = line1[cols[0]];
				brick2 = line2[cols[0]];
				//System.out.println("brick1: " + brick1 + ", brick2: " + brick2);
				if(brick1.equals("0") || (!(brick1.equals("0")) && brick2.equals("0"))){
					flag = false;
				}
				break;
			case Constants.DIRECTION_RIGHT:
				line = currentMap.get(rows[0]);
				brick1 = line[cols[0] + 1];
				brick2 = line[cols[0] + 2];
				if(brick1.equals("0") || (!(brick1.equals("0")) && brick2.equals("0"))){
					flag = false;
				}
				break;
			case Constants.DIRECTION_DOWN:
				line1 = currentMap.get(rows[0] + 1);
				line2 = currentMap.get(rows[0] + 2);
				brick1 = line1[cols[0]];
				brick2 = line2[cols[0]];
				if(brick1.equals("0") || (!(brick1.equals("0")) && brick2.equals("0"))){
					flag = false;
				}
				break;
			case Constants.DIRECTION_LEFT:
				line = currentMap.get(rows[0]);
				brick1 = line[cols[0] - 1];
				brick2 = line[cols[0] - 2];
				if(brick1.equals("0") || (!(brick1.equals("0")) && brick2.equals("0"))){
					flag = false;
				}
				break;
			}
			
		}else if(boxState == Constants.BOX_STATE_HORIZONTAL_X){
			int index;
			switch(direction){
			case Constants.DIRECTION_UP:
				line = currentMap.get(rows[0] - 1);
				brick1 = line[cols[0]];
				brick2 = line[cols[1]];
				if(brick1.equals("0") || brick2.equals("0")){
					flag = false;
				}
				break;
			case Constants.DIRECTION_RIGHT:
				line = currentMap.get(rows[0]);
				index = Math.max(cols[0], cols[1]);
				brick1 = line[index + 1];
				if(brick1.equals("0")){
					flag = false;
				}
				break;
			case Constants.DIRECTION_DOWN:
				line = currentMap.get(rows[0] + 1);
				brick1 = line[cols[0]];
				brick2 = line[cols[1]];
				if(brick1.equals("0") || brick2.equals("0")){
					flag = false;
				}
				break;
			case Constants.DIRECTION_LEFT:
				line = currentMap.get(rows[0]);
				index = Math.min(cols[0], cols[1]);
				brick1 = line[index - 1];
				if(brick1.equals("0")){
					flag = false;
				}
				break;
			}
			
		}else{
			int index;
			switch(direction){
			case Constants.DIRECTION_UP:
				index = Math.min(rows[0], rows[1]);
				line = currentMap.get(index - 1);
				brick1 = line[cols[0]];
				if(brick1.equals("0")){
					flag = false;
				}
				break;
			case Constants.DIRECTION_RIGHT:
				line1 = currentMap.get(rows[0]);
				line2 = currentMap.get(rows[1]);
				brick1 = line1[cols[0] + 1];
				brick2 = line2[cols[0] + 1];
				if(brick1.equals("0") || brick2.equals("0")){
					flag = false;
				}
				break;
			case Constants.DIRECTION_DOWN:
				index = Math.max(rows[0], rows[1]);
				line = currentMap.get(index + 1);
				brick1 = line[cols[0]];
				if(brick1.equals("0")){
					flag = false;
				}
				break;
			case Constants.DIRECTION_LEFT:
				line1 = currentMap.get(rows[0]);
				line2 = currentMap.get(rows[1]);
				brick1 = line1[cols[0] - 1];
				brick2 = line2[cols[0] - 1];
				if(brick1.equals("0") || brick2.equals("0")){
					flag = false;
				}
				break;
			}
		}
		System.out.println("can move? " + flag);
		return flag;
			
	}
	*/

}
