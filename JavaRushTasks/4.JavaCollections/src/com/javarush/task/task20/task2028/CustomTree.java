package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.util.*;

/* 
Построй дерево(1)
*/
public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {
    Entry<String> root = new Entry<>("0");
    int size=0;

    public static void main(String[] args) {
        List<String> list = new CustomTree();
        for (int i = 1; i < 16; i++) {
            list.add(String.valueOf(i));
        }
        System.out.println(list.size());
        System.out.println("Expected 3, actual is " + ((CustomTree) list).getParent("8"));
        list.remove("5");
        System.out.println("Expected null, actual is " + ((CustomTree) list).getParent("11"));
        System.out.println(list.size());

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


    @Override
    public String get(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int i, String s) {
        Entry<String> top = root;
        Entry<String> current = new Entry(s);
        Queue<Entry>queue=new LinkedList<>();
        do {
            if (top.leftChild!=null){//Если в левом поддереве есть нода - добавить её в очередь
                queue.add(top.leftChild);
            }else {
                top.leftChild=current;//Создаем новую ноду в левом поддереве
                top.leftChild.parent=top;//Указываем родителя
                size++;
                return;
            }
            if (top.rightChild!=null){//Если в правом поддереве есть нода - добавить её в очередь
                queue.add(top.rightChild);
            }else {
                top.rightChild=current;//Создаем новую ноду в правом поддереве
                top.rightChild.parent=top;//Указывае родителя
                size++;
                return;
            }
            if (!queue.isEmpty()){
                top=queue.poll();//Берём из начала очереди с удалением
            }
        }while (!queue.isEmpty());
        queue.clear();
    }


    @Override
    public String remove(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<String> iterator() {
        return new Itr();
    }

    public class Itr implements Iterator<String>{
        Entry<String>top=root;
        Queue<Entry>queue_fin=new LinkedList<>();
        public Itr(){
            Queue<Entry>queue=new LinkedList<>();

            do {
                if (top.leftChild!=null){
                    queue.add(top.leftChild);
                    if (!queue_fin.contains(top.leftChild)){
                        queue_fin.add(top.leftChild);
                    }

                }
                if (top.rightChild!=null){
                    if (!queue_fin.contains(top.rightChild)){
                        queue_fin.add(top.rightChild);
                    }
                }
                if (!queue.isEmpty()){
                    top=queue.poll();
                }
            }while (!queue.isEmpty());
        }
        @Override
        public boolean hasNext(){
            return !queue_fin.isEmpty();
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException("All nodes have been visited!");
            }
            try {
                top = queue_fin.poll();
                String next = top.elementName;

                return next;
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
        }

    }

   @Override
   public boolean remove(Object o) {
        if (this.contains(o)){
            Entry<String>top=root;
            Entry<String>search=null;
            String str=(String)o;
            Queue<Entry>queue=new LinkedList<>();
            queue.add(top);
            do {
                if (!queue.isEmpty())top=queue.poll();
                if (top.elementName!=null){
                    if (top.elementName.equals(str)){
                        search=top;
                        break;
                    }
                }
                if (top.leftChild!=null)queue.add(top.leftChild);
                if (top.rightChild!=null)queue.add(top.rightChild);
            }while (!queue.isEmpty());
            queue.clear();
            top=search;
            search=top.parent;
            if (search!=null&&search.leftChild==top){
                search.leftChild=null;
            }else if (search!=null&&search.rightChild==top){
                search.rightChild=null;
            }
            queue.add(top);
            do {
                top=queue.poll();
                if (top.leftChild!=null)queue.add(top.leftChild);
                if (top.rightChild!=null)queue.add(top.rightChild);
                top=null;
                size--;
            }while (!queue.isEmpty());
            queue.clear();
            return true;
        }
        return false;
    }

    public String getParent(String s) {
        Entry top = root;
        String result=null;
        Queue<Entry> queue = new LinkedList<> ();
        queue.add(top);
        do{
            if (top.elementName !=null)
            {
                if (top.elementName.equals(s))
                {
                    result = top.parent.elementName;
                    break;
                }
            }
            if (top.leftChild!=null) queue.add(top.leftChild);
            if (top.rightChild!=null) queue.add(top.rightChild);
            if (!queue.isEmpty()) top=queue.poll();
        }while (!queue.isEmpty());
        return result;
    }

    @Override
    public boolean contains(Object o) {
        Entry<String> top = root;
        boolean result = false;
        Queue<Entry> queue = new LinkedList<> ();
        queue.add(top);
        do{
            top=queue.poll();
            if (top.elementName !=null)
            {
                if (top.elementName.equals(o))
                {
                    result = true;
                    break;
                }
            }
            if (top.leftChild!=null) queue.add(top.leftChild);
            if (top.rightChild!=null) queue.add(top.rightChild);

        }while (!queue.isEmpty());
        return result;
    }

    @Override
    public String set(int i, String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int i, int i1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int i, Collection<? extends String> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        if  (root==null){
            return 0;
        }
        return size;
    }

    static class Entry<T> implements Serializable {
        String elementName;
        int lineNumber;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        Entry<T> parent, leftChild, rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            availableToAddLeftChildren = true;
            availableToAddRightChildren = true;
        }

        void checkChildren() {
            if (leftChild != null) {
                availableToAddLeftChildren = false;
            }
            if (rightChild != null) {
                availableToAddRightChildren = false;
            }
        }

        boolean isAvailableToAddChildren() {
            return availableToAddLeftChildren | availableToAddRightChildren;
        }
    }
}
