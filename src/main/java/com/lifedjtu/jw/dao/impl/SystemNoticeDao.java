package com.lifedjtu.jw.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.lifedjtu.jw.pojos.SystemNotice;

@Repository("systemNoticeDao")
public class SystemNoticeDao extends LifeDjtuDaoImpl<SystemNotice>{
	@Override
	@Autowired
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
}
