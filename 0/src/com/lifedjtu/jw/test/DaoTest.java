package com.lifedjtu.jw.test;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.lifedjtu.jw.dao.ParamMapper;
import com.lifedjtu.jw.dao.ProjectionWrapper;
import com.lifedjtu.jw.dao.Tuple;
import com.lifedjtu.jw.dao.impl.BuildingDao;
import com.lifedjtu.jw.pojos.Area;

public class DaoTest {
	@Test
	public void testRemoteService(){
		char sep = File.separatorChar;
		ApplicationContext ctx=new FileSystemXmlApplicationContext("WebRoot"+sep+"WEB-INF"+sep+"applicationContext.xml");

//		CriteriaWrapper wrapper = CriteriaWrapper.instance(Restrictions.eq("id", "\"hello\""), Restrictions.between("bba", "1", "5")).or(Restrictions.isEmpty("jiji"),Restrictions.eq("lla", "hage"));
//		UpdateWrapper updateWrapper = UpdateWrapper.instance().inc("value", -1).set("good", "haha");
//		
//		System.out.println(updateWrapper.getUpdate(User.class, wrapper));
//		
		BuildingDao buildingDao = (BuildingDao)ctx.getBean("buildingDao");
//		User user = new User();
//		user.setId(UUID.randomUUID().toString());
//		user.setStudentId("1018110207");
//		user.setUsername("李辛洋");
//		user.setPassword("12345");
		long start = System.currentTimeMillis();
		//Building building = buildingDao.findOneByJoinedParams(MapMaker.instance("area", "haha").toMap(), CriteriaWrapper.instance().and(Restrictions.eq("buildingName", "老年之家"),Restrictions.eq("haha.id", "4fbe7848-5911-47c9-a7c5-a874f0c49754")));
		//Building building = buildingDao.findOneByParams(CriteriaWrapper.instance().and(Restrictions.eq("buildingName", "老年之家"),Restrictions.eq("area.id", "4fbe7848-5911-47c9-a7c5-a874f0c49754")));
		//Building building = buildingDao.findOneById("1bc89513-4c93-4d42-b5d4-9a199772884c");
		
		//此NamedQuery的名字来自于Area实体中的NamedQuery注释，请去Area类查看
		List<Area> object = buildingDao.findByNamedQuery("area.findOneById", ParamMapper.param("areaId", "4fbe7848-5911-47c9-a7c5-a874f0c49754"));
		
		long end = System.currentTimeMillis();
		System.err.println((end-start)/(double)1000+"s");
		for(Area area : object){
			System.out.println(area.getAreaName()+" "+area.getAreaRemoteId());
		}
		//System.out.println(object);
	}
}
