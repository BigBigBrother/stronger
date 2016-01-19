package Utils;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/1/14.
 */
public class HttpUtils {

    public static String postResult;


    /**
     * 采用AsyncHttpClient的Post方式进行实现
     * @param url
     * @param key
     * @param value
     */
    public static String AsyncHttpClientPost(String url, String[] key,String[] value) {
        AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
        // 创建请求参数的封装的对象
        RequestParams params = new RequestParams();
        for (int i = 0; i < key.length; i++){
            params.put(key[i], value[i]); // 设置请求的参数名和参数值
        }
        // 执行post方法
        client.post(url, params, new AsyncHttpResponseHandler() {
            /**
             * 成功处理的方法
             * statusCode:响应的状态码; headers:相应的头信息 比如 响应的时间，响应的服务器 ;
             * responseBody:响应内容的字节
             */
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String result = new String(responseBody); // 设置显示的文本
                    Log.w("ZH-DEBUG","onSuccess result = "+result);
                    HttpUtils.postResult = result;
                }
            }

            /**
             * 失败处理的方法
             * error：响应失败的错误信息封装到这个异常对象中
             */
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                error.printStackTrace();
            }
        });
        return HttpUtils.postResult;
    }

    /**
     * 采用AsyncHttpClient的Get方式进行实现
     * @param url
     * @param key
     * @param value
     */
    public void AsyncHttpClientGet(String url, String[] key,String[] value) {
        AsyncHttpClient client = new AsyncHttpClient();
        // 创建请求参数的封装的对象
        RequestParams params = new RequestParams();
        for (int i = 0; i < value.length; i++){
            params.put(key[i], value[i]); // 设置请求的参数名和参数值
        }
        // 发送get请求的时候 url地址 相应参数,匿名回调对象
        client.get(url, params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // 成功处理的方法
                System.out.println("statusCode-------------------" + statusCode);
                // 遍历头信息
                for (int i = 0; i < headers.length; i++) {
                    Header header = headers[i];
                    System.out.println("header------------Name:"
                            + header.getName() + ",--Value:"
                            + header.getValue());
                }
                // 设置控件内容
//                tv_result.setText(new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // 失败处理的方法
                error.printStackTrace();
            }
        });
    }

    /**
     * 通过url请求获取JsonString
     * @param requestUrl
     * @return jsonStr
     */
    public static String getJsonString(String requestUrl) {
        StringBuffer strBuf;
        strBuf = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(15000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));// 转码。
            String line = null;
            while ((line = reader.readLine()) != null)
                strBuf.append(line + " ");
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strBuf.toString();
    }
}
