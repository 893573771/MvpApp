package github.alex.util;

import android.util.Log;

import java.net.ConnectException;
import java.net.HttpRetryException;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.PortUnreachableException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.util.concurrent.TimeoutException;

public class HttpErrorUtil
{
	public static final String TAG = "#HttpErrorUtil#";
	/**将状态码，转成普通话！*/
	public static String getMessage(String  errorMessage)
	{
		String message = "网络问题";
		if(errorMessage == null){
			message = "找不到服务器";
		}else if(errorMessage.startsWith("Unable to resolve host")){
			message = "找不到 "+errorMessage.substring(errorMessage.indexOf("\"")+1, errorMessage.lastIndexOf("\""));
		}
		//message = "网络问题";
		return message;
	}
	public static String getMessage(Throwable e){
		int code = 404;
		String message = "网络不稳定";
		Log.e(TAG, e+"");
		if(e instanceof TimeoutException){
			code = 404;
			message = "超时异常";
		}else if(e instanceof SocketTimeoutException){
			code = 404;
			message = "套接字超时异常";
		}else if(e instanceof ConnectException){
			code = 404;
			message = "连接服务器异常";
		}else if(e instanceof UnknownServiceException){
			code = 404;
			message = "未知服务异常";
		}else if(e instanceof UnknownHostException){
			code = 404;
			message  ="未知的主机异常";
		}else if(e instanceof SocketException){
			code = 404;
			message = "套接字异常";
		}else if(e instanceof ProtocolException){
			code = 404;
			message  ="请求协议异常";
		}else if(e instanceof PortUnreachableException){
			code = 404;
			message = "端口不能被访问";
		}else if(e instanceof NoRouteToHostException){
			code = 404;
			message = "无法访问主机路由";
		}else if(e instanceof MalformedURLException){
			code = 404;
			message = "请求地址无法解析";
		}else if(e instanceof HttpRetryException){
			code = 404;
			message = "无法重新请求";
		}else{
			code = 404;
			message = getMessage(e.getMessage());
		}
		return getMessage(code, message);
	}
	/**将状态码，转成普通话！*/
	public static String getMessage(int  code)
	{
		String message;
		if(code == 0){
			code = 404;
			message = "找不到服务器！";
		}else if(code == 202){
			message = "服务器已接受，尚未处理！";
		}else if(code == 204){
			message = "服务器已接受，无对应内容！";
		}else if(code == 301){
			message = "被请求的资源，已经永久移动到新位置了！";
		}else if(code == 400){
			message = "服务器不理解请求语法！";
		}else if(code == 403){
			message = "服务器拒绝您的请求！";
		}else if(code == 404){
			message = "找不到服务器！";
		}else if(code == 405){
			message = "请求资源被禁止！";
		}else if(code == 406){
			message = "服务器，无法接受请求！";
		}else if(code == 408){
			message = "请求超时！";
		}else if(code == 410){
			message = "被请求的资源，已经永久移动到未知位置！";
		}else if(code == 500){
			message = "内部服务器异常！";
		}else if(code == 501){
			message = "服务器不具备，被请求功能！";
		}else if(code == 502){
			message = "网关错误！";
		}else if(code == 503){
			message = "服务器，暂停服务！";
		}else{
			message = "其他异常";
		}
		//message = "网络问题";
		return getMessage(code, message);
	}

	public static String getMessage(int code, String message){
		return /*code+" "+*/message;
	}
	
}