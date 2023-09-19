package it.battleship.ships.utils;

import it.battleship.ships.utils.exceptions.DirectionException;

public enum Direction {
    HORIZONTAL, // Горизонтальное направление (горизонтально расположенные корабли).
    VERTICAL;   // Вертикальное направление (вертикально расположенные корабли).

    // Метод для декодирования символа в направление.
    public static Direction decode(char c) throws DirectionException {
        if (c == 'h' || c == 'H') return HORIZONTAL; // Если символ 'h' или 'H', возвращаем горизонтальное направление.
        else if (c == 'v' || c == 'V') return VERTICAL; // Если символ 'v' или 'V', возвращаем вертикальное направление.
        else throw new DirectionException("Символ '" + c + "' не может быть преобразован в направление");
        // Если символ не соответствует ни одному из допустимых, выбрасываем исключение.
    }

    // Метод для декодирования строки в направление.
    public static Direction decode(String str) throws DirectionException {
        if (str.toLowerCase().equals("horizontal")) return HORIZONTAL; // Если строка "horizontal" (независимо от регистра), возвращаем горизонтальное направление.
        else if (str.toLowerCase().equals("vertical")) return VERTICAL; // Если строка "vertical" (независимо от регистра), возвращаем вертикальное направление.
        else throw new DirectionException("Строка '" + str + "' не может быть преобразована в направление");
        // Если строка не соответствует ни одному из допустимых значений, выбрасываем исключение.
    }
}
