package cn.kevin.rollbox.data_model;

import java.util.ArrayList;

public class Bridge {
	
	private ArrayList<Tuple> list;
	
	public Bridge(ArrayList<Tuple> arr){
		this.list = arr;
	}

	public ArrayList<Tuple> getList() {
		return list;
	}

	public void setList(ArrayList<Tuple> list) {
		this.list = list;
	}
	
	
	
}
