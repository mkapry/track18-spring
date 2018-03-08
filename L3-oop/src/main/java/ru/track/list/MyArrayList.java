package ru.track.list;

import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 *
 * Должен иметь 2 конструктора
 * - без аргументов - создает внутренний массив дефолтного размера на ваш выбор
 * - с аргументом - начальный размер массива
 */
public class MyArrayList extends List {
    private final int INIT_SIZE = 16;
    private array;
    private int pointer = 0;
    private static int size;

    public MyArrayList() {
        Object[] array = new Object[INIT_SIZE];
        size=0;

    }

    public MyArrayList(int capacity) {
        Object[] array = new Object[capacity];
        size=0;
    }

    @Override
    void add(int item) {
        if (pointer==INIT_SIZE) {
            Object[] arraynew = new Object[INIT_SIZE+ 1];
            System.arraycopy(array,0,arraynew,0)

        }


            array[pointer++] = item;
        size++;

    }

    @Override
    int remove(int idx) throws NoSuchElementException {
        if(idx>size) throw NoSuchElementException;
        for (int i = idx; i<pointer; i++)
            array[i] = array[i+1];
        array[pointer] = null;
        pointer--;
        size--;
        return 0;
    }

    @Override
    int get(int idx) throws NoSuchElementException {
        if(idx>size) throw NoSuchElementException;
        return array[idx];
    }

    @Override
    int size() {
        return pointer;
    }
}
