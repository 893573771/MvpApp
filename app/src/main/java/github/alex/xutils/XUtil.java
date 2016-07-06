package github.alex.xutils;
import org.xutils.common.util.KeyValue;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;

import java.util.List;
public class XUtil
{
	/**UTF-8*/
	public static String charset = "UTF-8";
	/**上次已经下载完成*/
	public static String alreadyFinish = "maybe the file has downloaded completely";
	
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
		}else{
			message = errorCode+"  其他异常";
		}
		//message = "网络问题";
		return message;
	}
	
	/**将DB 异常信息转换成 普通话*/
	public static String getDBErrorInfo(DbException e){
		String dbError = e.toString();
		if("com.lidroid.xutils.exception.DbException: android.database.sqlite.SQLiteConstraintException: column id is not unique (code 19)".equalsIgnoreCase(dbError)){
			return "id 要保证唯一性！";
		}else{
			return dbError;
		}
	}
	public static String params2String(RequestParams requestParams){
		StringBuilder builder = new StringBuilder();
		List<KeyValue> bodyParams = requestParams.getBodyParams();
		List<KeyValue> queryStringParams = requestParams.getQueryStringParams();
		List<KeyValue> stringParams = requestParams.getStringParams();
		String uri = requestParams.getUri();
		builder.append("\n链接串 = "+uri);
		//KeyValue
		if((requestParams.getHeaders()!=null) && (requestParams.getHeaders().size()>0)){
			builder.append("\n请求头：\n");
		}
		for (int i = 0; (requestParams.getHeaders()!=null) && (i<requestParams.getHeaders().size()); i++){
			KeyValue keyValue = requestParams.getHeaders().get(i);
			builder.append("\n"+keyValue.key+" = "+keyValue.value);
		}
		if((queryStringParams!=null) && (queryStringParams.size() > 0)){
			builder.append("\n请求行：\n");
		}
		for (int i = 0; (queryStringParams!=null) && (i<queryStringParams.size()); i++)
		{
			KeyValue keyValue = queryStringParams.get(i);
			builder.append("\n"+keyValue.key+" = "+keyValue.value);
		}
		for (int i = 0; (stringParams!=null) && (i<stringParams.size()); i++)
		{
			KeyValue keyValue = stringParams.get(i);
			builder.append("\n"+keyValue.key+" = "+keyValue.value);
		}
		
		if((bodyParams!=null) && (bodyParams.size() >0)){
			builder.append("\n请求体：\n");
		}
		for (int i = 0; (bodyParams!=null) && (i<bodyParams.size()); i++)
		{
			KeyValue keyValue = bodyParams.get(i);
			builder.append("\n"+keyValue.key+" = "+keyValue.value);
		}
		return builder.toString();
	}
	
}