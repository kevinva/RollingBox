package cn.kevin.rollbox.thread;

import java.util.Iterator;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import cn.kevin.rollbox.R;
import cn.kevin.rollbox.data_model.Switch;
import cn.kevin.rollbox.utils.Constants;
import cn.kevin.rollbox.utils.MovementChecker;
import cn.kevin.rollbox.view.GameView;

public class GameViewDrawThread extends Thread {

	private boolean flag;
	private int span;
	private int span1;
	private SurfaceHolder sfh;
	private GameView gameView;
	private int direction = -1;
	
	public GameViewDrawThread(SurfaceHolder holder, GameView v, int d){
		this.sfh = holder;
		this.gameView = v;
		this.flag = true;
		this.direction = d;
		this.span1 = 100;
		this.span = 10;
	}
	
	public void run(){
		Canvas c;
		int[] tempRows = this.gameView.gameActivity.box.getRows();
		int[] tempCols = this.gameView.gameActivity.box.getCols();
		while(flag){
			//System.out.println("outer loop");
			if(direction == Constants.DIRECTION_UP){
				if(this.gameView.gameActivity.box.getState() == Constants.BOX_STATE_VERTICAL){
					this.rollingBox(Constants.box_y_z_up, false);
					this.gameView.gameActivity.box.setState(Constants.BOX_STATE_HORIZONTAL_Z);					
					tempRows[0]--;
					tempRows[1] -= 2;	
					
				}else if(this.gameView.gameActivity.box.getState() == Constants.BOX_STATE_HORIZONTAL_X){
					this.rollingBox(Constants.box_x_up, false);
					this.gameView.gameActivity.box.setState(Constants.BOX_STATE_HORIZONTAL_X);					
					tempRows[0]--;
					tempRows[1]--;	
					
				}else if(this.gameView.gameActivity.box.getState() == Constants.BOX_STATE_HORIZONTAL_Z){
					this.rollingBox(Constants.box_y_z_down, true);
					this.gameView.gameActivity.box.setState(Constants.BOX_STATE_VERTICAL);					
					if(tempRows[0] < tempRows[1]){
						tempRows[0]--;
						tempRows[1] -= 2;
					}else{
						tempRows[0] -= 2;
						tempRows[1]--;
					}
					
				}
				
				this.refreshSwitchAndBridge();
				
			}else if(direction == Constants.DIRECTION_LEFT){
				if(this.gameView.gameActivity.box.getState() == Constants.BOX_STATE_VERTICAL){
					this.rollingBox(Constants.box_y_x_left, false);
					this.gameView.gameActivity.box.setState(Constants.BOX_STATE_HORIZONTAL_X);					
					tempCols[0]--;
					tempCols[1] -= 2;	
					
				}else if(this.gameView.gameActivity.box.getState() == Constants.BOX_STATE_HORIZONTAL_X){
					this.rollingBox(Constants.box_y_x_right, true);
					this.gameView.gameActivity.box.setState(Constants.BOX_STATE_VERTICAL);					
					if(tempCols[0] < tempCols[1]){
						tempCols[0]--;
						tempCols[1] -= 2;
					}else{
						tempCols[0] -= 2;
						tempCols[1]--;
					}	
					
				}else if(this.gameView.gameActivity.box.getState() == Constants.BOX_STATE_HORIZONTAL_Z){
					this.rollingBox(Constants.box_z_left, false);
					this.gameView.gameActivity.box.setState(Constants.BOX_STATE_HORIZONTAL_Z);					
					tempCols[0]--;
					tempCols[1]--;
					
				}
				
				this.refreshSwitchAndBridge();
				
			}else if(direction == Constants.DIRECTION_DOWN){
				if(this.gameView.gameActivity.box.getState() == Constants.BOX_STATE_VERTICAL){
					this.rollingBox(Constants.box_y_z_down, false);
					this.gameView.gameActivity.box.setState(Constants.BOX_STATE_HORIZONTAL_Z);					
					tempRows[0]++;
					tempRows[1] += 2;	
					
				}else if(this.gameView.gameActivity.box.getState() == Constants.BOX_STATE_HORIZONTAL_X){
					this.rollingBox(Constants.box_x_up, true);
					this.gameView.gameActivity.box.setState(Constants.BOX_STATE_HORIZONTAL_X);					
					tempRows[0]++;
					tempRows[1]++;
					
				}else if(this.gameView.gameActivity.box.getState() == Constants.BOX_STATE_HORIZONTAL_Z){
					this.rollingBox(Constants.box_y_z_up, true);
					this.gameView.gameActivity.box.setState(Constants.BOX_STATE_VERTICAL);					
					if(tempRows[0] < tempRows[1]){
						tempRows[0] += 2;
						tempRows[1]++;
					}else{
						tempRows[0]++;
						tempRows[1] += 2;
					}
					
				}
				
				this.refreshSwitchAndBridge();
				
			}else if(direction == Constants.DIRECTION_RIGHT){
				if(this.gameView.gameActivity.box.getState() == Constants.BOX_STATE_VERTICAL){
					this.rollingBox(Constants.box_y_x_right, false);
					this.gameView.gameActivity.box.setState(Constants.BOX_STATE_HORIZONTAL_X);					
					tempCols[0]++;
					tempCols[1] += 2;
					
				}else if(this.gameView.gameActivity.box.getState() == Constants.BOX_STATE_HORIZONTAL_X){
					this.rollingBox(Constants.box_y_x_left, true);
					this.gameView.gameActivity.box.setState(Constants.BOX_STATE_VERTICAL);					
					if(tempCols[0] < tempCols[1]){
						tempCols[0] += 2;
						tempCols[1]++;
					}else{
						tempCols[0]++;
						tempCols[1] += 2;
					}
					
				}else if(this.gameView.gameActivity.box.getState() == Constants.BOX_STATE_HORIZONTAL_Z){
					this.rollingBox(Constants.box_z_left, true);
					this.gameView.gameActivity.box.setState(Constants.BOX_STATE_HORIZONTAL_Z);					
					tempCols[0]++;
					tempCols[1]++;
				}
				
				this.refreshSwitchAndBridge();
				
			}else{
				c = null;
				try{
					c = this.sfh.lockCanvas(null);				
					if(this.gameView.gameActivity.box.getState() == Constants.BOX_STATE_VERTICAL){
						this.gameView.myDraw(c, R.drawable.box_y);
					}else if(this.gameView.gameActivity.box.getState() == Constants.BOX_STATE_HORIZONTAL_Z){
						this.gameView.myDraw(c, R.drawable.box_z);
					}else{
						this.gameView.myDraw(c, R.drawable.box_x);
					}
					
					this.gameView.tempDraw(c, this.gameView.gameActivity.box.getState());
					
				}finally{
					if(c != null){
						this.sfh.unlockCanvasAndPost(c);
					}
				}
			}
			
			
			//System.out.println("Box:" + this.gameView.gameActivity.box);
			
			//胜利过关
			if(this.gameView.gameActivity.box.getState() == Constants.BOX_STATE_VERTICAL &&
					tempRows[0] == this.gameView.endPointRow && tempCols[0] == this.gameView.endPonitCol){
				this.gameView.gameActivity.mHandler.sendEmptyMessage(Constants.MESSAGE_LEVEL_CLEAR);
				//this.flag = false;
			}
			
			try {
				Thread.sleep(span1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
	private void rollingBox(int[] bitmaps, boolean invert){
		Canvas canvas;
		for(int i = 0; i < bitmaps.length; i++){
			//System.out.println("inner loop");
			canvas = null;
			
			synchronized(this.sfh){
				try{
					canvas = this.sfh.lockCanvas(null);	
					if(!invert){						
						this.gameView.myDraw(canvas, bitmaps[i]);
					}else{
						this.gameView.myDraw(canvas, bitmaps[bitmaps.length - 1 - i]);
					}
					
				}finally{
					if(canvas != null){
						this.sfh.unlockCanvasAndPost(canvas);
					}
				}
			}	
			
			try {
				Thread.sleep(span);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.direction = -1; //每次翻动之后direction都归-1
		
		System.out.println("Box:" + this.gameView.gameActivity.box);
	}
	
	private void refreshSwitchAndBridge(){
		int[] tempRows = this.gameView.gameActivity.box.getRows();
		int[] tempCols = this.gameView.gameActivity.box.getCols();		
		if(MovementChecker.getInstance(this.gameView.gameActivity).moveToRoundSwitch() ||
				MovementChecker.getInstance(this.gameView.gameActivity).moveToXSwitch()){
			Iterator<Switch> itera = this.gameView.gameActivity.switchList.iterator();
			Switch found = null;
			while(itera.hasNext()){
				Switch s = itera.next();
				if(tempRows[0] == s.getRow() || tempRows[1] == s.getRow()){
					if(tempCols[0] == s.getCol() || tempCols[1] == s.getCol()){
						found = s;
						break;
					}
				}
			}
			
			if(found != null){
				if(!found.isPressed()){
					//打开桥
					found.openBridge(this.gameView.gameActivity.currentMap);
					found.setPressed(true);
				}else{
					//关闭桥
					found.closeBridge(this.gameView.gameActivity.currentMap);
					found.setPressed(false);
				}
			}
		}
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	

}
