package executor;

import util.IoUtil;
import util.PDFUtil;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by sw on 2016/9/13.
 */
public class MultiThreadTest {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        Map<String, Future<InputStream>> tempMap = new LinkedHashMap<>();
        Callable<InputStream> callable1 = new MultiThreadOperation("/Inp/DOCTOR_MR_EXTERNAL_DATA/20151200002", "20160729000001");
        tempMap.put("file1",pool.submit(callable1));
        Callable<InputStream> callable2 = new MultiThreadOperation("/Inp/DOCTOR_MR_EXTERNAL_DATA/20151200002", "20160729000001");
        tempMap.put("file2",pool.submit(callable2));
        Callable<InputStream> callable3 = new MultiThreadOperation("/Inp/DOCTOR_MR_EXTERNAL_DATA/20151200002", "20160729000001");
        tempMap.put("file3",pool.submit(callable3));
        List<InputStream> inputStreamList = new ArrayList<InputStream>();
        for (String key : tempMap.keySet()) {
            Future<InputStream> future = tempMap.get(key);
            InputStream inputStream = null;
            try {
                inputStream = future.get();
            } catch (NullPointerException e) {
                System.out.println("文件流不存在");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    inputStreamList.add(inputStream);
                }
            }
        }
        pool.shutdown();
        ByteArrayOutputStream byteArrayOutputStream=PDFUtil.mergePDF(inputStreamList);
        IoUtil.writeFile(byteArrayOutputStream,"E:/pdfMergeTestMultiThread");
    }
}
