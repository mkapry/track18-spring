package ru.track.list;

import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 * Односвязный список
 */
public class MyLinkedList extends List implements Stack ,Queue {

    /**
     * private - используется для сокрытия этого класса от других.
     * Класс доступен только изнутри того, где он объявлен
     * <p>
     * static - позволяет использовать Node без создания экземпляра внешнего класса
     */
    private Node head;
    private int length;
    private Node tail;
    @Override
    public void push(int value) {
        Node elem=new Node(value);
        if (head==null)
            {
                head=elem;
                tail=elem;
            }
        elem.prev=head.next;
        head=elem;

    }

    @Override
    public int pop() {
        Node elem=head;
        try {
            head=head.prev;

        } catch (NullPointerException e){
            return elem.val;
        }
        return elem.val;
    }

    @Override
    public void enqueue(int value) {
        Node elem=new Node(value);
        if (head==null)
        {
            head=elem;
            tail=elem;
        } else {
        elem.prev=head.next;
        head=elem;
        }

    }

    @Override
    public int dequeu() {
        Node elem=tail;
        try {
            tail=tail.next;
        } catch (NullPointerException e){
            return elem.val;
        }
        return elem.val;
    }

    private static class Node {
        Node prev;
        Node next;
        int val;
        int ind;

        Node(int val) {
            this.prev = null;
            this.next = null;
            this.val = val;
            ind=1;
        }
    }

    @Override
    void add(int item) {
        Node node=new Node(item);
        if (head==null){
            head=node;
            //node.ind+=length;
        } else {
            node.prev=head.next;
            head=node;
            node.ind+=length;

        }
        length++;

    }

    @Override
    int remove(int idx) throws NoSuchElementException {
        if (idx==0||idx>length) throw new NoSuchElementException();
            Node search=head;
            while (search.ind!=idx) {
                search.ind--;
                search = search.prev;
            }
            if (idx==1) {
                search.next.prev=null;
                length=head.ind;
                return search.val;
            }else {
                if (search!=head)
                    {
                    search.prev.next=search.next.prev;
                    length=head.ind;
                    return search.val;
                    }
                    else {
                    head=head.prev;
                    head.next=null;
                    length=head.ind;
                    return search.val;
                    }
            }


    }

    @Override
    int get(int idx) throws NoSuchElementException {
            if (idx==0||idx>length) throw new NoSuchElementException();

            Node search=head;
            while (search.ind!=idx)
                search = search.prev;
            return search.val;
    }

    @Override
    int size() {
        return length;
    }
}
