package github.alex.xutils;

import java.io.File;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.ex.HttpException;

public abstract class BaseFileCallback implements Callback.ProgressCallback<File> {
	/**标记 请求类型*/
	public String tag;
	public BaseFileCallback(String callback){
		this.tag = callback;
		onStarted();
	}
	@Override
	public void onStarted(){
		
	}
	@Override
	public void onWaiting(){
		
	}
	@Override
	public void onError(Throwable ex, boolean isOnCallback) {
		LogUtil.w("ex = "+ex);
    	if(ex instanceof RuntimeException){
    		LogUtil.w("ex = "+ex);
    		return ;
    	}
        if(ex instanceof HttpException){
        	HttpException httpException = (HttpException) ex;
            onError(httpException.getCode(), XUtil.getErrorMessage(httpException.getCode()));
        }else if((ex.getMessage()!=null) && ex.getMessage().startsWith("failed to connect to")){
            onError(404, XUtil.getErrorMessage(404));
        }else{
            onError(0, XUtil.getErrorMessage(0));
        }
	}
	 /**处理异常*/
    public void onError(int errorCode, String errorMessage){
    	
    }

	@Override
	public void onCancelled(CancelledException cex) {
	}

	@Override
	public void onFinished() {
	
	}

	@Override
	public void onLoading(long total, long current, boolean isDownloading)
	{
		
	}
	
	@Override
	public void onSuccess(File file) {
		
	}
	
}
