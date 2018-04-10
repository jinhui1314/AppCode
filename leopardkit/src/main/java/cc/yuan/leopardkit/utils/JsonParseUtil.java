package cc.yuan.leopardkit.utils;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by Yuan on 2016/9/1.
 * json model 转化工具
 */
public class JsonParseUtil {

    private static final String LIST_TAG = "list";
    private static final String OBJ_TAG = "obj";
    private static Gson gson = new Gson();

    public static String mapToJson(Map<String, Object> map) {

        String result = "{";
        String result1 = "{";
        if (!map.isEmpty()) {
            for (String key : map.keySet()) {
                if (map.get(key) instanceof String) {
                    if (map.get(key) instanceof Map) {
                        for (Object key1 : ((Map) map.get(key)).keySet()) {
                            if (((Map) map.get(key)).get(key1) instanceof String) {

                                result1 += "\"" + key1 + "\"" + ":" + "\"" + ((Map) map.get(key)).get(key1) + "\"" + ",";
                            } else {
                                result1 += "\"" + key1 + "\"" + ":" + ((Map) map.get(key)).get(key1) + ",";

                            }
                        }
                        result1 = result1.substring(0, result1.length() - 1) + "}";
                        result += "\"" + key + "\"" + ":" + "\"" + result1 + "\"" + ",";

                    } else {
                        result += "\"" + key + "\"" + ":" + "\"" + map.get(key) + "\"" + ",";
                    }

                } else {
                    if (map.get(key) instanceof Map) {
                        for (Object key1 : ((Map) map.get(key)).keySet()) {
                            if (((Map) map.get(key)).get(key1) instanceof String) {

                                result1 += "\"" + key1 + "\"" + ":" + "\"" + ((Map) map.get(key)).get(key1) + "\"" + ",";
                            } else {
                                result1 += "\"" + key1 + "\"" + ":" + ((Map) map.get(key)).get(key1) + ",";
                            }
                        }
                        result1 = result1.substring(0, result1.length() - 1) + "}";

                        result += "\"" + key + "\"" + ":" + result1 + ",";


                    } else {
                        result += "\"" + key + "\"" + ":" + map.get(key) + ",";
                    }
                }

            }
        }

        return result.substring(0, result.length() - 1) + "}";
    }

    /**
     * 实体转化为json
     *
     * @param bean
     * @return
     */
    public static <T> String modeToJson(T bean) {
        return gson.toJson(bean);
    }

    /**
     * json转换为实体
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T jsonToMode(String json, Class<T> cls) {
        return gson.fromJson(json, cls);
    }

    /**
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonArrayToList(String json, Class<T> cls) {
        Type type = new TypeToken<ArrayList<T>>() {
        }.getType();
        return gson.fromJson(json, type);
    }


}
