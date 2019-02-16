package ru.kulikovman.cubes.database.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ru.kulikovman.cubes.model.CubeHistory;

public class CubeHistoryConverter {

    @TypeConverter
    public static List<CubeHistory> fromString(String data) {
        Type listType = new TypeToken<List<CubeHistory>>() {
        }.getType();
        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String fromList(List<CubeHistory> cubeHistories) {
        Gson gson = new Gson();
        return gson.toJson(cubeHistories);
    }
}
