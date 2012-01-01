package cn.kevin.rollbox.view;


import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import cn.kevin.rollbox.R;
import cn.kevin.rollbox.activity.GameActivity;
import cn.kevin.rollbox.thread.GameViewDrawThread;
import cn.kevin.rollbox.utils.Constants;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

	private Bitmap brick;
	private Bitmap brick_orange;
	private Bitmap brick_bridge;
	private Bitmap brick_switch_round;
	private Bitmap brick_switch_x;
	
	private Paint paint;
	private int initX = 0;
	private int initY = 120;

	public GameViewDrawThread gameViewDrawThread;	
	public GameActivity gameActivity;	
	public int endPointRow;
	public int endPonitCol;
	
	public GameView(GameActivity activity){
		super(activity);
		this.setKeepScreenOn(true); 
		this.gameActivity = activity;
		this.gameViewDrawThread = new GameViewDrawThread(this.getHolder(), this, -1);
		this.getHolder().addCallback(this);
		this.initMapBitmap();
	}
	
	private void initMapBitmap(){
		brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick);
		this.brick_orange = BitmapFactory.decodeResource(getResources(), R.drawable.brick_orange);
		this.brick_bridge = BitmapFactory.decodeResource(getResources(), R.drawable.brick_bridge);
		this.brick_switch_round = BitmapFactory.decodeResource(getResources(), R.drawable.brick_switch_round);
		this.brick_switch_x = BitmapFactory.decodeResource(getResources(), R.drawable.brick_switch_x);
		paint = new Paint();
		paint.setAntiAlias(true);
	}
	
	private void drawMap(Canvas c){		
		ArrayList<String[]> currentMap = this.gameActivity.currentMap;
		int row = 0;
		for(String[] line: currentMap){
			for(int col = 0; col < line.length; col++){				
				int x = initX + col * 35 - col * 10 + row * 8; //35ÎªÍ¼Æ¬¿í
				int y = initY + row * 25 - row * 11 - col * 5; //25ÎªÍ¼Æ¬¸ß
				if(line[col].equals("1") || line[col].equals("S")){
					c.drawBitmap(brick, x, y, paint);
				}else if(line[col].equals("2")){
					c.drawBitmap(this.brick_switch_round, x, y, paint);
				}else if(line[col].equals("3")){
					c.drawBitmap(this.brick_switch_x, x, y, paint);
				}else if(line[col].equals("4")){
					c.drawBitmap(this.brick_orange, x, y, paint);
				}else if(line[col].equals("5")){
					c.drawBitmap(this.brick_bridge, x, y, paint);
				}else if(line[col].equals("E")){
					this.endPointRow = row;
					this.endPonitCol = col;
				}
			}

			row++;
		}		
	}

	
	public void myDraw(Canvas c, int bitmapId){
		this.paint.setAntiAlias(true);
		c.drawColor(Color.WHITE);
		
		this.drawMap(c);

		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), bitmapId);
		this.gameActivity.box.setCurrentBox(bitmap);
		this.gameActivity.box.drawMySelf(c, paint);	

		
	}
	
	public void tempDraw(Canvas c, int boxState){
		int[] tempRows = this.gameActivity.box.getRows();
		int[] tempCols = this.gameActivity.box.getCols();
		int tempX;
		int tempY;
		int tempX1;
		int tempY1;
		if(boxState == Constants.BOX_STATE_VERTICAL){
			tempX = initX + tempCols[0] * 35 - tempCols[0] * 10 + tempRows[0] * 8;
			tempY = initY + tempRows[0] * 25 - tempRows[0] * 11 - tempCols[0] * 5;
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.brick_temp);
			c.drawBitmap(bitmap, tempX, tempY, paint);
		}else if(boxState == Constants.BOX_STATE_HORIZONTAL_X){
			tempX = initX + tempCols[0] * 35 - tempCols[0] * 10 + tempRows[0] * 8;
			tempY = initY + tempRows[0] * 25 - tempRows[0] * 11 - tempCols[0] * 5;
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.brick_temp);
			c.drawBitmap(bitmap, tempX, tempY, paint);
			tempX1 = initX + tempCols[1] * 35 - tempCols[1] * 10 + tempRows[1] * 8;
			tempY1 = initY + tempRows[1] * 25 - tempRows[1] * 11 - tempCols[1] * 5;
			Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.brick_temp);
			c.drawBitmap(bitmap1, tempX1, tempY1, paint);
		}else{
			tempX = initX + tempCols[0] * 35 - tempCols[0] * 10 + tempRows[0] * 8;
			tempY = initY + tempRows[0] * 25 - tempRows[0] * 11 - tempCols[0] * 5;
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.brick_temp);
			c.drawBitmap(bitmap, tempX, tempY, paint);
			tempX1 = initX + tempCols[1] * 35 - tempCols[1] * 10 + tempRows[1] * 8;
			tempY1 = initY + tempRows[1] * 25 - tempRows[1] * 11 - tempCols[1] * 5;
			Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.brick_temp);
			c.drawBitmap(bitmap1, tempX1, tempY1, paint);			
		}
	}	

	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		System.out.println("surfaceChanged");
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		System.out.println("surfaceCreated");
		this.gameViewDrawThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		System.out.println("surfaceDestroyed");
		boolean reTry = true;
		this.gameViewDrawThread.setFlag(false);
		while(reTry){
			try {
				this.gameViewDrawThread.join();
				reTry = false;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
