package html2pdf;

/**
 * Created by sw on 2016/9/13.
 */
public class Html2PdfTest {
    public static void main(String [] args){
        try {
            Html2Pdf.convertHtmlToPdf("http://42.96.207.122/my-software/test/test04.html");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
