import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

public class Tree234<T extends Comparable<T>> implements Collection<T>,Iterable<T> {//Объект класса представляет все дерево. Три основных метода: удаление, вставка и поиск.

    private Node<T> root = new Node<T>();//создание корневого узла
    private int size = 0;
    private Node<T> min = null;
    private Node<T> max = null;

    public void split(Node<T> thisNode) //Разбиение узла
    {
        DataItem<T> itemB, itemC;
        Node<T> parent, child2, child3;
        int itemIndex;

        itemC = thisNode.removeItem();//Удаление элементов из текущего узла
        itemB = thisNode.removeItem();
        child2 = thisNode.disconnectChild(2);//Отсоединение потомков от текущего узла
        child3 = thisNode.disconnectChild(3);
        Node<T> newRight = new Node<T>();// Создание нового узла

        if (thisNode == root)//если узел является корневым
        {
            root = new Node<T>();//Создание нового корня
            parent = root;//Корень становится родителем
            root.connectChild(0, thisNode);//Связывание с родителем
        } else
            parent = thisNode.getParent();//Получения родителя

        itemIndex = parent.insertItem(itemB);//B вставляется в родителя
        int n = parent.getNumItems();

        for (int j = n - 1; j > itemIndex; j--) //Перемещение связей родителя на одного потомка вправо
        {
            Node<T> temp = parent.disconnectChild(j);
            parent.connectChild(j + 1, temp);

        }
        parent.connectChild(itemIndex + 1, newRight);//Связывание newRight с родителями

        newRight.insertItem(itemC);//Элемент С в newRight
        newRight.connectChild(0, child2);//Связывание 0 и 1 с newRight
        newRight.connectChild(1, child3);
    }

    //Получение соответствующего потомка при поиске значения
    public Node<T> getNextChild(Node<T> theNode, T theValue) {
        int j; //предп, что узел не пуст, не полон и не является листом
        int numItems = theNode.getNumItems();
        for (j = 0; j < numItems; j++) {//Для каждого элемента в узле
            if (theValue.compareTo(theNode.getItem(j).dData) < 0)//Наше значение меньше --> вернуть левого потомка, иначе правого
                return theNode.getChild(j);
        }
        return theNode.getChild(j);
    }

    public Node<T> find(T key) {
        Node<T> curNode = root;
        while (true) {
            if ((curNode.findItem(key)) != -1)
                return curNode;//узел найден
            else if (curNode.isLeaf())
                return null; //узел не найден
            else//искать глубже
                curNode = getNextChild(curNode, key);
        }
    }


    public void insert(T dValue) {//Вставка элемента данных
        Node<T> curNode = root;
        DataItem<T> tempItem = new DataItem<T>(dValue);
        while (true) {
            if (curNode.isFull()) //если узел полон
            {
                split(curNode);//он разбивается
                curNode = curNode.getParent();//Возврат уровнем выше
                curNode = getNextChild(curNode, dValue);
            } else if (curNode.isLeaf())//если узел листовой
                break;//переход к вставке
                //узел не полный & не листовой ---> спускаемся ниже
            else
                curNode = getNextChild(curNode, dValue);

        }

        curNode.insertItem(tempItem);//Вставка нового элемента DataItem<T>
        size++;
    }

    public void remove (T dValue) {//Удаление элемента данных
        Node<T> nodeToDelete = find(dValue);
        Node<T> tempNode;
        Node<T> currentNode;
        if(!nodeToDelete.isLeaf()) {
            tempNode = nodeToDelete;
            currentNode = nodeToDelete.getChild(nodeToDelete.findItem(dValue)+1);
            while (!currentNode.isLeaf()) {
                currentNode = currentNode.getChild(0);
                if (currentNode.getNumItems()==2)
            }
        }
    }


    private void recDisplayTree(StringBuilder sb, Node<T> thisNode, int level, int childNumber) {
        sb.append("level=").append(level).append("; child=").append(childNumber).append(" ");
        sb.append(thisNode);

        //Вызов для каждого потомка текущего узла (присутствует рекурсия)
        int numItems = thisNode.getNumItems();
        for (int j = 0; j < numItems + 1; j++) {
            Node<T> nextNode = thisNode.getChild(j);
            if (nextNode != null)
                recDisplayTree(sb, nextNode, level + 1, j);
            else
                return;
        }
    }

