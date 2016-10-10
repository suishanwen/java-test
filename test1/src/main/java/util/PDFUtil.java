package util;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

public class PDFUtil {

    /**
     * PDF 合并同时生成页码
     * 返回输出流
     */
    public static ByteArrayOutputStream mergePDF(List<InputStream> inputStreamList) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //PDF合并
        PDFMergerUtility pdfMerge = new PDFMergerUtility();
        try {
            pdfMerge.addSources(inputStreamList);
            pdfMerge.setDestinationStream(out);
            pdfMerge.mergeDocuments();

            PDDocument doc = null;
            try {
                doc = PDDocument.load(new ByteArrayInputStream(out.toByteArray()));

                int pageCounts = doc.getDocumentCatalog().getPages().getCount();
                PDFont font = PDType1Font.TIMES_ROMAN;
                float fontSize = 12.0f;
                for (int i = 0; i < pageCounts; i++) {
                    PDPage page = doc.getPage(i);
                    page.setMediaBox(PDRectangle.A4);
                    PDPageContentStream footercontentStream = new PDPageContentStream(doc, page, true, true);
                    footercontentStream.beginText();
                    footercontentStream.setFont(font, fontSize);
                    footercontentStream.moveTextPositionByAmount((page.getMediaBox().getUpperRightX() / 2), (page.getMediaBox().getLowerLeftY()+10));
                    String pageContext = String.valueOf(i + 1) + "/" + pageCounts;
                    footercontentStream.drawString(pageContext);
                    footercontentStream.endText();
                    footercontentStream.close();
                }
                doc.save(out);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (doc != null) {
                    doc.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }


    /**
     * PDF 截取
     * */
    public static InputStream splitPDF(InputStream inputStream, Integer sStartPage, Integer sEndPage) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PDDocument sDocument = null;
        Splitter aSplitter = new Splitter();
        List<PDDocument> aParts = null;
        try{
            sDocument = PDDocument.load(inputStream);
            int aNumberOfPages = sDocument.getNumberOfPages();
            if(sStartPage > aNumberOfPages){
                return null;
            }
            if(sEndPage == null || sEndPage > aNumberOfPages){
                sEndPage = aNumberOfPages;
            }
            aSplitter.setStartPage(sStartPage);
            aSplitter.setEndPage(sEndPage);
            aSplitter.setSplitAtPage(sEndPage);
            aParts = aSplitter.split(sDocument);
            aParts.get(0).save(out);
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return IoUtil.ByteArray2InputStream(out.toByteArray());
    }

}
