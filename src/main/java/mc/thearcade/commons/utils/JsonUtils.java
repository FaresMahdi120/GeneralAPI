package mc.thearcade.commons.utils;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public final class JsonUtils {

    private JsonUtils() {}

    public static final Gson GSON = new GsonBuilder().addDeserializationExclusionStrategy(new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            return f.getAnnotation(DoNotSerialize.class) != null;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    }).serializeNulls().create();


    public static String toJson(Object object) {
        return GSON.toJson(object);
    }


    public static <T> T castJson(String json, Class<T> clazz) {
        try {
            return GSON.fromJson(json, clazz);
        }
        catch (JsonSyntaxException e) {
            return null;
        }
    }

    public static <T> T castJson(String json, Type type) {
        try {
            return GSON.fromJson(json, type);
        }
        catch (JsonSyntaxException e) {
            return null;
        }
    }

    public static <T> T castJson(String json, Class<T> clazz, T def) {
        try {
            return GSON.fromJson(json, clazz);
        }
        catch (JsonSyntaxException e) {
            return def;
        }
    }

    public static <T> T castJson(File file, Class<T> clazz) {
        try {
            if (!file.exists()) return null;
            FileReader fr = new FileReader(file);
            T t = GSON.fromJson(fr, clazz);
            fr.close();
            return t;
        }
        catch (JsonSyntaxException | IOException e) {
            return null;
        }
    }

    public static <T> T castJson(File file, Class<T> clazz, T def) {
        try {
            if (!file.exists()) return def;
            FileReader fr = new FileReader(file);
            T t = GSON.fromJson(fr, clazz);
            fr.close();
            return t;
        }
        catch (JsonSyntaxException |IOException e) {
            return def;
        }
    }

    public static <T> void writeJson(File file, T object, Type type) {
        try {
            if (!file.exists()) file.createNewFile();
            FileWriter fw = new FileWriter(file);
            JsonWriter writer = new JsonWriter(fw);
            GSON.toJson(object, type, writer);
            writer.close();
            fw.close();
        }
        catch (JsonSyntaxException |IOException e) {
            e.printStackTrace();
        }
    }

}
