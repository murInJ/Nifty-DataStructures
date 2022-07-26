import java.util.*;

public class SkipList<E extends Comparable<? super E>> implements Iterable<E> {
    private int curLevel;
    private Node head;
    private int size;
    private static final double PROBABILITY = 0.5;
    private int maxLevel;

    public SkipList(int maxLevel) {
        this.maxLevel = maxLevel;
        size = 0;
        curLevel = 0;
        head = new Node(null);
    }

    public int size() {
        return size;
    }

    public boolean add(E e) {
        if (contains(e)) return false;
        int level = randomLevel();
        if (level > curLevel) curLevel = level;

        Node newNode = new Node(e);
        Node current = head;
        while (level >= 0) {
            current = findNext(e, current, level);
            newNode.forwards.add(0, current.next(level));
            current.forwards.set(level, newNode);
            level--;
        }
        size++;
        return true;
    }

    //返回给定层数中小于e的最大者
    private Node findNext(E e, Node current, int level) {
        Node next = current.next(level);
        while (next != null) {
            if (e.compareTo(next.data) < 0) {
                break;
            }
            //到这说明e >= next.data
            current = next;
            next = current.next(level);
        }
        return current;
    }

    public Node find(E e) {
        if (empty()) {
            return null;
        }
        return find(e, head, curLevel);
    }

    private Node find(E e, Node current, int level) {
        while (level >= 0) {
            current = findNext(e, current, level);
            level--;
        }
        return current;
    }

    public boolean empty() {
        return size == 0;
    }

    public boolean remove(E e) {
        if (empty()) return false;
        boolean removed = false;
        int level = curLevel;
        Node current = head.next(level), pre = head;
        while (level >= 0) {
            while (current != null) {
                if (e.compareTo(current.data) < 0) break;

                if (e.compareTo(current.data) == 0) {
                    pre.forwards.set(level, current.next(level));
                    removed = true;
                    break;
                }
                pre = current;
                current = current.next(level);
            }
            level--;
            if (level < 0) break;

            current = pre.next(level);
        }
        if (removed) {
            size--;
            return true;
        }
        return false;
    }

    private int randomLevel() {
        int level = 0;
        while (Math.random() < PROBABILITY && level < maxLevel - 1) {
            ++level;
        }
        return level;
    }

    public boolean contains(E e) {
        Node node = find(e);
        return node != null && node.data != null && node.data.compareTo(e) == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new SkipListIterator();
    }

    private class SkipListIterator implements Iterator<E> {
        Node current = head;

        @Override
        public boolean hasNext() {
            return current.next(0) != null;
        }

        @Override
        public E next() {
            current = current.next(0);
            return current.data;
        }
    }

    private class Node {
        E data;
        List<Node> forwards;

        Node(E data) {
            this.data = data;
            forwards = new ArrayList<>();
            for (int i = 0; i <= maxLevel; i++) {
                forwards.add(null);
            }
        }

        @Override
        public String toString() {
            return data == null ? " " : "" + data;
        }

        Node next(int level) {
            return this.forwards.get(level);
        }

    }

    public void print() {
        Map<E, Integer> indexMap = new HashMap<>();
        Node current = head.next(0);
        int index = 1;
        int maxWidth = 1;
        while (current != null) {
            int curWidth = current.data.toString().length();
            if (curWidth > maxWidth) {
                maxWidth = curWidth;
            }
            indexMap.put(current.data, index++);
            current = current.next(0);
        }
        print(indexMap, maxWidth);
    }

    private void print(int level, Node current, Map<E, Integer> indexMap, int width) {
        System.out.print("Level " + level + ": ");
        int preIndex = 0;
        while (current != null) {
            int curIndex = indexMap.get(current.data);
            if (level == 0) {
                printSpace(curIndex - preIndex);
            } else {
                int num = (curIndex - preIndex) + (curIndex - preIndex - 1) * width;
                printSpace(num);
            }
            System.out.printf("%" + width + "s", current.data);
            preIndex = curIndex;
            current = current.next(level);
        }
        System.out.println();
    }

    private void printSpace(int num) {
        for (int i = 0; i < num; i++) {
            System.out.print(' ');
        }
    }

    private void print(Map<E, Integer> map, int width) {
        int level = curLevel;
        while (level >= 0) {
            print(level, head.next(level), map, width);
            level--;
        }
    }
}