    private Node<T> findMin() {
        if (!this.isEmpty()) {
            Node<T> currentNode = root;
            while (currentNode.getChild(0) != null)
                currentNode = currentNode.getChild(0);
            return currentNode;
        }
        else
            return null;
    }

    private Node<T> findMax() {
        if (!this.isEmpty()) {
            Node<T> currentNode = root;
            while ((currentNode.getChild(currentNode.getNumItems() + 1)) != null) {
                currentNode = currentNode.getChild(currentNode.getNumItems() + 1);
            }
            return currentNode;
        }
        else
            return null;
    }


    public void displayTree() {
        StringBuilder sb = new StringBuilder();
        recDisplayTree(sb, root, 0, 0);
        System.out.println(sb);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        recDisplayTree(sb, root, 0, 0);
        return sb.toString();
    }


    public int size() {
        return size;
    }


    public boolean isEmpty() {
        return size == 0;
    }


    public boolean contains(Object o) {
        try {
            return find((T) o) != null;
        } catch (ClassCastException exception) {
            return false;
        }
    }

    class TreeIterator implements Iterator<T> {
        private Node<T> currentNode;
        private DataItem<T> currentItem;
        private boolean detect = false;
        private Node<T> followingNode;
        private DataItem<T> followingItem;
        public TreeIterator() {
            currentNode = findMin();
            currentItem = findMin().getItem(0);
        }
        public boolean hasNext() {
            return currentNode != findMax();
        }

        public T next() {
            DataItem<T> tempItem;
//           /*while (true) {
//
//                if(currentNode.isLeaf()) {
//                    if (currentNode.findItem(currentItem) != currentNode.getNumItems()) {
//                        tempItem = currentItem;
//                        currentItem = currentNode.getItem(currentNode.findItem(currentItem) + 1);
//                        return tempItem.dData;
//                    } else {
//                        if (currentNode.getParent().getChild(currentNode.getParent().getNumItems() + 1) != currentNode) {
//                            int i = 0;
//                            Node<T> tempNode = currentNode;
//                            currentNode = currentNode.getParent();
//                            while (currentNode.getChild(i) != tempNode) {
//                                i++;
//                            }
//                            currentItem = currentNode.getItem(i);
//                        }
//                        else {
//                            currentNode = currentNode.getParent().getParent();
//                        }
//                    }
//                }
//            }*/
            tempItem = currentItem;
            if(!currentNode.isLeaf())
                currentNode = currentNode.getChild(currentNode.findItem(currentItem.dData) + 1);
            while(!currentNode.isLeaf()) {
                currentNode = currentNode.getChild(0);
                currentItem = currentNode.getItem(0);
            }
            followingItem = currentItem;
            if(currentItem != currentNode.getItem(currentNode.getNumItems()))
                currentItem = currentNode.getItem(currentNode.findItem(currentItem)+1);
            else {
                Node<T> tempNode = currentNode;
                currentNode = currentNode.getParent();
                int i = 0;
                while(currentNode.getChild(i) != tempNode)
                    i++;
                currentItem = currentNode.getItem(i);
                followingItem = currentItem;
                followingNode = currentNode;
            }
            return tempItem.dData;


        }
    }
    public Iterator<T> iterator() {
        return new TreeIterator();
    }

    public Object[] toArray() {
        return new Object[0];
    }


    public <T1> T1[] toArray(T1[] a) {
        return null;
    }


    public boolean add(T t) {
        insert(t);
        return true;
    }


    public boolean remove(Object o) {
        return false;
    }


    public boolean containsAll(Collection<?> c) {
        Iterator iterator = c.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if (!this.contains(o)) return false;
        }
        return true;
    }

    public boolean addAll(Collection<? extends T> c) {
        Iterator iterator = c.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            try {
                this.add((T)o);
            } catch (ClassCastException ignored) {
            }
        }
        return true;
    }


    public boolean removeAll(Collection<?> c) {
        return false;
    }


    public boolean retainAll(Collection<?> c) {
        return false;
    }

    public void clear() {

    }
}