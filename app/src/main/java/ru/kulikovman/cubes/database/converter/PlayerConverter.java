package ru.kulikovman.cubes.database.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ru.kulikovman.cubes.model.CubeLite;
import ru.kulikovman.cubes.model.Player;

public class PlayerConverter {

    @TypeConverter
    public static List<Player> fromString(String data) {
        Type listType = new TypeToken<List<Player>>() {
        }.getType();
        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String fromList(List<Player> players) {
        Gson gson = new Gson();
        return gson.toJson(players);
    }
}
