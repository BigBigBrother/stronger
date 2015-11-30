package Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class AllUtils {
	
	private static final String TAG = "Utils";
	
	/**
	 * 判断是否为空2
	 * @param str
	 * @return
	 */
	public static boolean isEmptyChar(CharSequence str) {
		return isNull(str) || str.length() == 0;
	}

	public static boolean isEmptyObject(Object[] os) {
		return isNull(os) || os.length == 0;
	}

	public static boolean isEmptyCollection(Collection<?> l) {
		return isNull(l) || l.isEmpty();
	}

	public static boolean isEmptyMap(Map<?, ?> m) {
		return isNull(m) || m.isEmpty();
	}

	public static boolean isNull(Object o) {
		return o == null;
	}


	/**
	 * 弹出 snackbar
	 * @param view
	 */
	public static void ShowSnackbar(View view){
		Snackbar.make(view, "SnackBar", Snackbar.LENGTH_LONG)
				.setAction("Action", new View.OnClickListener() {
					@Override
					public void onClick(View view) {

					}
				}).show();
	}

	/**
	 * 文件大小转换
	 * @param fileS
	 * @return
	 */
    public static String FormatFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
    
	/**
	 * 获取整型的ip地址(0表示设备未联网)
	 *
	 * @param context 上下文
	 * @return 整型的ip地址
	 */
	public static int getIpAddress(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		return wifiInfo.getIpAddress();
	}

	/**
	 * 将整型的ip地址转换为点分十进制格式("192.168.21.30")
	 *
	 * @param ip int型的ip地址
	 * @return 点分十进制格式的ip
	 */
	public static String getDecimalIpAddress(int ip) {
		// 分别取出每个字节的值
		return ((ip) & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "."
				+ (ip >> 24 & 0xFF);
	}

	/**
	 * 获取ip的网络号。如:"192.168.21.30",若子网掩码为"255.255.255.0",则前三位"192.168.21"为ip的网络号,
	 * 最后一位"30"为ip的主机号
	 *
	 * @param ip int型的ip地址
	 * @return ip的网络号
	 */
	public static String getIpNetAddress(int ip) {
		return ((ip) & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF);
	}

	/**
	 * 获取ip的主机号。如:"192.168.21.30",若子网掩码为"255.255.255.0",则前三位"192.168.21"为ip的网络号,
	 * 最后一位"30"为ip的主机号
	 *
	 * @param ip int型的ip地址
	 * @return ip的主机号
	 */
	public static int getIpHostAddress(int ip) {
		// 右移24位后获取到的一个字节
		return (ip >> 24) & 0xFF;
	}

	/**
	 * 收起输入法键盘
	 *
	 * @param context   Context
	 * @param tokenView 该输入法绑定的View
	 */
	public static void colseInputMethod(Context context, View tokenView) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(tokenView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 打开输入法键盘
	 *
	 * @param context   Context
	 * @param tokenView 该输入法绑定的View
	 */
	public static void openInputMethod(Context context, View tokenView) {
		tokenView.setFocusable(true);
		tokenView.requestFocus();
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInputFromWindow(tokenView.getWindowToken(), 0, InputMethodManager.SHOW_FORCED);
	}

	/**
	 * 验证IP是否合法
	 *
	 * @param ipAddress ip地址
	 * @return ip合法返回true，否则返回false
	 */
	public static boolean verifyIP(String ipAddress) {
		String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		if (ipAddress.matches(regex)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除文件或目录
	 *
	 * @param file 文件或目录
	 * @return 是否删除成功
	 */
	public static boolean deleteFile(File file) {
		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			for (File childFile : childFiles) {
				deleteFile(childFile);
			}
		}
		return file.delete();
	}

	/**
	 * 获取应用版本号
	 *
	 * @param context 上下文
	 * @return 应用版本号
	 */
	public static String getVersionCode(Context context) {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		try {
			PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			return packInfo.versionName;
		} catch (NameNotFoundException e) {
		}
		return "";
	}

	/**
	 * 获取设备的MAC地址
	 *
	 * @param context 上下文
	 * @return 设备的MAC地址
	 */
	public static String getMacAddress(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		if (wifiInfo != null) {
			return wifiInfo.getMacAddress();
		}
		return null;
	}

	/*
	 * 设置对话框大小
	 */
	public static void setDialogSize(Activity activity, Dialog dialog) {
		WindowManager m = activity.getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
		p.height = (int) d.getHeight(); // 高度设置为屏幕的高度
		p.width = (int) d.getWidth(); // 宽度设置为屏幕的宽度
		dialog.getWindow().setAttributes(p);
	}

	/**
	 * Toast
	 *
	 * @param msg
	 * 提示内容
	 */
	private static Toast mToast = null;

	public static void toast(Context context, String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(msg);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}

		mToast.show();
	}

	/**
	 * Toast
	 *
	 * @param resId 字串资源id
	 */
	public static void toast(Context context, int resId) {
		if (mToast == null) {
			mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(resId);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}

		mToast.show();
	}

	/**
	 * 获取缓存目录
	 *
	 * @return 缓存目录
	 */
	public static String getCacheDir(Context context) {
		Log.i(TAG, "CacheDir = " + context.getExternalCacheDir());
		return context.getExternalCacheDir()+"";
	}

	/**
	 * 获取屏幕的宽度
	 */
	public static int getWindowsWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 *
	 * @param pxValue （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 *
	 * @param spValue （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * @param url 指定的任意字符串
	 * @return 指定字符串通过md5运算并转化为字符串，如果url为null或长度为0，则返回null
	 */
	public static String md5(String url) {
		String result = null;
		if (url != null && url.length() > 0) {
			try {
				MessageDigest md5 = MessageDigest.getInstance("MD5");
				md5.update(url.getBytes());
				byte[] tar = md5.digest();
				StringBuilder sb = new StringBuilder("");
				for (byte b : tar) {
					int h = (b >> 4) & 15;
					int l = b & 15;
					// 因为4位二进制数最大为1111，即15
					sb.append(Integer.toHexString(h)).append(
							Integer.toHexString(l));
				}
				result = sb.toString();

			} catch (NoSuchAlgorithmException e) {
				result = String.valueOf(url.hashCode());
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * 在卸载应用后，会自动删除该文件夹
	 * 优先使用内存卡
	 *
	 * @param context
	 * @param uniqueName 保存文件夹名称
	 * @return
	 */
	public static File getDiskCacheDir(Context context, String uniqueName) {
		String cachePath = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)
				|| !Environment.isExternalStorageRemovable()) {
			cachePath = context.getExternalCacheDir().getPath();
		} else { // 内存卡不存在,获取应用地址
			cachePath = context.getCacheDir().getPath();
		}
		File cacheFile = new File(cachePath + File.separator + uniqueName);
		// 如果文件夹不存在，则创建
		if (!cacheFile.exists()) {
			cacheFile.mkdirs();
		}
		return cacheFile;
	}

	/**
	 * 检验是否为手机号
	 *
	 * @param phoneNum
	 * @return
	 */
	public static boolean isMobile(String phoneNum) {
		Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher matcher = pattern.matcher(phoneNum);
		return matcher.matches();
	}


	public enum NetType {
		None(1),
		Mobile(2),
		Wifi(4),
		Other(8);
		NetType(int value) {
			this.value = value;
		}
		public int value;
	}

	/**
	 * 获取ConnectivityManager
	 */
	public static ConnectivityManager getConnManager(Context context) {
		return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	/**
	 * 判断网络连接是否有效（此时可传输数据）。
	 * @param context
	 * @return boolean 不管wifi，还是mobile net，只有当前在连接状态（可有效传输数据）才返回true,反之false。
	 */
	public static boolean isConnected(Context context) {
		NetworkInfo net = getConnManager(context).getActiveNetworkInfo();
		return net != null && net.isConnected();
	}

	/**
	 * 判断有无网络正在连接中（查找网络、校验、获取IP等）。
	 * @param context
	 * @return boolean 不管wifi，还是mobile net，只有当前在连接状态（可有效传输数据）才返回true,反之false。
	 */
	public static boolean isConnectedOrConnecting(Context context) {
		NetworkInfo[] nets = getConnManager(context).getAllNetworkInfo();
		if (nets != null) {
			for (NetworkInfo net : nets) {
				if (net.isConnectedOrConnecting()) { return true; }
			}
		}
		return false;
	}

	public static NetType getConnectedType(Context context) {
		NetworkInfo net = getConnManager(context).getActiveNetworkInfo();
		if (net != null) {
			switch (net.getType()) {
				case ConnectivityManager.TYPE_WIFI :
					return NetType.Wifi;
				case ConnectivityManager.TYPE_MOBILE :
					return NetType.Mobile;
				default :
					return NetType.Other;
			}
		}
		return NetType.None;
	}

	/**
	 * 是否存在有效的WIFI连接
	 */
	public static boolean isWifiConnected(Context context) {
		NetworkInfo net = getConnManager(context).getActiveNetworkInfo();
		return net != null && net.getType() == ConnectivityManager.TYPE_WIFI && net.isConnected();
	}

	/**
	 * 是否存在有效的移动连接
	 * @param context
	 * @return boolean
	 */
	public static boolean isMobileConnected(Context context) {
		NetworkInfo net = getConnManager(context).getActiveNetworkInfo();
		return net != null && net.getType() == ConnectivityManager.TYPE_MOBILE && net.isConnected();
	}

	/**
	 * 检测网络是否为可用状态
	 */
	public static boolean isAvailable(Context context) {
		return isWifiAvailable(context) || (isMobileAvailable(context) && isMobileEnabled(context));
	}

	/**
	 * 判断是否有可用状态的Wifi，以下情况返回false：
	 *  1. 设备wifi开关关掉;
	 *  2. 已经打开飞行模式；
	 *  3. 设备所在区域没有信号覆盖；
	 *  4. 设备在漫游区域，且关闭了网络漫游。
	 *  
	 * @param context
	 * @return boolean wifi为可用状态（不一定成功连接，即Connected）即返回ture
	 */
	public static boolean isWifiAvailable(Context context) {
		NetworkInfo[] nets = getConnManager(context).getAllNetworkInfo();
		if (nets != null) {
			for (NetworkInfo net : nets) {
				if (net.getType() == ConnectivityManager.TYPE_WIFI) { return net.isAvailable(); }
			}
		}
		return false;
	}

	/**
	 * 判断有无可用状态的移动网络，注意关掉设备移动网络直接不影响此函数。
	 * 也就是即使关掉移动网络，那么移动网络也可能是可用的(彩信等服务)，即返回true。
	 * 以下情况它是不可用的，将返回false：
	 *  1. 设备打开飞行模式；
	 *  2. 设备所在区域没有信号覆盖；
	 *  3. 设备在漫游区域，且关闭了网络漫游。
	 * 
	 * @param context
	 * @return boolean
	 */
	public static boolean isMobileAvailable(Context context) {
		NetworkInfo[] nets = getConnManager(context).getAllNetworkInfo();
		if (nets != null) {
			for (NetworkInfo net : nets) {
				if (net.getType() == ConnectivityManager.TYPE_MOBILE) { return net.isAvailable(); }
			}
		}
		return false;
	}

	/**
	 * 设备是否打开移动网络开关
	 * @param context
	 * @return boolean 打开移动网络返回true，反之false
	 */
	public static boolean isMobileEnabled(Context context) {
		try {
			Method getMobileDataEnabledMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled");
			getMobileDataEnabledMethod.setAccessible(true);
			return (Boolean) getMobileDataEnabledMethod.invoke(getConnManager(context));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 反射失败，默认开启
		return true;
	}

	/**
	 * 打印当前各种网络状态
	 * @param context
	 * @return boolean
	 */
	public static boolean printNetworkInfo(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo in = connectivity.getActiveNetworkInfo();
			Log.i(TAG, "-------------$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$-------------");
			Log.i(TAG, "getActiveNetworkInfo: " + in);
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					// if (info[i].getType() == ConnectivityManager.TYPE_WIFI) {
					Log.i(TAG, "NetworkInfo[" + i + "]isAvailable : " + info[i].isAvailable());
					Log.i(TAG, "NetworkInfo[" + i + "]isConnected : " + info[i].isConnected());
					Log.i(TAG, "NetworkInfo[" + i + "]isConnectedOrConnecting : " + info[i].isConnectedOrConnecting());
					Log.i(TAG, "NetworkInfo[" + i + "]: " + info[i]);
					// }
				}
				Log.i(TAG, "\n");
			} else {
				Log.i(TAG, "getAllNetworkInfo is null");
			}
		}
		return false;
	}

	/**
	 * 打开网络设置界面
	 */
	public static void openSetting(Activity activity) {
		Intent intent = new Intent("/");
		ComponentName cm = new ComponentName("com.android.settings",
				"com.android.settings.WirelessSettings");
		intent.setComponent(cm);
		intent.setAction("android.intent.action.VIEW");
		activity.startActivityForResult(intent, 0);
	}


	/**
	 * 日志写入sdcard
	 */
	//public static final Logger logger = LoggerFactory.getLogger(MainActivity.class);


	/**
	 * 下载文件
	 * 
	 * @param downloadUrl
	 *            下载路径
	 * @param saveFile
	 *            文件保存路径
	 * @return
	 * @throws Exception
	 */
	public long downloadUpdateFile(String downloadUrl, File saveFile,NotificationManager manager,Notification notification)
			throws Exception {
		
		//在这个方法里初始化Notification相关的一些
		
		int downloadCount = 0;
		int currentSize = 0;
		long totalSize = 0;
		int updateTotalSize = 0;
		HttpURLConnection httpConnection = null;
		InputStream is = null;
		FileOutputStream fos = null;

		try {
			URL url = new URL(downloadUrl);
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection
					.setRequestProperty("User-Agent", "PacificHttpClient");
			if (currentSize > 0) {
				httpConnection.setRequestProperty("RANGE", "bytes="
						+ currentSize + "-");
			}
			httpConnection.setConnectTimeout(10000);
			httpConnection.setReadTimeout(20000);
			updateTotalSize = httpConnection.getContentLength();
			if (httpConnection.getResponseCode() == 404) {
				throw new Exception("fail!");
			}
			is = httpConnection.getInputStream();
			fos = new FileOutputStream(saveFile, false);
			byte buffer[] = new byte[4096];
			int readsize = 0;
			while ((readsize = is.read(buffer)) > 0) {
				fos.write(buffer, 0, readsize);
				totalSize += readsize;
				// 为了防止频繁的通知导致应用吃紧，百分比增加10才通知一次
				// if ((downloadCount == 0) || (int) (totalSize * 100 /
				// updateTotalSize) - 10 > downloadCount) {
				// downloadCount += 10;
				// updataNotification.setLatestEventInfo(UpdataVersionSerivce.this,
				// "正在下载", (int) totalSize * 100
				// / updateTotalSize + "%", updataPendingIntent);
				// updataNotificationManager.notify(0, updataNotification);
				// }
			}
		} finally {
			if (httpConnection != null) {
				httpConnection.disconnect();
			}
			if (is != null) {
				is.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
		return totalSize;
	}
}
