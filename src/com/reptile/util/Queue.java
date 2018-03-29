package com.reptile.util;

import java.util.LinkedList;

/**
 * 队列， 保存将要访问的URL
 */
public class Queue {
    //使用链表实现队列
    private LinkedList queue = new LinkedList();
    //入队列
    public void enQueue (Object o) {
        queue.addLast(o);

    }

    //出队列
    public Object deQueue () {
        return queue.removeFirst();
    }

    //判断队列是否为空
    public boolean isQueueEmpty () {
        return queue.isEmpty();
    }

    //判断队列是否包含某个
    public boolean contains (Object o) {
        return queue.contains(o);
    }

    public boolean empty () {
        return queue.isEmpty();
    }
}
