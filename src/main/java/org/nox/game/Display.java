package org.nox.game;


import org.nox.board.Board;
import org.nox.board.Position;
import org.nox.board.exceptions.PositionException;
import org.nox.game.utils.DisplayColors;
import org.nox.player.Input;
import org.nox.player.Player;
import org.nox.ships.Ship;

import java.util.Scanner;

public class Display {

    // Метод для вывода заголовка игры
    public static void printTitle() {
        System.out.println("" +
                "\n" +
                "────────────────────────────────██────────────────██────\n" +
                "█───█─████─████─████─█──█─████─█──█────████─████─█──█───\n" +
                "██─██─█──█─█──█─█──█─█─█──█──█─█──█────█────█──█─█──█───\n" +
                "█─█─█─█──█─████─█────██───█──█─█─██────████─█──█─█─██───\n" +
                "█───█─█──█─█────█──█─█─█──█──█─██─█────█──█─█──█─██─█───\n" +
                "█───█─████─█────████─█──█─████─█──█────████─████─█──█───");
    }

    // Метод для вывода меню и получения выбора пользователя
    public static int printMenu() {
        printTitle();
        System.out.println("\n(1) - Начать игру");
        System.out.println("(2) - Имитация игры");
        System.out.println("(3) - Правила");
        System.out.println("(0) - Выход\n");
        return Input.readOption(new Scanner(System.in), "Выберите: ");
    }

    // Метод для вывода правил игры на экран
    public static void printRules() {
        System.out.println(DisplayColors.ANSI_YELLOW + "\nКак выиграть:" + DisplayColors.ANSI_RESET);
        System.out.println(DisplayColors.ANSI_WHITE +
                "- Каждый игрок имеет поле боя, представленное сеткой 10x10 (по умолчанию), на которой размещаются " + it.battleship.ships.utils.ShipType.sizeAllShips() + " кораблей, скрытых от противника.\n" +
                "- Цель игры - потопить все корабли противника! Корабль считается потопленным, если он был подбит в каждой из своих клеток.\n" +
                "- Другими словами, корабль " + it.battleship.ships.utils.ShipType.toRussianNameShip(it.battleship.ships.utils.ShipType.values()[0]) + ", занимающий " + it.battleship.ships.utils.ShipType.values()[0].getShipLength() + " клеток, считается потопленным после одного попадания.\n" +
                "- Все " + it.battleship.ships.utils.ShipType.sizeAllShips() + " кораблей занимают в сумме " + it.battleship.ships.utils.ShipType.lengthAllShips() + " клеток, поэтому первый игрок, зарегистрировавший 20 попаданий, выигрывает!" +
                DisplayColors.ANSI_RESET);

        System.out.println(DisplayColors.ANSI_YELLOW + "\nГеймплей:" + DisplayColors.ANSI_RESET);
        System.out.println(DisplayColors.ANSI_WHITE +
                "- Для начала игры следуйте инструкциям по настройке ваших " + it.battleship.ships.utils.ShipType.sizeAllShips() + " кораблей в любом месте, которое вам нравится (не разрешается устанавливать их по диагонали или смежно с другими кораблями).\n" +
                "- Для размещения корабля необходимо указать начальные координаты (A1-J10 для стандартной сетки 10x10) и направление (вертикальное или горизонтальное).\n" +
                "- Например: A1 или B7. Корабли не могут перекрываться или смежно располагаться (соприкасаться друг с другом), и вы должны оставаться в пределах границ поля.\n" +
                "- Как только оба игрока настроят свои корабли, начинается битва!\n" +
                "- Вы можете выпускать торпеды по кораблям противника, угадывая координаты.\n" +
                "- Строки обозначаются буквами A-J, а столбцы - цифрами 1-10 (для стандартной сетки 10x10).\n" +
                "- Допустимые координаты включают в себя букву, за которой следует цифра, например A1, B7, J10 и так далее.\n" +
                "- Вам будет сообщено, попали ли вы в корабль или промахнулись.\n" +
                "- Чтобы выиграть, потопите все 8 кораблей компьютера!" +
                DisplayColors.ANSI_RESET);
        System.out.println(DisplayColors.ANSI_YELLOW + "\nЛегенда:" + DisplayColors.ANSI_RESET);
        for (it.battleship.ships.utils.ShipType type : it.battleship.ships.utils.ShipType.values()) {
            System.out.println(DisplayColors.ANSI_WHITE +
                    "- (" + DisplayColors.ANSI_YELLOW + Board.SHIP + DisplayColors.ANSI_WHITE + "x" + type.getShipLength() + ")\t: " + it.battleship.ships.utils.ShipType.toRussianNameShip(type) +
                    DisplayColors.ANSI_RESET);
        }
        System.out.println(
                "- ( " + DisplayColors.ANSI_BLUE + Board.WATER + DisplayColors.ANSI_WHITE + " )\t: Вода\n" +
                        "- (" + DisplayColors.ANSI_YELLOW + Board.SHIP + DisplayColors.ANSI_WHITE + ")\t: Корабль\n" +
                        "- (" + DisplayColors.ANSI_RED + Board.HIT + DisplayColors.ANSI_WHITE + ")\t: Попадание в корабль\n" +
                        "- (" + DisplayColors.ANSI_WHITE + Board.MISS + DisplayColors.ANSI_WHITE + " )\t: Промах\n");

        System.out.print("\nНажмите Enter, чтобы продолжить...");
        new Scanner(System.in).nextLine();
    }
    // Метод для вывода информации
    public static void printCredits() {
        System.out.println("\nСпасибо за игру!");
    }


