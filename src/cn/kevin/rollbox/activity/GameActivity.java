package cn.kevin.rollbox.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import cn.kevin.rollbox.R;
import cn.kevin.rollbox.data_model.Box;
import cn.kevin.rollbox.data_model.Switch;
import cn.kevin.rollbox.utils.Constants;
import cn.kevin.rollbox.utils.MapList;
import cn.kevin.rollbox.utils.MovementChecker;
import cn.kevin.rollbox.view.GameView;

public class GameActivity extends Activity implements OnGestureListener{	

	private GameView gameView;
	private GestureDetector detector;
	private MovementChecker movementChecker;
	private int currentMapIndex;
	
	public Box box;
	public ArrayList<String[]> currentMap;
	public ArrayList<Switch> switchList;
	public Handler mHandler = new Handler(){
		
		public void handleMessage(Message msg){
			if(msg.what == 1){
				Toast.makeText(GameActivity.this, R.string.dialog_level_clear_message, 800).show();
				GameActivity.this.initGameView(++currentMapIndex);
			}
		}
		
	};
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		Bundle bundle = this.getIntent().getExtras();
		if(bundle != null){
			this.detector = new GestureDetector(this);
			this.currentMapIndex = bundle.getInt(Constants.KEY_MAP_INDEX);
			
			System.out.println("current map index: " + currentMapIndex);
			
			initGameView(currentMapIndex);
		}		

	}
	
	private void initGameView(int index){
		this.movementChecker = MovementChecker.getInstance(this);
		this.currentMap = this.loadMaps(index);
		this.box = new Box();
		this.initBox();
		gameView = new GameView(this);
		this.setContentView(gameView);		
	}
	
	private ArrayList<String[]> loadMaps(int index){
		ArrayList<String[]> list = new ArrayList<String[]>();
		if(index < MapList.maps.length){
			InputStream is = this.getResources().openRawResource(MapList.maps[index]);
			InputStreamReader reader = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(reader);
			
			String line = null;
			try {
				while((line = br.readLine()) != null){
					String[] row = line.split(" ");
					list.add(row);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			for(String[] row: list){
				for(int j = 0; j < row.length; j++){
					System.out.print(row[j] + " ");
				}
				System.out.println();
			}		

			return list;
		}
		return null;
	
	}
	
	/*
	private ArrayList<Switch> getSwitch(){
		ArrayList<Switch> list = new ArrayList<Switch>();		
		int rows = currentMap.size();
		int cols = currentMap.get(0).length;
		for(int i = 0; i < rows; i++){
			String[] line = this.currentMap.get(i);
			for(int j = 0; j < cols; j++){
				if(line[j].equals("2")){
					Switch s = new Switch(i, j, Constants.BRICK_SWITCH_ROUND, false);
					list.add(s);
				}else if(line[j].equals("3")){
					Switch s2 = new Switch(i, j, Constants.BRICK_SWITCH_X, false);
					list.add(s2);
				}
			}
		}
		return list;
	}
	*/
	
	private void initBox(){
		int rows = currentMap.size();
		int cols = currentMap.get(0).length;
		boolean flag = false;
		int startRow = -1;
		int startCol = -1;
		for(int i = 0; i < rows; i++){
			String[] line = currentMap.get(i);
			for(int j = 0; j < cols; j++){
				if(line[j].equals("S")){
					startRow = i;
					startCol = j;
					flag = true;
				}
			}
			if(flag){
				break;
			}
		}
		
		if(startRow != -1 && startCol != -1){
			this.box.setRows(startRow, startRow);
			this.box.setCols(startCol, startCol);
		}
	}
	
	public boolean onTouchEvent(MotionEvent ev){
		return this.detector.onTouchEvent(ev);
	}

	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub

		if(e1.getX() - e2.getX() < -60){
			if(this.movementChecker.canMove_v2(this.box.getState(), Constants.DIRECTION_RIGHT)){
				System.out.println("right");
				this.gameView.gameViewDrawThread.setDirection(Constants.DIRECTION_RIGHT);
			}else{
				Toast.makeText(this, R.string.not_allow_move_text, 800).show();
			}
		}else if(e1.getX() - e2.getX() > 60){
			if(this.movementChecker.canMove_v2(this.box.getState(), Constants.DIRECTION_LEFT)){
				System.out.println("left");
				this.gameView.gameViewDrawThread.setDirection(Constants.DIRECTION_LEFT);
			}else{
				Toast.makeText(this, R.string.not_allow_move_text, 800).show();
			}
		}else if(e1.getY() - e2.getY() < -60){
			if(this.movementChecker.canMove_v2(this.box.getState(), Constants.DIRECTION_DOWN)){
				System.out.println("down");
				this.gameView.gameViewDrawThread.setDirection(Constants.DIRECTION_DOWN);
			}else{
				Toast.makeText(this, R.string.not_allow_move_text, 800).show();
			}
		}else if(e1.getY() - e2.getY() > 60){
			if(this.movementChecker.canMove_v2(this.box.getState(), Constants.DIRECTION_UP)){
				System.out.println("up");
				this.gameView.gameViewDrawThread.setDirection(Constants.DIRECTION_UP);
			}else{
				Toast.makeText(this, R.string.not_allow_move_text, 800).show();
			}
		}		
		return true;
	}

	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

}
