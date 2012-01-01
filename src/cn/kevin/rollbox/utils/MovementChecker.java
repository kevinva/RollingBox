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

}
