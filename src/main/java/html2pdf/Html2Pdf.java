package html2pdf;


import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by sw on 2016/9/13.
 */
public class Html2Pdf {

    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }

        bos.close();
        return bos.toByteArray();
    }

    public static InputStream html2Pdf(String url0){
        InputStream inputStream=null;
        try {
            URL url = new URL(url0);
            URLConnection conn = url.openConnection();//获得UrlConnection 连接对象
            inputStream = conn.getInputStream();
            byte[] getData = readInputStream(inputStream);     //获得网站的二进制数据
            String data = new String(getData, "gb2312");
            inputStream=new ByteArrayInputStream(data.getBytes("UTF-8"));
            System.out.println(data);
        }catch (IOException e){
            e.printStackTrace();
        }
        return inputStream;
    }

    public static boolean convertHtmlToPdf(String url)
            throws Exception {
        //   step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("E:/pdfTest.pdf"));
        // step 3
        document.open();
        // step 4
        BaseFont baseFontChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font font = new Font(baseFontChinese);
        document.add(new Paragraph("解决中文问题了！",font));

        XMLWorkerHelper.getInstance().parseXHtml(writer, document,html2Pdf(url));
        //step 5
        document.close();

        System.out.println( "PDF Created!" );
        return true;
    }
}
