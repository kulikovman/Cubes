package ru.kulikovman.cubes.helper;

import ru.kulikovman.cubes.model.Area;
import ru.kulikovman.cubes.model.Calculation;
import ru.kulikovman.cubes.model.Cube;

public class Intersection {

    // Пересечение с зоной кнопки настроек и суммы кубиков
    public static boolean withTotalArea(Cube cube) {
        Area area = cube.getCalculation().getTotalArea();
        int radius = cube.getCalculation().getCubeOuterRadius();
        int x = cube.getX();
        int y = cube.getY();

        return x > area.getMinX() - radius && x < area.getMaxX() + radius && y > area.getMinY() - radius && y < area.getMaxY() + radius;
    }

    public static boolean withCube(Calculation calculation, Cube c1, Cube c2) {
        // Расчет растояния между центрами кубиков
        int distance = (int) Math.sqrt((Math.pow(Math.abs(c1.getX() - c2.getX()), 2) + Math.pow(Math.abs(c1.getY() - c2.getY()), 2)));

        // ЭТАП 1: проверка по внешним радиусам
        // Если больше суммы внешних радиусов, то все ок
        if (distance > calculation.getCubeOuterRadius() * 2) {
            return false;
        }

        // ЭТАП 2: проверка по внутренним радиусам
        // Если меньше суммы радиусов, то кубики 100% пересекаются
        if (distance < calculation.getCubeInnerRadius() * 2) {
            return true;
        }

        // ЭТАП 3: проверка пересечения вершин и кубиков
        // Первый кубик и точки второго кубика
        if (isPointInsideCube(c1, c2.getX1(), c2.getY1()) || isPointInsideCube(c1, c2.getX2(), c2.getY2()) ||
                isPointInsideCube(c1, c2.getX3(), c2.getY3()) || isPointInsideCube(c1, c2.getX4(), c2.getY4())) {
            return true;
        }

        // Второй кубик и точки первого кубика
        if (isPointInsideCube(c2, c1.getX1(), c1.getY1()) || isPointInsideCube(c2, c1.getX2(), c1.getY2()) ||
                isPointInsideCube(c2, c1.getX3(), c1.getY3()) || isPointInsideCube(c2, c1.getX4(), c1.getY4())) {
            return true;
        }

        // Кубики не пересекаются
        return false;
    }

    private static boolean isPointInsideCube(Cube cube, int px, int py) {
        // Делим куб на два треугольника и проверяем принадлежность
        // точек одного куба к треугольникам другого

        // Обозначение вершин куба
        // a - b
        // | \ |
        // d - c

        int x1 = cube.getX1();
        int x2 = cube.getX2();
        int x3 = cube.getX3();
        int x4 = cube.getX4();

        int y1 = cube.getY1();
        int y2 = cube.getY2();
        int y3 = cube.getY3();
        int y4 = cube.getY4();

        // Треугольник ABC
        int ab = (x1 - px) * (y2 - y1) - (x2 - x1) * (y1 - py);
        int bc = (x2 - px) * (y3 - y2) - (x3 - x2) * (y2 - py);
        int ca = (x3 - px) * (y1 - y3) - (x1 - x3) * (y3 - py);

        if ((ab >= 0 && bc >= 0 && ca >= 0) || (ab <= 0 && bc <= 0 && ca <= 0)) {
            //Log.d("myLog", "Точка внутри ABC!");
            return true;
        }

        // Треугольник ACD
        int ac = (x1 - px) * (y3 - y1) - (x3 - x1) * (y1 - py);
        int cd = (x3 - px) * (y4 - y3) - (x4 - x3) * (y3 - py);
        int da = (x4 - px) * (y1 - y4) - (x1 - x4) * (y4 - py);

        if ((ac >= 0 && cd >= 0 && da >= 0) || (ac <= 0 && cd <= 0 && da <= 0)) {
            //Log.d("myLog", "Точка внутри ACD!");
            return true;
        }

        // Точка находится вне куба
        return false;
    }
}
