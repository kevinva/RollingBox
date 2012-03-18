package cn.kevin.rollbox.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import cn.kevin.rollbox.R;

public class StartActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	
	private Button startBtn;
	private Button exitBtn;
	private Button aboutBtn;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);        
        setContentView(R.layout.main);
        
        this.initLayout();
    }
    
    public void onStart(){
    	super.onStart();

    }
    
    public void onResume(){
    	super.onResume();

    }

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.start_button:
			Intent intent = new Intent(this, LevelActivity.class);
			this.startActivity(intent);			
			break;
		case R.id.about_button:
		case R.id.exit_button:
			new AlertDialog.Builder(this)
			.setTitle(R.string.quit_dialog_title)
			.setMessage(R.string.quit_dialog_message)
			.setPositiveButton(R.string.quit_dailog_confirm_yes, new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			})
			.setNegativeButton(R.string.quit_dialog_confirm_no, new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			})
			.show();
			break;
		}
	}
	
	private void initLayout() {
		// TODO Auto-generated method stub
		startBtn = (Button)findViewById(R.id.start_button);
		aboutBtn = (Button)findViewById(R.id.about_button);
		exitBtn = (Button)findViewById(R.id.exit_button);
		startBtn.setOnClickListener(this);
		aboutBtn.setOnClickListener(this);
		exitBtn.setOnClickListener(this);
	}
	

	

	
}