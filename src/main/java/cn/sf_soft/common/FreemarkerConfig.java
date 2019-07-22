package cn.sf_soft.common;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerConfig {
    private static Configuration config = null;

    private static final String DEFAULT_ENCODING="UTF-8";

    private static final String TEMPLATE_PATH="D:\\template";

    static {
        config = new Configuration();
        config.setClassForTemplateLoading(FreemarkerConfig.class, "/template");
//        try {
//            config.setDirectoryForTemplateLoading(new File(TEMPLATE_PATH));
//        } catch (IOException e) {
//            throw new ServiceException("获取Freemarker模板目录："+TEMPLATE_PATH+"出错",e);
//        }
        config.setDefaultEncoding(DEFAULT_ENCODING);
    }

    public static Configuration getConfiguation() {
        return config;
    }



    public static void main(String[] args) throws Exception {
        Configuration cfg = getConfiguation();

        Map root = new HashMap();
        root.put("user", "Big Joe");
        Map latest = new HashMap();
        root.put("latestProduct", latest);
        latest.put("url", "products/greenmouse.html");
        latest.put("name", "green mouse");

        Template temp = cfg.getTemplate("1.htm");

        Writer out = new OutputStreamWriter(System.out);
        temp.process(root, out);

    }
}
