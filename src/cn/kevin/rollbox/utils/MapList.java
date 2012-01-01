package cn.kevin.rollbox.utils;

import cn.kevin.rollbox.R;

/*地图数字标记说明：
 * 0——不可碰区域
 * 1——普通地板
 * 2——圆形开关
 * 3——叉形开关
 * 4——橙色地板
 * 5——开启的桥梁（初始状态）
 * 6——未开启的桥梁（初始状态）
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
}
