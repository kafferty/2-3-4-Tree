import java.util.Collection;
import java.util.Iterator;

public class Tree234<T extends Comparable<T>> implements Collection<T> {//Объект класса представляет все дерево. Три основных метода: удаление, вставка и поиск.
    private Node<T> root = new Node<>();//создание корневого узла
    private int size = 0;

    public void split(Node<T> thisNode) //Разбиение узла
    {
        DataItem<T> itemB, itemC;
        Node<T> parent, child2, child3;
        int itemIndex;

        itemC = thisNode.removeItem();//Удаление элементов из текущего узла
        itemB = thisNode.removeItem();
        child2 = thisNode.disconnectChild(2);//Отсоединение потомков от текущего узла
        child3 = thisNode.disconnectChild(3);
        Node<T> newRight = new Node<>();// Создание нового узла

        if (thisNode == root)//если узел является корневым
        {
            root = new Node<>();//Создание нового корня
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

    public int find(T key) {
        Node<T> curNode = root;
        int childNumber;
        while (true) {
            if ((childNumber = curNode.findItem(key)) != -1)
                return childNumber;//узел найден
            else if (curNode.isLeaf())
                return -1; //узел не найден
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


    private void recDisplayTree(Node<T> thisNode, int level, int childNumber) {
        System.out.print("level=" + level + "; child=" + childNumber + " ");
        thisNode.displayNode();
        //Вызов для каждого потомка текущего узла (присутствует рекурсия)
        int numItems = thisNode.getNumItems();
        for (int j = 0; j < numItems + 1; j++) {
            Node<T> nextNode = thisNode.getChild(j);
            if (nextNode != null)
                recDisplayTree(nextNode, level + 1, j);
            else
                return;
        }

    }


    public void displayTree() {
        recDisplayTree(root, 0, 0);
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        try {
            return find((T) o) != -1;
        } catch (ClassCastException exception) {
            return false;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        insert(t);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Iterator iterator = c.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if (!this.contains(o)) return false;
        }
        return true;
    }

    @Override
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

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }
}