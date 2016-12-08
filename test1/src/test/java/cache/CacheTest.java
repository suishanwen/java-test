package cache;



public class CacheTest {


	public static void main(String[] args){
		CacheManager.putCache("phone_cache_list", "123");
		System.out.printf(CacheManager.getCache("phone_cache_list").toString());
	}
}
















