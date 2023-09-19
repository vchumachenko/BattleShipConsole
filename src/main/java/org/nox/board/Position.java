package org.nox.board;

import org.nox.board.Board;
import org.nox.board.exceptions.PositionException;

public class Position {
    // Переменные, представляющие координаты позиции
    private final int row;
    private final int column;

    // Конструктор, принимающий целочисленные координаты
    public Position(int row, int column) throws PositionException {
        if (row < 0 || column < 0)
            throw new PositionException("Допустимое значение больше 1");
        this.row = row;
        this.column = column;
    }

    // Конструктор, принимающий символьную координату и целочисленную координату
    public Position(char row, int column) throws PositionException {
        if (row < 'a' || column < 0)
            throw new PositionException("Допустимые значения больше a1");
        this.row = decode(row);
        this.column = column;
    }

    // Получение номера строки
    public int getRow() {
        return row;
    }

    // Получение номера столбца
    public int getColumn() {
        return column;
    }

    // Декодирование символьной координаты в число (например, 'a' -> 0, 'b' -> 1 и так далее)
    public static int decode(char row) {
        return row - 'a';
    }

    // Кодирование числовой координаты в символ (например, 0 -> 'a', 1 -> 'b' и так далее)
    public static char encode(int row) {
        return (char)('a' + row);
    }

    // Преобразование позиции в строку с символьными координатами (например, "(a,1)")
    public String toStringEncode(Position position) {
        return "(" + (char)('a' + position.getRow()) + "," + (position.getColumn() + 1) + ")";
    }

    // Проверка, находится ли позиция в пределах допустимых значений для данной доски
    public static boolean isInRange(char row, int column, Board board) throws PositionException {
        int decodeRow = decode(row);
        if (decodeRow >= board.getLength() || column > board.getLength() || decodeRow < 0 || column < 0)
            throw new PositionException("Ошибка, допустимые значения между a1 и " + Position.encode(board.getLength() - 1) + board.getLength());
        else return true;
    }

    // Переопределение метода toString для возврата строки с числовыми координатами (например, "(0,1)")
    @Override
    public String toString (){
        return "(" + row + "," + column + ")";
    }
}
