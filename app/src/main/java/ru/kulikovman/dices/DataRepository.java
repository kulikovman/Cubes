package ru.kulikovman.dices;

import java.util.List;

import ru.kulikovman.dices.database.AppDatabase;
import ru.kulikovman.dices.model.ThrowResult;

public class DataRepository {

    private static DataRepository instance;

    private final AppDatabase database;

    private DataRepository() {
        database = App.getInstance().getDatabase();
    }

    public static DataRepository get() {
        if (instance == null) {
            synchronized (DataRepository.class) {
                if (instance == null) {
                    instance = new DataRepository();
                }
            }
        }

        return instance;
    }

    public void saveThrowResult(ThrowResult throwResult) {
        // Записываем результат броска и удаляем старые записи
        database.throwResultDao().insert(throwResult);
        database.throwResultDao().deleteOldestRecords(10);
    }

    public List<ThrowResult> getThrowResultList() {
        return database.throwResultDao().getAll();
    }



}
