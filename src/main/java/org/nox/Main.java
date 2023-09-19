package org.nox;


import org.nox.board.exceptions.PositionException;
import org.nox.game.BattleshipGame;
import org.nox.game.Display;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws PositionException {
        Scanner sc = new Scanner(System.in);
        BattleshipGame game;
        String name = "";
        int opt;
        boolean hasName = false;

        try {
            do {
                opt = Display.printMenu(); // Вывод меню и получение выбора пользователя

                switch (opt) {
                    case 1 -> {
                        if (!hasName) {
                            System.out.print("\nВведите ваше имя: ");
                            name = sc.next();
                            hasName = true;
                        }
                        game = new BattleshipGame(name); // Создание игры с именем игрока
                        game.run(); // Запуск игры.
                    }
                    case 2 -> {
                        game = new BattleshipGame(); // Создание игры без имени (имитация игры против компьютера)
                        game.run(); // Запуск игры.
                    }
                    case 3 -> {
                        Display.printRules(); // Вывод правил игры.
                    }
                }
            } while (opt != 0); // Повторение меню до выбора выхода (0).

        } catch (InputMismatchException ignored) { } // Обработка ошибок ввода.

        Display.printCredits(); // Вывод информации о завершении игры.
    }
}
