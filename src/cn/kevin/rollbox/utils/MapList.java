package cn.kevin.rollbox.utils;

import cn.kevin.rollbox.R;

/*��ͼ���ֱ��˵����
 * 0��������������
 * 1������ͨ�ذ�
 * 2����Բ�ο��� (ľ��ֻҪ���ִ������ɴ�)
 * 3�������ο��أ�ľ��Ҫ�����������ϲſɴ򿪣�
 * 4������ɫ�ذ壨ľ���������ϻ����ѣ�
 * 5��������
 * E����Ŀ�ĵ�
 * S������ʼλ��
 * 
 * 
 */

/*
 * ��ͼ����˵����
 * 1�������ɼ���ש��Χ����Ҫ����һ��0��������������
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
