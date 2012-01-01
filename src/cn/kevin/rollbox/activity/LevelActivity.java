package cn.kevin.rollbox.activity;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;
import cn.kevin.rollbox.R;
import cn.kevin.rollbox.utils.Constants;

public class LevelActivity extends Activity implements OnGestureListener, OnClickListener{
		
	public static String KEY_MAP_INDEX = "map_index";
	
	private static int BUTTON_COUNT_PER_PAGE = 10;	
	private ViewFlipper flipper;
	private GestureDetector detector;
	private Button backBtn;
	private ArrayList<Button> buttonList;
	private int indexCount = 0;
	private int currentPage = 1;
	private int pageCount;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);        
        
        this.setContentView(R.layout.level_view);
        this.buttonList = new ArrayList<Button>();
        initLayout();
	}
	
	private void initLayout(){	
        int temp = Constants.TOTAL_LEVEL_COUNT / BUTTON_COUNT_PER_PAGE;
        int remain = Constants.TOTAL_LEVEL_COUNT % BUTTON_COUNT_PER_PAGE;
        if(remain != 0){
        	temp++;
        }
        pageCount = temp;
		
		this.detector = new GestureDetector(this);
		this.flipper = (ViewFlipper)findViewById(R.id.viewFlipper);
		for(int i = 0; i < pageCount; i++){
			if(i == pageCount - 1){
				if(remain != 0){
					this.flipper.addView(this.createPageView(remain));
				}else{
					this.flipper.addView(createPageView(BUTTON_COUNT_PER_PAGE));
				}
			}else{
				this.flipper.addView(createPageView(BUTTON_COUNT_PER_PAGE));
			}
		}
		
		this.backBtn = (Button)findViewById(R.id.back_button);
		this.backBtn.setOnClickListener(this);
	}
	
	//num为一页要添加按钮的数量
	private View createPageView(int num){
		LinearLayout mainLayout = new LinearLayout(this);
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams mainpPrams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
				);
		mainLayout.setGravity(Gravity.CENTER);
		
		int row = num / 5;
		if(num % 5 != 0){
			row++;
		}
		for(int i = 0; i < row; i++){
			LinearLayout layout = new LinearLayout(this);
			layout.setGravity(Gravity.CENTER_HORIZONTAL);
			for(int j = 0; j < 5; j++){
				indexCount++;
				if(indexCount > Constants.TOTAL_LEVEL_COUNT){
					break;
				}
				Button btn = new Button(this);
				btn.setOnClickListener(this);
				this.buttonList.add(btn);
				btn.setText("Level " + indexCount);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.WRAP_CONTENT,
							LinearLayout.LayoutParams.WRAP_CONTENT
							);				
				params.setMargins(10, 10, 10, 10);
				layout.addView(btn, params);				
			}			
			mainLayout.addView(layout, mainpPrams);			
		}
		return mainLayout;
	}
	
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return this.detector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("onDown");
		return true;
	}

	
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		if(e1.getX() - e2.getX() > 90){
			System.out.println("left");
			if(this.currentPage == pageCount){
				return false;
			}else{
				this.currentPage++;
				this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.left_in));
				this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.left_out));
				this.flipper.showNext();
				System.out.println("currentPage: " + currentPage);
				return true;
			}
		}else if(e1.getX() - e2.getX() < -90){
			System.out.println("right");
			if(this.currentPage == 1){
				return false;
			}else{
				this.currentPage--;
				this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.right_in));
				this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.right_out));
				this.flipper.showPrevious();
				System.out.println("currentPage: " + currentPage);
				return true;
			}
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("onLongPress");

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		System.out.println("onScroll");
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("onShowPress");

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("onSingleTapUp");
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Button clicked  = (Button)v;
		
		if(clicked == this.backBtn){
			finish();
		}else{
			int found = -1;
			int startIndex = (this.currentPage - 1) * BUTTON_COUNT_PER_PAGE;
			for(int i = startIndex; i < this.indexCount; i++){
				Button btn = this.buttonList.get(i);
				if(btn == clicked){
					found = i;
					break;
				}
			}
			
			Intent intent = new Intent(this, GameActivity.class);
			intent.putExtra(KEY_MAP_INDEX, found);
			this.startActivity(intent);
		}

	}
	
	
	
}
