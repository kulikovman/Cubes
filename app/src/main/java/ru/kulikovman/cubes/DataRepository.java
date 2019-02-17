package ru.kulikovman.cubes;

import java.util.List;

import ru.kulikovman.cubes.database.AppDatabase;
import ru.kulikovman.cubes.model.RollResult;

public class DataRepository {

    private static DataRepository instance;

    private final AppDatabase database;

    private DataRepository() {
        database = App.getInstance().getDatabase();
    }

    public static DataRepository getInstance() {
        if (instance == null) {
            synchronized (DataRepository.class) {
                if (instance == null) {
                    instance = new DataRepository();
                }
            }
        }

        return instance;
    }

    public void saveRollResult(RollResult rollResult) {
        // Записываем результат броска и удаляем старые записи
        database.rollResultDao().insert(rollResult);
        database.rollResultDao().deleteOldestRecords(10);
    }

    public List<RollResult> getRollResultList () {
        return database.rollResultDao().getAll();
    }



}