    // Метод для вывода текста о завершении игры и объявления победителя
    public static void printWinner(Player player) {
        System.out.println(DisplayColors.ANSI_BLUE + "\n✔ " + player.getName() + " победил!" + DisplayColors.ANSI_RESET + "\n");
        System.out.print("\nНажмите Enter, чтобы продолжить...");
        new Scanner(System.in).nextLine();
    }

    // Метод для вывода сообщения об ошибке
    public static void printError(String message) {
        System.out.println(DisplayColors.ANSI_RED + message + DisplayColors.ANSI_RESET);
    }

    // Метод для вывода информации о выстреле (попадание или промах)
    public static void printShot(Player player, Position position, boolean isHit) {
        System.out.println("- " + player.getName() + " стреляет в " + position.toStringEncode(position) + ": " +
                (isHit ? DisplayColors.ANSI_BLUE + "Попал!" + DisplayColors.ANSI_RESET :
                        DisplayColors.ANSI_RED + "Промах!" + DisplayColors.ANSI_RESET));
    }

    // Метод для вывода текущего состояния корабля
    public static void printCurrentShip(Ship ship, int numShipLeft) {
        System.out.println("☛ " + ship.getName() + " (" +
                DisplayColors.ANSI_YELLOW + ship.toGraphicLength() + DisplayColors.ANSI_RESET +
                ") x" + numShipLeft);
    }

    // Метод для вывода соседних игровых полей двух игроков
    public static void printAdjacentBoard(Player pOne, Player pTwo) throws PositionException {
        System.out.println(toStringAdjacentBoard(pOne, pTwo));
    }

