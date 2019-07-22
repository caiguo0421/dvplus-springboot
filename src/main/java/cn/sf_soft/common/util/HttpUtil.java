package cn.sf_soft.common.util;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

public class HttpUtil {
    static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(HttpUtil.class);
    private static final String CHAR_SET = "UTF-8";

    private static final Integer CONNECT_TIMEOUT = 60 * 1000;


    /**
     * Http的Get方法
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static InputStream doGet(String url) throws Exception {
        URL localURL = new URL(url);

        URLConnection connection = openConnection(localURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

        httpURLConnection.setRequestProperty("Accept-Charset", CHAR_SET);
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT);

        InputStream in = null;

        if (httpURLConnection.getResponseCode() >= 300) {
            throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
        }

        in = httpURLConnection.getInputStream();


        return in;
    }


    /**
     * Http的post方法
     *
     * @param url
     * @param parameterMap 参数map
     * @return 返回的流
     * @throws Exception
     */
    public static InputStream doPost(String url, Map parameterMap) throws Exception {
        StringBuffer parameterBuffer = new StringBuffer();
        if (parameterMap != null) {
            Iterator iterator = parameterMap.keySet().iterator();
            String key = null;
            String value = null;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                if (parameterMap.get(key) != null) {
                    value = (String) parameterMap.get(key);
                } else {
                    value = "";
                }

                parameterBuffer.append(key).append("=").append(value);
                if (iterator.hasNext()) {
                    parameterBuffer.append("&");
                }
            }
        }
        return doPostForString(url,parameterBuffer.toString());
    }


    public static InputStream doPostForString(String url,String parameterStr)throws Exception{

        byte[] bodyStr = parameterStr.getBytes(Charset.forName(CHAR_SET));

        URL localURL = new URL(url);
        URLConnection connection = openConnection(localURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept-Charset", CHAR_SET);
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(bodyStr.length));
        httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT);
        logger.debug("doPostForString参数： url = "+url+"\r\n parameterStr = "+parameterStr);

        OutputStream out = null;
        InputStream in = null;
        try {
            out = httpURLConnection.getOutputStream();

            out.write(bodyStr);
            out.flush();
            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }

            in = httpURLConnection.getInputStream();
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return in;
    }


    /**
     * 打开链接
     *
     * @param localURL
     * @return
     * @throws IOException
     */
    private static URLConnection openConnection(URL localURL) throws IOException {
        return openConnection(localURL, null, null);
    }


    private static URLConnection openConnection(URL localURL, String proxyHost, Integer proxyPort) throws IOException {
        URLConnection connection;
        if (proxyHost != null && proxyPort != null) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
            connection = localURL.openConnection(proxy);
        } else {
            connection = localURL.openConnection();
        }
        return connection;
    }
}
