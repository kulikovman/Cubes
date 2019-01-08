package ru.kulikovman.cubes.model;

public class Field {

    private int width;
    private int height;

    // Матрица поля: 1 - клетка занята, 0 - свободна
    private int[][] matrix;

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        matrix = new int[height][width];
    }



}
