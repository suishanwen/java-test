package html2pdf;


import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by sw on 2016/9/13.
 */
public class Html2Pdf {

    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }

        bos.close();
        return bos.toByteArray();
    }

    public static InputStream html2Pdf(String url0) {
        InputStream inputStream = null;
        try {
            URL url = new URL(url0);
            URLConnection conn = url.openConnection();//获得UrlConnection 连接对象
            inputStream = conn.getInputStream();
            byte[] getData = readInputStream(inputStream);     //获得网站的二进制数据
            String data = new String(getData, "gb2312");
            inputStream = new ByteArrayInputStream(data.getBytes("UTF-8"));
            System.out.println(data);
        } catch (IOException e) {
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
//        document.add(new Paragraph("解决中文问题了！", font));
        Image image = Image.getInstance("E:/P101076");
        image.setAlignment(Image.ALIGN_CENTER);
        System.out.println(image.getScaledWidth());
        System.out.println(image.getScaledHeight());

        image.scaleAbsolute(505,775);//自定义大小

        document.add(image);
//        XMLWorkerHelper.getInstance().parseXHtml(writer, document, html2Pdf(url));
        //step 5
        document.close();

        System.out.println("PDF Created!");
        return true;
    }

    public static void create()
    {
        try {
            Document document =new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document,baos );
            document.open();

            document.add(new Paragraph("Hello World"));

            document.close();

            byte[] content = baos.toByteArray();

        } catch (Exception e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
