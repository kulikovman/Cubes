package ru.kulikovman.cubes.helper;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.kulikovman.cubes.data.CubeType;
import ru.kulikovman.cubes.model.Calculation;
import ru.kulikovman.cubes.model.Cube;
import ru.kulikovman.cubes.model.Settings;

public class CubeGenerator {

    private static final int FAILURE_LIMIT = 30; // Лимит неудачных бросков (с пересечением кубиков)

    public static List<Cube> getCubes(Calculation calculation, Settings settings) {
        CubeType cubeType = CubeType.valueOf(settings.getCubeType());
        int numberOfCubes = settings.getNumberOfCubes();

        List<Cube> cubes = new ArrayList<>();

        // Генерируем необходимое количество кубиков
        while (cubes.size() < numberOfCubes) {
            Cube cube = new Cube(calculation, cubeType);

            if (cubes.isEmpty()) {
                cubes.add(cube);
                Log.d("myLog", "Add cube " + cubes.size() + ": " + cube.getX() + ", " + cube.getY());
            } else {
                // Проверяем пересечение с другими кубиками
                int count = 0;
                boolean intersection = true;
                while (intersection) {
                    for (Cube c : cubes) {
                        if (cube.intersection(c)) {
                            intersection = true;
                            break;
                        } else {
                            intersection = false;
                        }
                    }

                    if (intersection) {
                        // Если есть пересечение
                        count++;
                        Log.d("myLog", "cube intersection: " + count);

                        if (count < FAILURE_LIMIT) { // Защита от неудачного разброса кубиков
                            // Двигаем кубик
                            cube.moveCube();
                        } else {
                            Log.d("myLog", "Сработала защита от неудачного разброса!");
                            Log.d("myLog", "---------------------------");

                            // Очищаем списки и начинаем заново
                            cubes.clear();

                            cubes.add(cube);
                            Log.d("myLog", "Add cube " + cubes.size() + ": " + cube.getX() + ", " + cube.getY());
                            intersection = false;
                        }
                    } else {
                        // Если нет пересечений
                        cubes.add(cube);
                        Log.d("myLog", "Add cube " + cubes.size() + ": " + cube.getX() + ", " + cube.getY());
                    }
                }
            }
        }

        return cubes;
    }
}
