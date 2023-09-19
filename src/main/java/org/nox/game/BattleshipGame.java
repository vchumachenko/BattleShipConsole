package org.nox.game;


import org.nox.board.Position;
import org.nox.board.exceptions.BoardException;
import org.nox.board.exceptions.PositionException;
import org.nox.player.Player;

public class BattleshipGame {
    // Игрок 1
    private final Player pOne;

    // Игрок 2 (может быть компьютером)
    private final Player pTwo;

    // Константа, представляющая имя компьютера
    private final String COMPUTER = "AI";

    // человек против компьютера
    public BattleshipGame(String name){
        pOne = new Player(name);                // Создаем кожаного
        pTwo = new Player(COMPUTER, true);      // Создаем железного
    }

    // комп vs комп
    public BattleshipGame(){
        pOne = new Player(COMPUTER + "1", true);  // Создаем компьютерного игрока 1
        pTwo = new Player(COMPUTER + "2", true);  // Создаем компьютерного игрока 2
    }

    // ход в игре
    private boolean turn(Player attack, Player defend) throws PositionException {
        Position shoot = null;
        boolean isHit, isAddHit;

        // Проверяем, есть ли еще живые корабли у атакующего игрока
        if (attack.hasShipsLive()){
            do {
                try {
                    // Получаем координаты выстрела от атакующего игрока
                    shoot = attack.shoot(defend.getBoard().getBoardHideShips());

                    // Попытка добавить выстрел на доску защищающегося игрока
                    isAddHit = defend.addShoot(shoot);
                } catch (BoardException e) {
                    if (!attack.isAI()) Display.printError("Ошибка, вы уже стреляли в этой позиции!");
                    isAddHit = false;
                }
            } while (!isAddHit);

            // Проверяем, попали ли мы в корабль защищающегося игрока
            isHit = defend.getBoard().thereIsHit(shoot);

            // Регистрируем выстрел атакующего игрока
            if (isHit) attack.registerShoot(shoot);

            // Выводим результат выстрела на экран
            Display.printShot(attack, shoot, isHit);

            // Выводим соседние доски, если хотя бы один из игроков - человек
            if (attack.isAI() && defend.isAI()) Display.printAdjacentBoard(attack, defend);
            else if (!attack.isAI()) Display.printAdjacentBoard(attack, defend);
            else if (!defend.isAI()) Display.printAdjacentBoard(defend, attack);

            // Задержка для человеческих игроков
            if (!attack.isAI() && !defend.isAI()) try { Thread.sleep(1000); } catch (InterruptedException ignored) { }

            return true;  // Возвращаем true, чтобы указать, что ход был выполнен успешно
        } else {
            return false; // Возвращаем false, если у атакующего игрока больше нет живых кораблей
        }
    }

    // Метод для размещения всех кораблей на досках игроков
    private void addAllShips(){
        pOne.addAllShips();  // Размещаем корабли для игрока 1
        pTwo.addAllShips();  // Размещаем корабли для игрока 2
    }

    // Метод для вывода результатов игры
    private void printResultGame(){
        if (pOne.shipsLeft() > pTwo.shipsLeft()) Display.printWinner(pOne);
        else Display.printWinner(pTwo);
    }

    // Метод для запуска игры
    public void run() throws PositionException {
        addAllShips();  // Размещаем все корабли на досках игроков
        while (turn(pOne, pTwo) && turn(pTwo, pOne)) { }  // Проводим ходы пока оба игрока имеют живые корабли
        printResultGame();  // Выводим результат игры
    }
}

