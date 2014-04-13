package com.lifedjtu.jw.test;

import com.lifedjtu.jw.pojos.Area;
import com.lifedjtu.jw.util.LifeDjtuUtil;

import java.util.ArrayList;
import java.util.List;

public class MainTest {
	public static void main(String[] args){

        List<Area> list = new ArrayList<>();
        Area area = new Area();
        area.setAreaName("lihe");
        list.add(area);

        List<Area> list2 = new ArrayList<>();
        list2.add(area);

        list.get(0).setAreaName("yangyang");
        System.out.println(list2.get(0).getAreaName());

		System.err.println(LifeDjtuUtil.getStartDateOfTerm(2014, 1));
	}
}
