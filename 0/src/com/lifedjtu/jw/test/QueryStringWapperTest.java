package com.lifedjtu.jw.test;



import org.junit.Test;

import com.lifedjtu.jw.dao.QueryStringWapper;


public class QueryStringWapperTest {
	@Test
	public void testWapper(){
		System.out.println(QueryStringWapper.instance().select("*").equal("id", 1).build().getQueryString());
	}
}
