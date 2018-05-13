package ru.track.cypher;

import java.util.*;

import org.jetbrains.annotations.NotNull;
import sun.tools.jconsole.Tab;

public class Decoder {

    // Расстояние между A-Z -> a-z
    public static final int SYMBOL_DIST = 32;

    private Map<Character, Character> cypher;

    /**
     * Конструктор строит гистограммы открытого домена и зашифрованного домена
     * Сортирует буквы в соответствие с их частотой и создает обратный шифр Map<Character, Character>
     *
     * @param domain - текст по кторому строим гистограмму языка
     */
    public Decoder(@NotNull String domain, @NotNull String encryptedDomain) {
        Map<Character, Integer> domainHist = createHist(domain);
        Map<Character, Integer> encryptedDomainHist = createHist(encryptedDomain);

        cypher = new LinkedHashMap<>();
        Iterator<Character> it = domainHist.keySet().iterator();
        Iterator<Character> eit = encryptedDomainHist.keySet().iterator();
        while (eit.hasNext()) {
            cypher.put(eit.next(),it.next());
            }

    }

    public Map<Character, Character> getCypher() {

        return cypher;
    }

    /**
     * Применяет построенный шифр для расшифровки текста
     *
     * @param encoded зашифрованный текст
     * @return расшифровка
     */
    @NotNull
    public String decode(@NotNull String encoded) {
        StringBuilder result = new StringBuilder();
        for(int i=0;i<encoded.length();i++){
            if(Character.isLetter(encoded.charAt(i))){
            result.append(cypher.get(encoded.charAt(i)));
            } else result.append(encoded.charAt(i));
        }

        return result.toString();
    }

    /**
     * Считывает входной текст посимвольно, буквы сохраняет в мапу.
     * Большие буквы приводит к маленьким
     *
     *
     * @param text - входной текст
     * @return - мапа с частотой вхождения каждой буквы (Ключ - буква в нижнем регистре)
     * Мапа отсортирована по частоте. При итерировании на первой позиции наиболее частая буква
     */
    @NotNull
    Map<Character, Integer> createHist(@NotNull String text) {
        Map Table = new HashMap< Character, Integer>();
        String temp = text.toLowerCase();
        for(int i=0;i<temp.length();i++)
        {
            if(Character.isLetter(temp.charAt(i))){
                if (Table.containsKey(temp.charAt(i))){
                    Integer value = (Integer) Table.get(temp.charAt(i));
                    Table.put(temp.charAt(i),++value);
                } else {
                    Table.put(temp.charAt(i),1);
                }
            }
        }
        List<Map.Entry<Character, Integer>> list = new LinkedList<Map.Entry<Character, Integer>>(Table.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Character, Integer>>() {
            public int compare(Map.Entry<Character, Integer> o1,
                               Map.Entry<Character, Integer> o2) {
                return (o2.getValue().compareTo(o1.getValue()));
            }
        });
        Map<Character, Integer> sortedMap = new LinkedHashMap<Character, Integer>();
        for (Map.Entry<Character, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        System.out.print(sortedMap);
        return sortedMap;

    }

}
