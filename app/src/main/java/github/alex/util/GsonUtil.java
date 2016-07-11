package github.alex.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by Alex on 2016/7/9.
 */

@SuppressWarnings("ALL")
public class GsonUtil {
    private static Gson gson;

    /**
     * 过滤掉gson解析的异常信息
     */
    public static Gson gson() {
        if (gson == null) {
            synchronized (GsonUtil.class) {
                gson = (gson == null) ? new GsonBuilder().registerTypeAdapterFactory(new GsonUtil.SafeTypeAdapterFactory()).create() : gson;
            }
        }
        return gson;
    }

    public final static class SafeTypeAdapterFactory implements TypeAdapterFactory {

        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            return gson.getDelegateAdapter(this, type);
        }
    }

    private static final class MyTypeAdapter extends TypeAdapter {
        TypeAdapter typeAdapter;
        TypeToken type;

        public MyTypeAdapter(TypeAdapter typeAdapter, TypeToken type) {
            this.typeAdapter = typeAdapter;
            this.type = type;
        }

        @Override
        public void write(JsonWriter out, Object value) throws IOException {
            try {
                typeAdapter.write(out, value);
            } catch (IOException e) {
                typeAdapter.write(out, null);
            }
        }

        @Override
        public Object read(JsonReader in) throws IOException {
            try {
                return typeAdapter.read(in);
            } catch (IOException e) {
                in.skipValue();
                return null;
            } catch (IllegalStateException e) {
                in.skipValue();
                return null;
            } catch (JsonSyntaxException e) {
                in.skipValue();
                if (type.getType() instanceof Class) {
                    try {
                        return ((Class) type.getType()).newInstance();
                    } catch (Exception e1) {

                    }
                }
                return null;
            }
        }
    }
}
