package cn.kevin.rollbox.utils;

import cn.kevin.rollbox.R;

public class Constants {
	
	public static final int TOTAL_LEVEL_COUNT = 37;
	public static int LEVEL_COUNT_PER_PAGE = 10;	
	
	public static final int BOX_STATE_VERTICAL = 0;   //方块竖立
	public static final int BOX_STATE_HORIZONTAL_X = 1; //方块沿x轴横卧
	public static final int BOX_STATE_HORIZONTAL_Z = 2; //方块沿z轴横卧
	
	public static final int DIRECTION_UP = 0;
	public static final int DIRECTION_LEFT = 1;
	public static final int DIRECTION_DOWN = 2;
	public static final int DIRECTION_RIGHT = 3;
	
	public static final int MESSAGE_LEVEL_CLEAR = 1;
	
	public static final int BRICK_SWITCH_ROUND = 0;
	public static final int BRICK_SWITCH_X = 1;
	
	public static final int SWITCH_CLOSE = 0;  //开关只能关闭桥
	public static final int SWITCH_OPEN = 1;	//开关只能开启桥
	public static final int SWITCH_CLOSE_OR_OPEN = 2;  //开关可开启或关闭桥
	
	public static final int[] box_y_z_up = new int[]{
		R.drawable.box_y,
		R.drawable.box_y_z_up1,
		R.drawable.box_y_z_up2,
		R.drawable.box_y_z_up3,
		R.drawable.box_y_z_up4,
		R.drawable.box_y_z_up5,
		R.drawable.box_y_z_up6,
		R.drawable.box_y_z_up7,
		R.drawable.box_y_z_up8,
		R.drawable.box_z
	};
	
	public static final int[] box_y_z_down = new int[]{
		R.drawable.box_y,
		R.drawable.box_y_z_down1,
		R.drawable.box_y_z_down2,
		R.drawable.box_y_z_down3,
		R.drawable.box_y_z_down4,
		R.drawable.box_y_z_down5,
		R.drawable.box_y_z_down6,
		R.drawable.box_y_z_down7,
		R.drawable.box_y_z_down8,
		R.drawable.box_z
	};
	
	
	public static final int[] box_y_x_left = new int[]{
		R.drawable.box_y,
		R.drawable.box_y_x_left1,
		R.drawable.box_y_x_left2,
		R.drawable.box_y_x_left3,
		R.drawable.box_y_x_left4,
		R.drawable.box_y_x_left5,
		R.drawable.box_y_x_left6,
		R.drawable.box_x
	};
	
	public static final int[] box_y_x_right = new int[]{
		R.drawable.box_y,
		R.drawable.box_y_x_right1,
		R.drawable.box_y_x_right2,
		R.drawable.box_y_x_right3,
		R.drawable.box_y_x_right4,
		R.drawable.box_y_x_right5,
		R.drawable.box_y_x_right6,
		R.drawable.box_x
	};
	
	public static final int[] box_x_up = new int[]{
		R.drawable.box_x,
		R.drawable.box_x_1,
		R.drawable.box_x_2,
		R.drawable.box_x_3,
		R.drawable.box_x_4,
		R.drawable.box_x_5,
		R.drawable.box_x_6,
		R.drawable.box_x
	};
	
	public static final int[] box_z_left = new int[]{
		R.drawable.box_z,
		R.drawable.box_z_1,
		R.drawable.box_z_2,
		R.drawable.box_z_3,
		R.drawable.box_z_4,
		R.drawable.box_z_5,
		R.drawable.box_z_6,
		R.drawable.box_z
	};
	
	public static String KEY_MAP_INDEX = "map_index";	
	

}
