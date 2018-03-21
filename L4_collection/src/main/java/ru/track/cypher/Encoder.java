package ru.track.cypher;

import java.util.Map;

import org.jetbrains.annotations.NotNull;

/**
 * Класс умеет кодировать сообщение используя шифр
 */
public class Encoder {

    /**
     * Метод шифрует символы текста в соответствие с таблицей
     * NOTE: Текст преводится в lower case!
     *
     * Если таблица: {a -> x, b -> y}
     *
     * то текст aB -> xy, AB -> xy, ab -> xy
     *
     * @param cypherTable - таблица подстановки
     * @param text - исходный текст
     * @return зашифрованный текст
     */
    public String encode(@NotNull Map<Character, Character> cypherTable, @NotNull String text) {
        String temp=text.toLowerCase();
        StringBuilder result=new StringBuilder();
        for (int j=0;j<temp.length(); j++)
        {
            if (cypherTable.get(j)!=null)
            {
                result.append(cypherTable.get(temp.charAt(j)));
            } else
            result.append(temp.charAt(j));
        }
        return result.toString();
    }
}
