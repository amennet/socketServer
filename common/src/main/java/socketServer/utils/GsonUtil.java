package socketServer.utils;

import com.google.gson.Gson;
import com.google.gson.JsonNull;

import java.io.Serializable;
import java.lang.reflect.Type;

public class GsonUtil implements Serializable {
    private static final long serialVersionUID = 1L;

    private static Gson gson = new Gson();

    private GsonUtil() {

    }

    /**
     * 将对象转为JSON串
     * @param src 将要被转化的对象
     * @return 转化后的JSON串
     */
    public static String toJson(Object src) {
        if (src == null) {
            return gson.toJson(JsonNull.INSTANCE);
        }
        return gson.toJson(src);
    }

    /**
     * 用来将JSON串转为对象，但此方法不支持转带泛型的集合
     * @param json
     * @param classOfT classOfT
     * @param <T>
     * @return
     */
    public static <T> Object fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, (Type) classOfT);
    }

    /**
     * 用来将JSON串转为对象，此方法可用来转带泛型的集合，如：Type为
     * new TypeToken<List<T>>(){}.getType(),
     * 其它类也可以用此方法调用，将List<T>替换为你想要转成的类
     * @param json
     * @param typeOfT
     * @return
     */
    public static Object fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

}