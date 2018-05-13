package ru.track.cypher;

import java.util.Map;
import java.util.Set;

import org.jetbrains.annotations.NotNull;

/**
 * Класс умеет кодировать сообщение используя шифр
 */
public class Encoder {

    /**
     * Метод шифрует символы текста в соответствие с таблицей
     * NOTE: Текст преводится в lower case!
     * <p>
     * Если таблица: {a -> x, b -> y}
     * <p>
     * то текст aB -> xy, AB -> xy, ab -> xy
     *
     * @param cypherTable - таблица подстановки
     * @param text        - исходный текст
     * @return зашифрованный текст
     */
    public String encode(@NotNull Map<Character, Character> cypherTable, @NotNull String text) {
        String temp = text.toLowerCase();
        StringBuilder result = new StringBuilder();
        for (int j = 0; j < temp.length(); j++) {
            if(!cypherTable.containsValue(temp.charAt(j))){
                result.append(temp.charAt(j));
            } else {
                Character desiredObject = new Character(temp.charAt(j));
                Set<Map.Entry<Character, Character>> entrySet = cypherTable.entrySet();
                for (Map.Entry<Character, Character> pair : entrySet) {
                    if (desiredObject.equals(pair.getValue())) {
                        result.append(pair.getKey());
                    }
                }
            }

        }

        return result.toString();
    }
}
