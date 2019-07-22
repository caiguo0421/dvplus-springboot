package cn.sf_soft.common.util;

import cn.sf_soft.common.FreemarkerConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;

public class FileUtil {
    private static final String CONVERT_TO_PDF_URL = "http://html2pdf.demo.usingnet.com/";

    public static InputStream generatePdf(String template, Map<String, Object> variables)  throws Exception {
        return HttpUtil.doPostForString(CONVERT_TO_PDF_URL, generateHtml(template,variables));
    }


    public static String generateHtml(String template, Map<String, Object> variables) throws IOException, TemplateException {
        Configuration config = FreemarkerConfig.getConfiguation();
        Template tp = config.getTemplate(template);
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        tp.setEncoding("UTF-8");
        tp.process(variables, writer);
        String htmlStr = stringWriter.toString();
        writer.flush();
        writer.close();
        return htmlStr;
    }


    /**
     * HTML转换为pdf
     *
     * @param htmlText html文档
     * @return pdf的stream
     * @throws Exception
     */
    public static InputStream convertHtmlToPdf(String htmlText) throws Exception {
        return HttpUtil.doPostForString(CONVERT_TO_PDF_URL, htmlText);
    }



    public static void main(String[] args) throws Exception {

        String htmlText = generateHtml("1.htm",null);
        System.out.println(htmlText);

        /**
        String htmlPath = "D:\\1.htm";
        String pdfPath = "D:\\1.pdf";
        StringBuilder result = new StringBuilder();

        BufferedReader br = new BufferedReader(new FileReader(htmlPath));
        String s = null;
        while ((s = br.readLine()) != null) {
            result.append(System.lineSeparator() + s);
        }
        br.close();

        InputStream ins = convertHtmlToPdf(result.toString());
        OutputStream os = new FileOutputStream(pdfPath);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
         **/
    }
}
