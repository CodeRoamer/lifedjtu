package com.lifedjtu.jw.dao.impl;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.lifedjtu.jw.pojos.Teacher;


@Component
public class TeacherDaoImpl {
	private HibernateTemplate hibernateTemplate;



	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}


	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}



	@Test
	public void textSave(){
		Teacher t = new Teacher();
		t.setId(2);
		t.setName("123");
		
		ApplicationContext context=new FileSystemXmlApplicationContext("D:\\workspace\\lifedjtu\\0\\WebRoot\\WEB-INF\\applicationContext.xml");
		hibernateTemplate = (HibernateTemplate)context.getBean("hibernateTemplate");
		hibernateTemplate.save(t);
	}
}
