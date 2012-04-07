package cn.kevin.rollbox.utils;

import cn.kevin.rollbox.R;

/*地图数字标记说明：
 * 0——不可碰区域
 * 1——普通地板
 * 2——圆形开关 (木块只要部分触碰即可打开)
 * 3——叉形开关（木块要整个竖立其上才可打开）
 * 4——橙色地板（木块竖立其上会碎裂）
 * 5——桥梁
 * E——目的地
 * S——开始位置
 * 
 * 
 */

/*
 * 地图制作说明：
 * 1）整个可见地砖外围至少要包含一层0，即不可碰区域
 */

public class MapList {
	public static int[] maps = new int[]{
		R.raw.level_1,
		R.raw.level_2,
		R.raw.level_3,
		R.raw.level_4,
		R.raw.level_5
	};
	
	public static int[] mapsConfig = new int[]{
		R.raw.level_1_conf,
		R.raw.level_2_conf,
		R.raw.level_3_conf,
		R.raw.level_4_conf,
		R.raw.level_5_conf
	};
}
