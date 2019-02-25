package ru.kulikovman.dices.database.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ru.kulikovman.dices.model.CubeLite;

public class CubeLiteConverter {

    @TypeConverter
    public static List<CubeLite> fromString(String data) {
        Type listType = new TypeToken<List<CubeLite>>() {
        }.getType();
        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String fromList(List<CubeLite> cubeHistories) {
        Gson gson = new Gson();
        return gson.toJson(cubeHistories);
    }
}
