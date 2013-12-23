package com.lifedjtu.jw.util.extractor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.lifedjtu.jw.pojos.EntityObject;

public class Filter extends EntityObject {
	// 列出支持的filter
	// eq(index) 只返回下标为....index的
	// lt(index) 只返回下标小于...index的
	// gt(index) 只返回下标大于...index的
	// contains(text) 只返回text包含...text的
	// empty 只返回html为空的
	// even 只返回下标为偶数的,0,2,4,6,8
	// first 只返回第一个
	// last 只返回第二个

	private static final String EQ = "eq_Integer";
	private static final String LT = "lt_Integer";
	private static final String GT = "gt_Integer";
	private static final String CONTAINS = "contains_String";
	private static final String EMPTY = "empty";
	private static final String EVEN = "event";
	private static final String FIRST = "first";
	private static final String LAST = "last";

	
	private String filterName;
	private String param;

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public Filter() {
	}

	public Filter(String filterName, String param) {
		super();
		this.filterName = filterName;
		this.param = param;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<DomElement> filterList(List<DomElement> domElements) {
		String filterType = ensureFilterType();
		if(filterType==null){
			return domElements;
		}
		
		int bottomLineIndex = filterType.indexOf("_");
		if(bottomLineIndex==-1){
			domElements = handleFilter(domElements, null);
		}else{
			try {
				String typeAffix = filterType.substring(bottomLineIndex+1);
				Object castedObj;
				if(typeAffix.equals("String")){
					castedObj = param;
				}else{
					Class clazz = Class.forName("java.lang."+typeAffix);
					Method method = clazz.getDeclaredMethod("valueOf", Class.forName("java.lang.String"));
					castedObj = method.invoke(null, param);
				}
				
				//System.out.println((castedObj).getClass().getName());
				domElements = handleFilter(domElements, castedObj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return domElements;
	}
	
	private String ensureFilterType(){
		String filterType = null;
		if(EQ.startsWith(filterName)){
			filterType = EQ;
		}else if(LT.startsWith(filterName)){
			filterType = LT;
		}else if(GT.startsWith(filterName)){
			filterType = GT;
		}else if(CONTAINS.startsWith(filterName)){
			filterType = CONTAINS;
		}else if(EMPTY.startsWith(filterName)){
			filterType = EMPTY;	
		}else if(EVEN.startsWith(filterName)){
			filterType = EVEN;			
		}else if(FIRST.startsWith(filterName)){
			filterType = FIRST;			
		}else if(LAST.startsWith(filterName)){
			filterType = LAST;			
		}
		
		return filterType;
	}
	
	private List<DomElement> handleFilter(List<DomElement> domElements, Object castedObj){
		if(EQ.startsWith(filterName)){
			Integer index = (Integer)castedObj;
			return domElements.subList(index, index+1);
		}else if(LT.startsWith(filterName)){
			Integer index = (Integer)castedObj;
			return domElements.subList(0, index);
		}else if(GT.startsWith(filterName)){
			Integer index = (Integer)castedObj;
			return domElements.subList(index, domElements.size());
		}else if(CONTAINS.startsWith(filterName)){
			String str = (String)castedObj;
			List<DomElement> subList = new ArrayList<DomElement>();
			for(DomElement domElement:domElements){
				if(domElement.getText().contains(str)){
					subList.add(domElement);
				}
			}
			return subList;
		}else if(EMPTY.startsWith(filterName)){
			List<DomElement> subList = new ArrayList<DomElement>();
			for(DomElement domElement:domElements){
				if(domElement.getHtml()==null||domElement.getHtml().equals("")){
					subList.add(domElement);
				}
			}
			return subList;
		}else if(EVEN.startsWith(filterName)){
			List<DomElement> subList = new ArrayList<DomElement>();
			int i = 0;
			for(DomElement domElement : domElements){
				if(i%2==0){
					subList.add(domElement);
				}
				++i;
			}
			return subList;
		}else if(FIRST.startsWith(filterName)){
			return domElements.subList(0, 1);	
		}else if(LAST.startsWith(filterName)){
			return domElements.subList(domElements.size()-1, domElements.size());	
		}else{
			return domElements;
		}
	}

}
