package org.nox.player;


import org.nox.board.Board;
import org.nox.board.Position;
import org.nox.board.exceptions.BoardException;
import org.nox.board.exceptions.PositionException;
import org.nox.game.Display;
import org.nox.player.Input;
import org.nox.ships.Ship;
import org.nox.ships.utils.Direction;
import org.nox.ships.utils.ShipType;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Player {
    // Имя игрока
    private String name;

    // Игровая доска игрока
    private final Board board = new Board(10);

    // Список координат, по которым были сделаны выстрелы
    private final ArrayList<Position> shoots = new ArrayList<>();

    // Список координат, которые будут атакованы следующими (для искусственного интеллекта)
    private ArrayList<Position> nextTargets = new ArrayList<>();

    // Флаг, указывающий, является ли игрок искусственным интеллектом
    private boolean isAI;

    // Конструктор по умолчанию. Создает игрока с случайным именем и устанавливает флаг isAI в true.
    public Player(){
        name = randName();
        isAI = true;
    }

    // Конструктор, принимающий имя игрока. Устанавливает имя и флаг isAI в false.
    public Player(String name){
        this.name = name;
        isAI = false;
    }

    // Конструктор, принимающий имя и флаг isAI.
    public Player(String name, boolean isAI){
        this.name = name;
        this.isAI = isAI;
    }

    // Конструктор, принимающий только флаг isAI и устанавливающий случайное имя.
    public Player(boolean isAI){
        this.name = randName();
        this.isAI = isAI;
    }

    // Инициализация списка кораблей.
    private ArrayList<Ship> initShips(){
        ArrayList<Ship> list = new ArrayList<>();
        for (ShipType type: ShipType.values()){
            for (int i = 0; i < type.getNumShips(); i++){
                list.add(new Ship(ShipType.toRussianNameShip(type), type.getShipLength()));
            }
        }
        return list;
    }

    // Генерация случайного имени для игрока.
    private String randName(){
        Random rand = new Random();
        String letters = "abcdefghiljkmnopqrstuvwxyz";
        String name = "";
        int maxLung = 10, minLung = 3;
        int l = rand.nextInt(maxLung - minLung) + minLung;
        for (int i=0; i<l; i++){
            name += letters.charAt(rand.nextInt(letters.length()));
        }
        return name;
    }

    // Установка имени игрока.
    public void setName(String name) {
        this.name = name;
    }

    // Получение имени игрока.
    public String getName() {
        return name;
    }

    // Получение игровой доски игрока.
    public Board getBoard() {
        return board;
    }

    // Получение списка координат, по которым были сделаны выстрелы.
    public ArrayList<Position> getShoots() {
        return shoots;
    }

    // Установка флага isAI.
    public void setAI(boolean AI) {
        isAI = AI;
    }

    // Проверка, является ли игрок искусственным интеллектом.
    public boolean isAI(){
        return isAI;
    }

    // Добавление всех кораблей на игровую доску игрока.
    public void addAllShips(){
        if (!isAI) {
            boolean isAdded;
            Position position;
            Direction direction;
            String messageInputPosition = "- Введите координаты (Например A1): ";
            String messageInputDirection = "- Введите ориентацию(h-горизонт./v-верт.) (h/v): ";
            Scanner sc = new Scanner(System.in);
            ArrayList<Ship> list = initShips();
            for (int i = 0; i < list.size(); i++) {
                Ship ship = list.get(i);
                do {
                    Display.printBoard(board);
                    Display.printCurrentShip(ship, countShip(list, ship.getLength()));

                    position = Input.readPosition(sc, board, messageInputPosition);
                    direction = Input.readDirection(sc, messageInputDirection);
                    ship.setPosition(position);
                    ship.setDirection(direction);

                    try {
                        isAdded = board.addShip(ship);
                    } catch (BoardException | PositionException e) {
                        Display.printError(e.toString());
                        isAdded = false;
                    }
                } while (!isAdded);
                list.remove(i);
                i--;
            }
            Display.printBoard(board);
        }else randAddAllShips();
    }

    // Добавление всех кораблей на игровую доску для искусственного интеллекта.
    private void randAddAllShips(){
        Random random = new Random();
        ArrayList<Ship> list = initShips();

        boolean isAdded;
        Position position;
        Direction direction;
        int deadlock = 0, limit = 1000;

        for (int i = 0; i < list.size(); i++){
            Ship ship = list.get(i);
            deadlock = 0;
            do {
                try {
                    position = new Position(random.nextInt(board.getLength()), random.nextInt(board.getLength()));
                    direction = random.nextBoolean() ? Direction.VERTICAL : Direction.HORIZONTAL;
                    ship.setPosition(position);
                    ship.setDirection(direction);
                    isAdded = board.addShip(ship);
                } catch (BoardException | PositionException e){ isAdded = false; }
                if (!isAdded) deadlock++;
                if (deadlock > limit) {
                    reset();
                    i = -1;
                    break;
                }
            } while (!isAdded);
        }
    }

    // Проверка наличия живых кораблей.
    public boolean hasShipsLive(){
        return board.getNumShips() > 0;
    }

    // Подсчет количества кораблей заданной длины.
    private int countShip(ArrayList<Ship> ships, int length){
        int count = 0;
        for (Ship ship: ships){
            if (ship.getLength() == length) count++;
        }
        return count;
    }

    // Получение количества оставшихся кораблей.
    public int shipsLeft(){
        return board.getNumShips();
    }

    // Генерация случайной позиции на игровой доске.
    private Position randPosition() throws PositionException {
        Random random = new Random();
        int x = random.nextInt(board.getLength());
        int y = random.nextInt(board.getLength());
        return new Position(x, y);
    }

    // Добавление выстрела на игровую доску.
    public boolean addShoot(Position pos) throws BoardException {
        return board.addHit(pos);
    }

    // Выстрел искусственного интеллекта (AI).
    public Position shootAI(Board boardEnemy) throws PositionException {
        Position lastPos, nextPos;
        if (shoots.isEmpty()) return randPosition();
        else {
            lastPos = getLastShoot(); // Последний выстрел
            nextTargets.addAll(boardEnemy.getPossibleTarget(lastPos));
            if (nextTargets.isEmpty()) return randPosition();
            nextPos = nextTargets.get(0);
            nextTargets.remove(0);
            return nextPos;
        }
    }

    // Выстрел игрока.
    public Position shoot(Board boardEnemy) throws PositionException {
        if (isAI) return shootAI(boardEnemy);
        else {
            Scanner sc = new Scanner(System.in);
            return Input.readPosition(sc, board,  "- " + name + ", куда ты хочешь ударить? ");
        }
    }

    // Регистрация выстрела на доске игрока.
    public void registerShoot(Position position){
        shoots.add(position);
    }

    // Получение последнего выстрела.
    public Position getLastShoot(){
        if (shoots.isEmpty()) return null;
        return shoots.get(shoots.size() - 1);
    }

    // Сброс игровой доски игрока.
    private void reset(){
        board.reset();
    }
}
