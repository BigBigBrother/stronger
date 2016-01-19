package Utils;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/1/14.
 */
public class HttpUtils {

    public static String postResult;

/*    public static String getReultForHttpPost1(String url,String name, String pwd)
            throws ClientProtocolException, IOException {
        String strResult = null;
        String httpUrl = url;
        // HttpPost连接对象
        HttpPost httpRequest = new HttpPost(httpUrl);
        // 使用NameValuePair来保存要传递的Post参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        // 添加要传递的参数
        params.add(new BasicNameValuePair("tlp", name));
        params.add(new BasicNameValuePair("pp", pwd));
        // 设置字符集
        HttpEntity httpentity = new UrlEncodedFormEntity(params, "gb2312");
        // 请求httpRequest
        httpRequest.setEntity(httpentity);
        // 取得默认的HttpClient
        HttpClient httpclient = new DefaultHttpClient();
        // 取得HttpResponse
        HttpResponse httpResponse = httpclient.execute(httpRequest);
        // HttpStatus.SC_OK表示连接成功
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // 取得返回的字符串;
            strResult = EntityUtils.toString(httpResponse.getEntity());
        }
        return strResult;
    }*/

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
//        // 执行post方法
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
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable error) {
                // 失败处理的方法
                error.printStackTrace();
            }
        });
    }
}
