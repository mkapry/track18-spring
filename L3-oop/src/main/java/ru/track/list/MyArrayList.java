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
    private Object[] array= new Object[INIT_SIZE];
    private int pointer = 0;

    public MyArrayList(int capacity) {
        Object[] array = new Object[capacity];
    }

    @Override
    void add(int item) {
        if (pointer==INIT_SIZE) {
            Object[] arraynew = new Object[INIT_SIZE+ 1];
            System.arraycopy(array,0,arraynew,0,pointer);

        }

        array[pointer++] = item;

    }

    @Override
    int remove(int idx) throws NoSuchElementException {
        if(idx>size()) throw new NoSuchElementException();
        for (int i = idx; i<pointer; i++)
            array[i] = array[i+1];
        array[pointer] = null;
        pointer--;
        return 0;
    }

    @Override
    int get(int idx) throws NoSuchElementException {
        if(idx>size()) throw new NoSuchElementException();
        int i = (int) array[idx];
        return i;
    }

    @Override
    int size() {
        return pointer;
    }
}
