package com.ns.common.util.gson;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuezhucao on 16/2/4.
 */
public class GsonUtil {

    public static Gson getGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }

    public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        return getGson().fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, TypeToken<T> typeToken) throws JsonSyntaxException {
        return getGson().fromJson(json, typeToken.getType());
    }

    public static <T> List<T> listFromJson(String json, Class<T> classOfT) {
        JsonParser parser = new JsonParser();
        JsonArray jsonElements = parser.parse(json).getAsJsonArray();

        ArrayList<T> lcs = new ArrayList<>(jsonElements.size());

        for (JsonElement obj : jsonElements) {
            T cse = getGson().fromJson(obj, classOfT);
            lcs.add(cse);
        }
        return lcs;
    }

    public static <T> String toJson(T obj) {
        return getGson().toJson(obj);
    }

    public static boolean isJson(String json) {
        try {
            GsonUtil.fromJson(json, JsonObject.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
