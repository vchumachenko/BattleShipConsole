package org.nox.board;


import org.nox.board.exceptions.BoardException;
import org.nox.board.exceptions.PositionException;
import org.nox.ships.Ship;
import org.nox.ships.utils.Direction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Board {
    // Длина доски (количество строк и столбцов, так как доска квадратная)
    private final int length;

    // Матрица доски, представляющая состояние игровой доски
    private char[][] board;

    // Количество кораблей на доске
    private int numShips = 0;

    // Константы для обозначения состояний клеток на доске
    public static final char HIT = '☒';
    public static final char MISS = '☸';
    public static final char SHIP = '☐';
    public static final char WATER = '~';

    // Конструктор, принимающий длину доски и инициализирующий ее
    public Board(int length) {
        this.length = length;
        board = initBoard();
    }

    // Конструктор, принимающий матрицу и устанавливающий ее как состояние доски
    public Board(char[][] matrix) {
        this.length = matrix.length;
        board = matrix;
    }

    // Инициализация доски и заполнение ее водой
    private char[][] initBoard() {
        char[][] matrix = new char[length][length];
        for (char[] row : matrix) {
            Arrays.fill(row, WATER);
        }
        return matrix;
    }

    // Получение длины доски (количество строк и столбцов)
    public int getLength() {
        return length;
    }

    // Получение количества кораблей на доске
    public int getNumShips() {
        return numShips;
    }

    // Получение матрицы, представляющей состояние доски
    public char[][] getBoard() {
        return board;
    }

    // Получение символа в указанной позиции на доске
    public char at(Position position) {
        return board[position.getRow()][position.getColumn()];
    }

    // Установка символа в указанной позиции на доске
    public boolean set(char status, Position position) {
        board[position.getRow()][position.getColumn()] = status;
        return true;
    }

    // Проверка, есть ли корабль в указанной позиции
    public boolean thereIsShip(Position position) {
        return at(position) == SHIP;
    }

    // Проверка, есть ли вода в указанной позиции
    public boolean thereIsWater(Position position) {
        return at(position) == WATER;
    }

    // Проверка, есть ли промах в указанной позиции
    public boolean thereIsMiss(Position position) {
        return at(position) == MISS;
    }

    // Проверка, есть ли попадание в указанной позиции
    public boolean thereIsHit(Position position) {
        return at(position) == HIT;
    }

    // Проверка, есть ли место для размещения корабля на доске
    public boolean thereIsSpace(Ship ship) {
        int l = ship.getLength();
        int x = ship.getPosition().getRow();
        int y = ship.getPosition().getColumn();
        if (ship.getDirection() == Direction.HORIZONTAL) return (length - y + 1) > l;
        else return (length - x + 1) > l;
    }

    // Проверка, есть ли другой корабль рядом с указанным кораблем
    public boolean isNearShip(Ship ship) throws PositionException {
        // Инициализация переменных для текущей позиции корабля и счетчика
        int k, row, column;
        row = ship.getPosition().getRow();
        column = ship.getPosition().getColumn();

        // Определение счетчика в зависимости от направления корабля
        if (ship.getDirection() == Direction.HORIZONTAL)
            k = column;
        else
            k = row;

        // Проверка позиций вокруг корабля
        for (int i = 0; i < ship.getLength() && k + i < length - 1; i++) {
            // Проверка, есть ли корабль вокруг текущей позиции корабля
            if (isShipAround(row, column))
                return true;

            // Обновление координат в зависимости от направления корабля
            if (ship.getDirection() == Direction.HORIZONTAL)
                column++;
            else if (ship.getDirection() == Direction.VERTICAL)
                row++;
        }

        // Возврат false, если вокруг корабля нет других кораблей
        return false;
    }


    // Проверка, есть ли корабль вокруг указанной позиции
    private boolean isShipAround(int row, int column) throws PositionException {
        ArrayList<Position> list = getAllNearPositions(row, column);
        for (Position position : list) {
            if (at(position) == SHIP) return true;
        }
        return false;
    }

    // Получение списка возможных целей для атаки вокруг указанной позиции
    public ArrayList<Position> getPossibleTarget(Position position) throws PositionException {
        int row = position.getRow(), column = position.getColumn();
        ArrayList<Position> list = new ArrayList<>();
        // Направление: север
        if (row - 1 >= 0 && !thereIsMiss(new Position(row - 1, column)) && !thereIsHit(new Position(row - 1, column)))
            list.add(new Position(row - 1, column));
        // Направление: запад
        if (column - 1 >= 0 && !thereIsMiss(new Position(row, column - 1)) && !thereIsHit(new Position(row, column - 1)))
            list.add(new Position(row, column - 1));
        // Направление: юг
        if (row + 1 < length && !thereIsMiss(new Position(row + 1, column)) && !thereIsHit(new Position(row + 1, column)))
            list.add(new Position(row + 1, column));
        // Направление: восток
        if (column + 1 < length && !thereIsMiss(new Position(row, column + 1)) && !thereIsHit(new Position(row, column + 1)))
            list.add(new Position(row, column + 1));
        return list;
    }

    // Получение списка всех позиций вокруг указанной позиции
    public ArrayList<Position> getAllNearPositions(int row, int column) throws PositionException {
        ArrayList<Position> list = new ArrayList<>();
        // Направление: север
        if (row - 1 >= 0) list.add(new Position(row - 1, column));
        // Направление: юг
        if (row + 1 < length) list.add(new Position(row + 1, column));
        // Направление: восток
        if (column + 1 < length) list.add(new Position(row, column + 1));
        // Направление: запад
        if (column - 1 >= 0) list.add(new Position(row, column - 1));
        // Направление: северо-восток
        if (row - 1 >= 0 && column + 1 < length) list.add(new Position(row - 1, column + 1));
        // Направление: северо-запад
        if (row - 1 >= 0 && column - 1 >= 0) list.add(new Position(row - 1, column - 1));
        // Направление: юго-восток
        if (row + 1 < length && column + 1 < length) list.add(new Position(row + 1, column + 1));
        // Направление: юго-запад
        if (row + 1 < length && column - 1 >= 0) list.add(new Position(row + 1, column - 1));
        return list;
    }

    // Добавление корабля на доску
    public boolean addShip(Ship ship) throws BoardException, PositionException {
        int k = 0, row, column;
        if (!thereIsShip(ship.getPosition())) {
            if (thereIsSpace(ship)) {
                if (!isNearShip(ship)) {
                    row = ship.getPosition().getRow();
                    column = ship.getPosition().getColumn();
                    for (int i = 0; i < ship.getLength() && k + i < length; i++) {
                        if (ship.getDirection() == Direction.HORIZONTAL) {
                            if (i == 0) k = column;
                            board[row][column + i] = SHIP;
                        } else if (ship.getDirection() == Direction.VERTICAL) {
                            if (i == 0) k = row;
                            board[row + i][column] = SHIP;
                        }
                        numShips++;
                    }
                    return true;
                } else throw new BoardException("Ошибка, поблизости находится другой корабль");
            } else throw new BoardException("Ошибка");
        } else throw new BoardException("Здесь уже есть корабль");
    }

    // Добавление выстрела на доску и обновление количества кораблей
    public boolean addHit(Position position) throws BoardException {
        if (thereIsShip(position)) {
            numShips--;
            return set(HIT, position);
        } else if (thereIsWater(position)) return set(MISS, position);
        else throw new BoardException("Ты уже сюда стрелял");
    }

    // Выбор случайной позиции из списка позиций
    public Position randPositionFromList(ArrayList<Position> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    // Получение доски со скрытыми кораблями (для вывода оппоненту)
    public Board getBoardHideShips() throws PositionException {
        char[][] matrix = new char[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (!thereIsShip(new Position(i, j))) {
                    matrix[i][j] = at(new Position(i, j));
                } else matrix[i][j] = WATER;
            }
        }
        return new Board(matrix);
    }

    // Сброс состояния доски
    public void reset() {
        numShips = 0;
        board = initBoard();
    }
}

