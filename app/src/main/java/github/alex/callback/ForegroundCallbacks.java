package github.alex.callback;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import github.alex.util.LogUtil;

/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class ForegroundCallbacks implements Application.ActivityLifecycleCallbacks {

	public static final long CHECK_DELAY = 500;
	public static final String TAG = ForegroundCallbacks.class.getName();

	public interface AppStatusListener {

		 void onBecameForeground();

		 void onBecameBackground();

	}

	private static ForegroundCallbacks instance;

	private boolean foreground = false, paused = true;
	private Handler handler = new Handler();
	private List<AppStatusListener> listeners = new CopyOnWriteArrayList<AppStatusListener>();
	private Runnable check;

	/**
	 * Its not strictly necessary to use this method - _usually_ invoking get
	 * with a Context gives us a path to retrieve the Application and
	 * initialise, but sometimes (e.g. in test harness) the ApplicationContext
	 * is != the Application, and the docs make no guarantees.
	 * 
	 * @param application 应用
	 * @return an initialised Foreground instance
	 */
	public static ForegroundCallbacks init(Application application) {
		if (instance == null) {
			instance = new ForegroundCallbacks();
			application.registerActivityLifecycleCallbacks(instance);
		}
		return instance;
	}

	public static ForegroundCallbacks get(Application application) {
		if (instance == null) {
			init(application);
		}
		return instance;
	}

	public static ForegroundCallbacks get(Context ctx) {
		if (instance == null) {
			Context appCtx = ctx.getApplicationContext();
			if (appCtx instanceof Application) {
				init((Application) appCtx);
			}
			throw new IllegalStateException(
					"Foreground is not initialised and "
							+ "cannot obtain the Application object");
		}
		return instance;
	}

	public static ForegroundCallbacks get() {
		if (instance == null) {
			throw new IllegalStateException(
					"Foreground is not initialised - invoke "
							+ "at least once with parameterised init/get");
		}
		return instance;
	}

	public boolean isForeground() {
		return foreground;
	}

	public boolean isBackground() {
		return !foreground;
	}

	public void addListener(AppStatusListener listener) {
		listeners.add(listener);
	}

	public void removeListener(AppStatusListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void onActivityResumed(Activity activity) {
		paused = false;
		boolean wasBackground = !foreground;
		foreground = true;

		if (check != null)
			handler.removeCallbacks(check);

		if (wasBackground) {
			LogUtil.w("went foreground");
			for (AppStatusListener l : listeners) {
				try {
					l.onBecameForeground();
				} catch (Exception exc) {
					LogUtil.e("有异常："+exc);
				}
			}
		} else {
			LogUtil.w("still foreground");
		}
	}

	@Override
	public void onActivityPaused(Activity activity) {
		paused = true;

		if (check != null)
			handler.removeCallbacks(check);

		handler.postDelayed(check = new Runnable() {
			@Override
			public void run() {
				if (foreground && paused) {
					foreground = false;
					LogUtil.w("went background");
					for (AppStatusListener l : listeners) {
						try {
							l.onBecameBackground();
						} catch (Exception exc) {
							LogUtil.e("有异常："+exc);
						}
					}
				} else {
					LogUtil.w("still foreground");
				}
			}
		}, CHECK_DELAY);
	}

	@Override
	public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
	}

	@Override
	public void onActivityStarted(Activity activity) {
	}

	@Override
	public void onActivityStopped(Activity activity) {
	}

	@Override
	public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
	}

	@Override
	public void onActivityDestroyed(Activity activity) {
	}
}
