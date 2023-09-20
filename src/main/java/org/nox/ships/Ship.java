package org.nox.ships;

import org.nox.board.Board;
import org.nox.board.Position;
import org.nox.ships.utils.Direction;

public class Ship {
    private final String name;      // Название корабля.
    private final int length;       // Длина корабля в клетках.
    private Position position;       // Позиция корабля на игровом поле.
    private Direction direction;     // Направление корабля (горизонтальное или вертикальное).

    // Конструктор для создания корабля с заданным названием и длиной.
    public Ship(String name, int length){
        this.name = name;
        this.length = length;
        this.position = null;        // Изначально позиция не установлена.
        this.direction = null;       // Изначально направление не установлено.
    }

    // Конструктор для создания корабля с заданным названием, длиной, позицией и направлением.
    public Ship(String name, int length, Position position, Direction direction){
        this.name = name;
        this.length = length;
        this.position = position;
        this.direction = direction;
    }

    // Геттер для получения названия корабля.
    public String getName() {
        return name;
    }

    // Геттер для получения длины корабля.
    public int getLength() {
        return length;
    }

    // Геттер для получения позиции корабля.
    public Position getPosition() {
        return position;
    }

    // Сеттер для установки позиции корабля.
    public void setPosition(Position position) {
        this.position = position;
    }

    // Геттер для получения направления корабля.
    public Direction getDirection() {
        return direction;
    }

    // Сеттер для установки направления корабля.
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    // Метод для получения строкового представления корабля с учетом его длины (графическое представление).
    public String toGraphicLength() {
        return ("" + Board.SHIP).repeat(length);
    }

    // Переопределение метода toString() для получения строкового представления корабля.
    public String toString(){
        return name + ";" + length; /*+ ";" + (position.toString()) + ";" + direction;*/
    }
}
