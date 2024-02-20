package kr.co.hs.common.util;


import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

public class BeanUtils {

	private BeanUtils() {}

	public static <T> T beansCopy(Object source, Class<T> clz) {
		if(source == null){
			return null;
		}
		try {
			Class<? extends Object> c = Class.forName(clz.getName()).asSubclass(clz);
			Object obj = c.newInstance();
			beanCopy( obj, source);
			return clz.cast(obj);			
		}catch(Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}
	
	public static <T> T beansCopy(HashMap<String, Object> source, Class<T> clz) {
		if(source == null){
			return null;
		}
		try {
			Class<? extends Object> c = Class.forName(clz.getName()).asSubclass(clz);
			Object obj = c.newInstance();
			checkBeanNull(obj, source);
			
			for(String key : source.keySet() ) {
				if( isWriteable( StringUtil.getCamelize(key), obj) ) {
					PropertyUtils.setProperty(obj, StringUtil.getCamelize(key), source.get(key) );
				}else if( isWriteable(key, obj) ) {
					PropertyUtils.setProperty(obj, key, source.get(key) );
				}
			}
			return clz.cast(obj);			
		}catch(Exception ex) {
			throw new IllegalArgumentException(ex);
		}		
	}
	
	public static <T> List<T> beansListCopy(List<HashMap<String, Object>> list, Class<T> clz) {
		List<T> returnList = new ArrayList<T>();
		
		try {
			Class<? extends Object> c = Class.forName(clz.getName()).asSubclass(clz);
			
			for(HashMap<String, Object> source : list){				
				Object obj = c.newInstance();
				for(String key : source.keySet() ) {
					if( isWriteable( StringUtil.getCamelize(key), obj) ) {
						PropertyUtils.setProperty(obj, StringUtil.getCamelize(key), source.get(key) );
					}else if( isWriteable(key, obj) ) {
						PropertyUtils.setProperty(obj, key, source.get(key) );
					}
				}
				returnList.add(clz.cast(obj));			
			}
			
			return returnList;
		}catch(Exception ex) {
			throw new IllegalArgumentException(ex);
		}		
	}
	
	public static <T> List<T> beansListCopy2(List<?> list, Class<T> clz) {
		List<T> returnList = new ArrayList<T>();
		
		try {
			Class<? extends Object> c = Class.forName(clz.getName()).asSubclass(clz);
			for(Object source : list){
				Object obj = c.newInstance();
				beanCopy( obj, source);
				returnList.add(clz.cast(obj));			
			}
			
			return returnList;
		}catch(Exception ex) {
			throw new IllegalArgumentException(ex);
		}		
	}

	public static void beanCopy(Object target, Object source ) {
		if(source == null){
			return;
		}
		checkBeanNull(target, source);
		writeBeanToBean(target, source);
	}
	
	public static void beanCopy(Object target, HashMap<String, Object> source) {
		if(source == null){
			return;
		}
		try {
			checkBeanNull(target, source);
			
			for(String key : source.keySet() ) {
				if( isWriteable( StringUtil.getCamelize(key), target) ) {
					PropertyUtils.setProperty(target, StringUtil.getCamelize(key), source.get(key) );
				}else if( isWriteable(key, target) ) {
					PropertyUtils.setProperty(target, key, source.get(key) );
				}
			}
		}catch(Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}
	
	private static void checkBeanNull(Object target, Object source) {
		if( ValidationUtils.isNull(target, source) )
			throw new IllegalArgumentException("sourceObject/targetObject is null!");
	}
	
	
	private static void writeBeanToBean(Object target, Object source) {
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(source);
		
		String propName = null;
		for( PropertyDescriptor pd : pds ) {
			propName = pd.getName();
			
			if (isWriteable(propName, target) && isReadable(propName, source) ) {
				try {
					PropertyUtils.setProperty(target, propName, PropertyUtils.getProperty(source, propName) );
				}catch(Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	private static boolean isReadable(String propName, Object obj) {
		return PropertyUtils.isReadable(obj, propName);
	}
	
	private static boolean isWriteable(String propName, Object obj) {
		return PropertyUtils.isWriteable(obj, propName);
	}
	
	public static void propertySetNull(Object target, String key) throws Exception{
		propertySetValue(target, key, null);
	}
	
	public static void propertySetValue(Object target, String key, Object value) throws Exception{
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(target);
		for(PropertyDescriptor pd : pds){
			if(isWriteable(pd.getName(), target)){
				childPropertySetValue(PropertyUtils.getProperty(target, pd.getName()), key, value);
			}
		}
	}
	
	public static void childPropertySetValue(Object target, String key, Object value) throws Exception{
		if(target  != null){
			PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(target);
			for(PropertyDescriptor pd : pds){
				if(isWriteable(pd.getName(), target)){
					if(PropertyUtils.getProperty(target, pd.getName()) instanceof Collection<?> || PropertyUtils.getProperty(target, pd.getName()) instanceof List<?> ){
						List<?> l = (List<?>)PropertyUtils.getProperty(target, pd.getName());
						Iterator<?> iter = l.iterator();
						while (iter.hasNext()) {
							Object o = iter.next();
							childPropertySetValue(o, key, value);
						}
					}else{
						if(key.equals(pd.getName())){
							PropertyUtils.setProperty(target, pd.getName(), value);
						}
					}
				}
			}
		}
	}
}
