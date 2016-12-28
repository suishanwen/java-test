import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {

        List<Integer> list1 = Lists.newArrayList();
        List<Integer> list2 = Lists.newArrayList();
        list1.add(1);
        list1.add(2);
        list2.add(2);
        list2.add(3);
        List<Integer> newList = list2.stream().filter((item) -> {
            return !list1.contains(item);
        }).collect(Collectors.toList());
        newList.forEach((item) -> {
//			System.out.println(item);
        });

//		ftp://{username}:{password}@{url}:{port}
//		ftp://{username}:{password}@{url}:{port}/{dir}
        String config0 = "ftp://meddo22cV:meddoc@192.168.1.63:2121/dad2/fafa";
        Map<String,String> configMap=new HashMap<>();
        configMap.put("username","ftp://([a-zA-Z1-9]+):");
        configMap.put("password",":([a-zA-Z1-9]+)@");
        configMap.put("url","@([a-zA-Z1-9\\.]+)");
        configMap.put("port","@[a-zA-Z1-9\\.]+:([1-9]+)");
        configMap.put("dir","@[a-zA-Z1-9\\.]+:[1-9]+/([a-zA-Z1-9/]+)");
        for(String key:configMap.keySet()){
            Matcher matcher=Pattern.compile(configMap.get(key)).matcher(config0);
            if(matcher.find()){
                configMap.put(key,matcher.group(1));
            }else if(key.equals("dir")){
                configMap.put(key,"");
            }else{
//               throw new Exception("exception");
            }
        }
        String config = "ftp://" + configMap.get("username") + ":" + configMap.get("password") + "@" + configMap.get("url") + ":" + configMap.get("port") + "/" + configMap.get("dir");
        System.out.println(config);
    }
}
















