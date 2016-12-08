package cache;

import java.util.HashMap;

/**
 * 缓存管理类
 * 提供各种接口管理内存中的缓存 
 * @author Hades
 * 
 */
public class CacheManager {
	/**
	 * 单实例构造方法
	 */
	private CacheManager() {
	}
	
	//存放缓存类的map
	private static HashMap<String, Object> managerMap = new HashMap<String, Object>();
	
	public HashMap<String,Object> getManagerMap(){
		return managerMap;
	}
	
	/**
	 * 获取指定的缓存
	 * @param key
	 * @return
	 */
	public static synchronized Object getCache(String key){
		return managerMap.get(key);
	}
	
	/**
	 * 判断是否包含一个缓存
	 * @param key
	 * @return
	 */
	public static synchronized boolean hasCache(String key){
		return managerMap.containsKey(key);
	}
	
	/**
	 * 清楚所有缓存
	 */
	public static synchronized void clearAll(){
		managerMap.clear();
	}
	
	/**
	 * 清楚指定缓存
	 * @param key
	 */
	public static synchronized void clearSpecify(String key){
		managerMap.remove(key);
	}
	
	/**
	 * 载入缓存
	 * @param key
	 * @param value
	 */
	public static synchronized void putCache(String key,Object value){
		managerMap.put(key, value);
	}
	//TODO 添加其他针对内存中缓存的操作
}
