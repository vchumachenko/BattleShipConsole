package org.nox.player;


import org.nox.board.Board;
import org.nox.board.Position;
import org.nox.board.exceptions.PositionException;
import org.nox.game.Display;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {

    // Метод для считывания позиции от игрока, учитывая допустимые значения на игровой доске.
    public static Position readPosition(Scanner sc, Board board, String message) {
        try {
            System.out.print(message); // Выводим пользовательское сообщение для ввода позиции.
            String s = sc.nextLine().toLowerCase(); // Считываем строку в нижнем регистре.
            char row = s.charAt(0); // Первый символ строки представляет строку на доске.
            int column = Integer.parseInt(s.substring(1)); // Остальная часть строки представляет столбец на доске.
            Position.isInRange(row, column, board); // Проверяем, что введенные координаты в пределах допустимых значений.
            return new Position(row, column - 1); // Возвращаем объект позиции (учитываем, что столбцы начинаются с 0).
        } catch (PositionException | NumberFormatException | StringIndexOutOfBoundsException e) {
            // Если возникает исключение, выводим сообщение об ошибке и просим ввести позицию заново.
            Display.printError("Ошибка, допустимые значения между a1 и " + Position.encode(board.getLength() - 1) + board.getLength());
            return readPosition(sc, board, message); // Рекурсивный вызов метода для повторного ввода.
        }
    }

    // Метод для считывания направления (горизонтальное или вертикальное) от игрока.
    public static it.battleship.ships.utils.Direction readDirection(Scanner sc, String message) {
        try {
            System.out.print(message); // Выводим пользовательское сообщение для ввода направления.
            String s = sc.nextLine();
            return it.battleship.ships.utils.Direction.decode(s.charAt(0)); // Декодируем введенное направление.
        } catch (it.battleship.ships.utils.exceptions.DirectionException | StringIndexOutOfBoundsException e) {
            // Если возникает исключение, выводим сообщение об ошибке и просим ввести направление заново.
            Display.printError("Ошибка, допустимые значения для направления 'г' или 'в'");
            return readDirection(sc, message); // Рекурсивный вызов метода для повторного ввода.
        }
    }

    // Метод для считывания опции (целого числа) от игрока.
    public static int readOption(Scanner sc, String message) {
        try {
            System.out.print(message); // Выводим пользовательское сообщение для ввода опции.
            return Integer.parseInt(sc.next()); // Считываем и возвращаем введенное целое число.
        } catch (NumberFormatException | InputMismatchException e) {
            // Если возникает исключение, выводим сообщение об ошибке и просим ввести опцию заново.
            Display.printError("Ошибка, введите число для продолжения");
            return readOption(sc, message); // Рекурсивный вызов метода для повторного ввода.
        }
    }
}