    // Метод для формирования строки с соседними игровыми полями двух игроков
    public static String toStringAdjacentBoard(Player pOne, Player pTwo) throws PositionException {
        Board firstBoard = pOne.getBoard();
        Board secondBoard = pTwo.getBoard().getBoardHideShips();
        String numbers = "⒈⒉⒊⒋⒌⒍⒎⒏⒐⒑";
        String letters = "ͣᵇͨͩͤᶠᶢͪͥʲ";
        String s = "\n――――――――――――――――――――――――――――――――――\n";
        s += "\n     ";

        for (int i = 0; i < firstBoard.getLength(); i++) s += " " + numbers.charAt(i) + "    ";
        s += "          ";
        for (int i = 0; i < secondBoard.getLength(); i++) s += " " + numbers.charAt(i) + "    ";


        s += "\n";
        for (int i = 0; i < firstBoard.getLength(); i++) {
            s += DisplayColors.ANSI_WHITE;
            if (i == 5) s += " " + letters.charAt(i) + "    "; //f
            else if (i == 6) s += letters.charAt(i) + "    "; //g
            else s += letters.charAt(i) + "  ";

            for (int j = 0; j < firstBoard.getLength(); j++) {
                if (firstBoard.getBoard()[i][j] == Board.WATER) s += DisplayColors.ANSI_BLUE + " " + Board.WATER + " " + " " + DisplayColors.ANSI_RESET;
                else if (firstBoard.getBoard()[i][j] == Board.HIT) s += DisplayColors.ANSI_RED + Board.HIT + " " + DisplayColors.ANSI_RESET;
                else if (firstBoard.getBoard()[i][j] == Board.MISS) s += Board.MISS + " " + DisplayColors.ANSI_RESET;
                else s += DisplayColors.ANSI_YELLOW + firstBoard.getBoard()[i][j] + " " + DisplayColors.ANSI_RESET;
            }

            s += "   ";

            s += DisplayColors.ANSI_WHITE;
            if (i == 5) s += " " + letters.charAt(i) + "    "; //f
            else if (i == 6) s += letters.charAt(i) + "    "; //g
            else s += letters.charAt(i) + "  ";

            for (int j = 0; j < secondBoard.getLength(); j++) {
                if (secondBoard.getBoard()[i][j] == Board.WATER) s += DisplayColors.ANSI_BLUE + " " + Board.WATER + " " + " " + DisplayColors.ANSI_RESET;
                else if (secondBoard.getBoard()[i][j] == Board.HIT) s += DisplayColors.ANSI_RED + Board.HIT + " " + DisplayColors.ANSI_RESET;
                else if (secondBoard.getBoard()[i][j] == Board.MISS) s += Board.MISS + " " + DisplayColors.ANSI_RESET;
                else s += DisplayColors.ANSI_YELLOW + secondBoard.getBoard()[i][j] + " " + DisplayColors.ANSI_RESET;
            }

            s += "\n";
        }
        s += "\n――――――――――――――――――――――――――――――――――\n";
        return s;
    }

    // Метод для вывода игровой доски
    public static void printBoard(Board board) {
        System.out.println(toStringBoard(board));
    }

    // Метод для формирования строки с игровой доской
    public static String toStringBoard(Board board) {
        String numbers = "⒈⒉⒊⒋⒌⒍⒎⒏⒐⒑";
        String letters = "ͣᵇͨͩͤᶠᶢͪͥʲ";
        String s = "\n     ";
        for (int i = 0; i < board.getLength(); i++) s += " " + numbers.charAt(i) + "    ";
        s += "\n";
        for (int i = 0; i < board.getLength(); i++) {
            s += DisplayColors.ANSI_WHITE;
            if (i == 5) s += " " + letters.charAt(i) + "    "; //f
            else if (i == 6) s += letters.charAt(i) + "    "; //g
            else s += letters.charAt(i) + "  ";

            for (int j = 0; j < board.getLength(); j++) {
                if (board.getBoard()[i][j] == Board.WATER) s += DisplayColors.ANSI_BLUE + " " + Board.WATER + " " + " " + DisplayColors.ANSI_RESET;
                else if (board.getBoard()[i][j] == Board.HIT) s += DisplayColors.ANSI_RED + Board.HIT + " " + DisplayColors.ANSI_RESET;
                else if (board.getBoard()[i][j] == Board.MISS) s += DisplayColors.ANSI_WHITE + Board.MISS + " " + DisplayColors.ANSI_RESET;
                else s += DisplayColors.ANSI_YELLOW + board.getBoard()[i][j] + " " + DisplayColors.ANSI_RESET;
            }
            s += "\n";
        }
        return s;
    }
}
