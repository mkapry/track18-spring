package ru.track.cypher;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

/**
 * Вспомогательные методы шифрования/дешифрования
 */
public class CypherUtil {

    public static final String SYMBOLS = "abcdefghijklmnopqrstuvwxyz";

    /**
     * Генерирует таблицу подстановки - то есть каждой буква алфавита ставится в соответствие другая буква
     * Не должно быть пересечений (a -> x, b -> x). Маппинг уникальный
     *
     * @return таблицу подстановки шифра
     */
    @NotNull
    public static Map<Character, Character> generateCypher() {
        Map cypherTable = new HashMap< Character, Character>();
        for (int i =0;i<SYMBOLS.length()/2;i++)
        {
            Character h = new Character(SYMBOLS.charAt(i));
            cypherTable.put(SYMBOLS.charAt(SYMBOLS.length()-(i+1)), h);
        }
        for (int i= SYMBOLS.length()-1;i>=SYMBOLS.length()/2;i--)
        {
            Character h = new Character(SYMBOLS.charAt(i));
            cypherTable.put(SYMBOLS.charAt(SYMBOLS.length()-(i+1)), h);
        }
        return cypherTable;
    }

}
