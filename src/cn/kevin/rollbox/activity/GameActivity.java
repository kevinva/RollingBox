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
import cn.kevin.rollbox.data_model.Bridge;
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
			this.switchList = new ArrayList<Switch>();
			
			System.out.println("current map index: " + currentMapIndex);
			
			initGameView(currentMapIndex);
		}		

	}
	
	private void initGameView(int index){
		this.switchList.clear();
		this.movementChecker = MovementChecker.getInstance(this);
		this.currentMap = this.loadMaps(index);
		this.loadConfig(index);
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
			}finally{
				try {
					is.close();
					reader.close();
					br.close();
					is = null;
					reader = null;
					br = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
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
	
	private void loadConfig(int index){
		if(index < MapList.mapsConfig.length){
			if(this.currentMap != null){
				InputStream is = this.getResources().openRawResource(MapList.mapsConfig[index]);
				InputStreamReader reader = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(reader);
				String line = null;
				Switch s = null;
				try{
					while((line = br.readLine()) != null){						
						if(line.equals("[Switch")){
							s = new Switch();
						}else if(line.equals("]")){
							if(s != null){
								this.switchList.add(s);
							}
						}else{
							if(line.startsWith("type=")){
								if(s != null){
									String[] tmp1 = line.split("=");
									if(tmp1[1].equals("close;open") || tmp1[1].equals("open;close")){
										s.setType(Constants.SWITCH_CLOSE_OR_OPEN);
									}else if(tmp1[1].equals("close")){	//close可控制桥阻断
										s.setType(Constants.SWITCH_CLOSE);
									}else if(tmp1[1].equals("open")){ //open表示可控制桥连通
										s.setType(Constants.SWITCH_OPEN);
									}
								}
							}else if(line.startsWith("position=")){
								if(s != null){
									String[] tmp2 = line.split("=");
									String[] tmp3 = tmp2[1].split(",");
									s.setRow(Integer.parseInt(tmp3[0]));
									s.setCol(Integer.parseInt(tmp3[1]));
								}
							}else if(line.startsWith("bridge=")){
								if(s != null){
									String[] tmp4 = line.split("=");
									String[] tmp5 = tmp4[1].split("\\|");
									ArrayList<Bridge> bridges = new ArrayList<Bridge>();
									for(String str: tmp5){
										String[] tmp6 = str.split(",");
										Bridge b = new Bridge(Integer.parseInt(tmp6[0]), Integer.parseInt(tmp6[1]));
										bridges.add(b);
									}
									s.setBridges(bridges);
								}
							}
						}

					}					

					System.out.println("SwitchBridge size:" + this.switchList.size());
					
				}catch(IOException e){
					e.printStackTrace();
				}finally{
					try {
						is.close();
						reader.close();
						br.close();
						is = null;
						reader = null;
						br = null;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}	

	
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
			if(this.movementChecker.canMove_v2(Constants.DIRECTION_RIGHT)){
				System.out.println("right");
				this.gameView.gameViewDrawThread.setDirection(Constants.DIRECTION_RIGHT);
			}else{
				Toast.makeText(this, R.string.not_allow_move_text, 800).show();
			}
		}else if(e1.getX() - e2.getX() > 60){
			if(this.movementChecker.canMove_v2(Constants.DIRECTION_LEFT)){
				System.out.println("left");
				this.gameView.gameViewDrawThread.setDirection(Constants.DIRECTION_LEFT);
			}else{
				Toast.makeText(this, R.string.not_allow_move_text, 800).show();
			}
		}else if(e1.getY() - e2.getY() < -60){
			if(this.movementChecker.canMove_v2(Constants.DIRECTION_DOWN)){
				System.out.println("down");
				this.gameView.gameViewDrawThread.setDirection(Constants.DIRECTION_DOWN);
			}else{
				Toast.makeText(this, R.string.not_allow_move_text, 800).show();
			}
		}else if(e1.getY() - e2.getY() > 60){
			if(this.movementChecker.canMove_v2(Constants.DIRECTION_UP)){
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
