package org.nox.ships.utils;

public enum ShipType {
    BOAT(4, 1),           // Тип корабля "Торпедный катер" с длиной 1 и 4 кораблями этого типа.
    DESTROYER(3, 2),      // Тип корабля "Эсминец" с длиной 2 и 3 кораблями этого типа.
    CRUISER(2, 3),        // Тип корабля "Крейсер" с длиной 3 и 2 кораблями этого типа.
    BATTLESHIP(1, 4);     // Тип корабля "Линкор" с длиной 4 и 1 кораблем этого типа.

    private final int numShips;    // Количество кораблей данного типа.
    private final int shipLength;  // Длина корабля данного типа.

    // Конструктор, устанавливающий значения количества кораблей и длины корабля для каждого типа.
    ShipType (int numShips, int shipLength) {
        this.numShips = numShips;
        this.shipLength = shipLength;
    }

    // Геттер для получения длины корабля данного типа.
    public int getShipLength() {
        return shipLength;
    }

    // Геттер для получения количества кораблей данного типа.
    public int getNumShips() {
        return numShips;
    }

    // Метод для вычисления общей длины всех кораблей всех типов.
    public static int lengthAllShips(){
        int sum = 0;
        for (ShipType type: ShipType.values()) sum += type.shipLength * type.numShips;
        return sum;
    }

    // Метод для вычисления общего количества кораблей всех типов.
    public static int sizeAllShips(){
        int sum = 0;
        for (ShipType type: ShipType.values()) sum += type.numShips;
        return sum;
    }

    // Метод для получения русскоязычного названия корабля данного типа.
    public static String toRussianNameShip(ShipType type){
        return switch (type) {
            case BATTLESHIP -> "Линкор";          // Если тип "Линкор", возвращаем "Линкор".
            case CRUISER -> "Крейсер";            // Если тип "Крейсер", возвращаем "Крейсер".
            case DESTROYER -> "Эсминец";         // Если тип "Эсминец", возвращаем "Эсминец".
            case BOAT -> "Торпедный катер";      // Если тип "Торпедный катер", возвращаем "Торпедный катер".
        };
    }
}

