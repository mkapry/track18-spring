package ru.track.list;

import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 * Односвязный список
 */
public class MyLinkedList extends List {
    private static ListElement head=null;
    private static size=0;


    /**
     * private - используется для сокрытия этого класса от других.
     * Класс доступен только изнутри того, где он объявлен
     * <p>
     * static - позволяет использовать Node без создания экземпляра внешнего класса
     */
    private static class Node {
        Node prev;
        Node next;
        int val;

        Node(Node prev, Node next, int val) {
            this.prev = prev;
            this.next = next;
            this.val = val;
        }
    }

    @Override
    void add(int item) {
        if(head==0)
        {
            head=this;
            next=0;
            prev=0;
            val=item;
            size++;
        } else{
            next=head;
            head=this;
            next.prev=this;
            prev=0;
            this.val=item;
            size++;
        }

    }

    @Override
    int remove(int idx) throws NoSuchElementException {
        if(idx>size) throw NoSuchElementException;
        NODE t = head;
        int i=0;
        if(head == null)
            return 0;
        for (i=0;i<idx+1;i++)
                t=t.next;
        while (t.next!==null)

        {
            t.next = t.next.next;
            t.next.prev = t.prev;
            size--;
        }
                return 0;
            }

    }

    @Override
    int get(int idx) throws NoSuchElementException {
        NODE t = head;

        if(idx>size) throw NoSuchElementException;
        if(head == null)
            return 0;
        for (i=0;i<idx+1;i++)
            t=t.next;
        return t;
    }

    @Override
    int size() {
        int i=1;
        NODE c=head;
        while (c!=this)
        {i++;
        c=c.next;
        }
        return i;
    }

}
