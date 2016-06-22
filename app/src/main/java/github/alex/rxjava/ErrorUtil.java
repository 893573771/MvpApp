package github.alex.rxjava;

public class ErrorUtil
{
	/**UTF-8*/
	public static String charset = "UTF-8";
	/**上次已经下载完成*/
	public static String alreadyFinish = "maybe the file has downloaded completely";

	/**将状态码，转成普通话！*/
	public static String getErrorMessage(String  errorMessage)
	{
		String message = "网络问题";
		if(errorMessage == null){
			message = "0 未知异常";
		}else if(errorMessage.startsWith("Unable to resolve host")){
			message = "404 找不到 "+errorMessage.substring(errorMessage.indexOf("\"")+1, errorMessage.lastIndexOf("\""));
		}
		//message = "网络问题";
		return message;
	}
	/**将状态码，转成普通话！*/
	public static String getErrorMessage(int  errorCode)
	{
		String message;
		if(errorCode == 0){
			message = "0 找不到服务器！";
		}else if(errorCode == 202){
			message = errorCode+" 服务器已接受，尚未处理！";
		}else if(errorCode == 204){
			message = errorCode+" 服务器已接受，无对应内容！";
		}else if(errorCode == 301){
			message = errorCode+" 被请求的资源，已经永久移动到新位置了！";
		}else if(errorCode == 400){
			message = errorCode+" 服务器不理解请求语法！";
		}else if(errorCode == 403){
			message = errorCode+" 服务器拒绝您的请求！";
		}else if(errorCode == 404){
			message = errorCode+" 找不到服务器！";
		}else if(errorCode == 405){
			message = errorCode+" 请求资源被禁止！";
		}else if(errorCode == 406){
			message = errorCode+" 服务器，无法接受请求！";
		}else if(errorCode == 408){
			message = errorCode+" 请求超时！";
		}else if(errorCode == 410){
			message = errorCode+" 被请求的资源，已经永久移动到未知位置！";
		}else if(errorCode == 500){
			message = errorCode+" 内部服务器异常！";
		}else if(errorCode == 501){
			message = errorCode+" 服务器不具备，被请求功能！";
		}else if(errorCode == 502){
			message = errorCode+" 网关错误！";
		}else if(errorCode == 503){
			message = errorCode+" 服务器，暂停服务！";
		}else if(errorCode == 504){
			message = errorCode+" 网络不给力！";
		}else{
			message = errorCode+"  其他异常";
		}
		//message = "网络问题";
		return message;
	}
	
}