import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by guo on 23/06/2017.
 */
public class PerformTest {
    protected static Server server;

    @BeforeClass
    public static void setup() {
        server = new Server();
        HttpConfiguration http_config = new HttpConfiguration();
        http_config.setSecureScheme("https");
        http_config.setSecurePort(4080);
        http_config.setOutputBufferSize(32768);

        ServerConnector http = new ServerConnector(server,
                new HttpConnectionFactory(http_config));
        http.setPort(8080);
        http.setIdleTimeout(30000);

        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath("./src/main/etc/keystore");
        sslContextFactory.setKeyStorePassword("19920903");
        sslContextFactory.setKeyManagerPassword("19920903");

        HttpConfiguration https_config = new HttpConfiguration(http_config);
        SecureRequestCustomizer src = new SecureRequestCustomizer();
        src.setStsMaxAge(2000);
        src.setStsIncludeSubDomains(true);
        https_config.addCustomizer(src);

        ServerConnector https = new ServerConnector(server,
                new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()),
                new HttpConnectionFactory(https_config));
        https.setPort(4080);
        https.setIdleTimeout(500000);

        // Set the connectors
        server.setConnectors(new Connector[] { http, https });

        // Set a handler
        WebAppContext context = new WebAppContext();
        context.setDescriptor("./src/main/webapp/WEB-INF/web.xml");
        context.setResourceBase("./src/main/webapp");
        context.setContextPath("/");

        server.setHandler(context);
    }

    public static String sendHttp(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


    public static HttpResponse sendHttps(String url,String charset){
        if(null == charset){
            charset = "utf-8";
        }
        HttpClient httpClient = null;
        HttpGet httpGet= null;
        String result = null;

        HttpResponse response = null;
        try {
            httpClient = new SSLClient();
            httpGet = new HttpGet(url);

            response = httpClient.execute(httpGet);
            /*
            if(response != null){
                StatusLine statusLine = response.getStatusLine();
                System.out.println(statusLine.getStatusCode());
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    //System.out.println(resEntity.getContentType().getName());
                    //application/json
                    System.out.println(resEntity.getContentType().getValue());
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
            */
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @Test
    public void testGetUsername() throws Exception {
        try {
            server.start();
            String url = "https://localhost:4080/hello?username=guo";
            HttpResponse response = sendHttps(url, null);

            StatusLine statusLine = response.getStatusLine();
            Assert.assertEquals(statusLine.getStatusCode(), 201);

            HttpEntity resEntity = response.getEntity();
            Assert.assertEquals(resEntity.getContentType().getValue(), "application/json");

            String result = EntityUtils.toString(resEntity, "utf-8");
            Assert.assertEquals(result, "{response:Hello World guo}");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }

    }

    @Test
    public void testNoUsername() throws Exception {
        try {
            server.start();
            String url = "https://localhost:4080/hello";
            HttpResponse response = sendHttps(url, null);

            StatusLine statusLine = response.getStatusLine();
            Assert.assertEquals(statusLine.getStatusCode(), 400);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }

    }
    /*
    public static void main(String[] args){
        //System.out.println(sendGet("http://localhost:8080/hello", "username=guo"));

        String url = "https://localhost:4080/hello?username=guo";
        System.out.println(url);
        System.out.println(sendHttps(url,null).getEntity().toString());

    }
    */
}
