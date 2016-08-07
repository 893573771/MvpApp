package github.alex.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */

public class ParcelableMap implements Parcelable {
    public static final String extraBundle = "extraBundle";
    public static final String bundleKey = "bundleKey";
    private Bundle bundle;
    private ArrayList<String> keyList;
    public ParcelableMap() {
        bundle = new Bundle();
        keyList = new ArrayList<String>();
    }
    public ParcelableMap put(String key, Parcelable value){
        bundle.putParcelable(key, value);
        keyList.add(key);
        return this;
    }

    /**
     * 往 map中添加数据
     */
    public ParcelableMap put(String key, int value) {
        bundle.putInt(key,value);
        keyList.add(key);
        return this;
    }
    /**
     * 往 map中添加数据
     */
    public ParcelableMap put(String key, long value) {
        bundle.putLong(key, value);
        keyList.add(key);
        return this;
    }
    /**
     * 往 map中添加数据
     */
    public ParcelableMap put(String key, Double value) {
        bundle.putDouble(key, value);
        keyList.add(key);
        return this;
    }
    /**
     * 往 map中添加数据
     */
    public ParcelableMap put(String key, String value) {
        bundle.putString(key, value);
        keyList.add(key);

        return this;
    }

    /**
     * 往 map中添加数据
     */
    public ParcelableMap put(String key, Boolean value) {
        bundle.putBoolean(key, value);
        keyList.add(key);
        return this;
    }
    /**
     * 往 map中添加数据
     */
    public ParcelableMap put(String key, byte value) {
        bundle.putByte(key, value);
        keyList.add(key);
        return this;
    }

    public Bundle getBundle(){
        return bundle;
    }

    public ArrayList<String> getKeyList() {
        return keyList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.bundle);
        dest.writeStringList(this.keyList);
    }

    protected ParcelableMap(Parcel in) {
        this.bundle = in.readBundle();
        this.keyList = in.createStringArrayList();
    }

    public static final Parcelable.Creator<ParcelableMap> CREATOR = new Parcelable.Creator<ParcelableMap>() {
        @Override
        public ParcelableMap createFromParcel(Parcel source) {
            return new ParcelableMap(source);
        }

        @Override
        public ParcelableMap[] newArray(int size) {
            return new ParcelableMap[size];
        }
    };
}
