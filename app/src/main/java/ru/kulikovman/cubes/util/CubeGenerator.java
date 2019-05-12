package ru.kulikovman.cubes.util;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.kulikovman.cubes.model.CubeType;
import ru.kulikovman.cubes.model.Calculation;
import ru.kulikovman.cubes.model.Cube;
import ru.kulikovman.cubes.model.Settings;

public class CubeGenerator {

    private static final int FAILURE_LIMIT = 30; // Лимит неудачных бросков (с пересечением кубиков)

    private static CubeGenerator instance;

    private Calculation calculation;
    private Settings settings;

    public static CubeGenerator get() {
        if (instance == null) {
            synchronized (CubeGenerator.class) {
                if (instance == null) {
                    instance = new CubeGenerator();
                }
            }
        }

        return instance;
    }

    public List<Cube> getCubes(Calculation calculation, Settings settings) {
        this.calculation = calculation;
        this.settings = settings;

        return settings.isNotRolling() ? getRowCubes() : getRollCubes();
    }

    private List<Cube> getRollCubes() {
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
                    for (Cube currentCube : cubes) {
                        if (Intersection.withCube(calculation, cube, currentCube)) {
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

    private List<Cube> getRowCubes() {
        CubeType cubeType = CubeType.valueOf(settings.getCubeType());
        List<List<Point>> points = getListOfPoints();

        // Создаем кубики на основе сгенерированных координат
        List<Cube> cubes = new ArrayList<>();
        for (List<Point> line : points) {
            for (Point point : line){
                cubes.add(new Cube(calculation, cubeType, point));
            }
        }

        return cubes;
    }

    private List<List<Point>> getListOfPoints() {
        List<List<Point>> points = new ArrayList<>();

        int cubes = settings.getNumberOfCubes();
        int rows = calculation.getMaxCubesPerHeight();
        while (cubes > 0) {
            int remainder = cubes / rows < 1 || cubes % rows != 0 ? 1 : 0;
            int cubesPerRow = cubes / rows + remainder;

            // 7 / 3 = 2 + (1) = 3
            // 4 / 2 = 2 + (0) = 2
            // 2 / 1 = 2 + (0) = 2

            // 8 / 3 = 2 + (1) = 3
            // 5 / 2 = 2 + (1) = 3
            // 2 / 1 = 2 + (0) = 2

            // 2 / 3 = 0 + (1) = 1
            // 1 / 2 = 0 + (1) = 1

            // Получаем Y последнего ряда и размер кубика
            int space = calculation.getSpaceBetweenCentersOfCubes();
            int y = points.size() != 0 ? points.get(points.size() - 1).get(0).y + space : 0;

            // Создаем линию точек и добавляем в общий список
            List<Point> line = new ArrayList<>();
            for (int i = 0; i < cubesPerRow; i++) {
                int x = line.size() != 0 ? line.get(line.size() - 1).x + space : 0;
                line.add(new Point(x, y));
            }

            points.add(line);

            // Вычитаем добавленные кубики и созданный ряд
            cubes -= cubesPerRow;
            rows--;
        }

        // Вычисляем отступы для центрирования массива точек
        int width = points.get(0).get(points.get(0).size() - 1).x;
        int height = points.get(points.size() - 1).get(0).y;
        int title = calculation.getTitleHeight();
        int offsetX = (calculation.getScreenWidth() - width) / 2;
        int offsetY = title + (calculation.getScreenHeight() - title * 2 - height) / 2;

        // Центрируем массив точек
        for (List<Point> line : points) {
            // Внутренний отступ относительно первого ряда
            int widthCurrentLine = line.get(line.size() - 1).x;
            int innerOffsetX = (width - widthCurrentLine) / 2;

            // Отступы относительно первого ряда и центра экрана
            for (Point point : line){
                point.offset(innerOffsetX, 0);
                point.offset(offsetX, offsetY);
            }
        }

        return points;
    }
}
