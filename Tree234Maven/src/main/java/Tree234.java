import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

public class Tree234<T extends Comparable<T>> implements Collection<T>{//Объект класса представляет все дерево. Три основных метода: удаление, вставка и поиск.

    private Node<T> root = new Node<T>();//создание корневого узла
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
        Node<T> newRight = new Node<T>();// Создание нового узла

        if (thisNode == root)//если узел является корневым
        {
            root = new Node<T>();//Создание нового корня
            parent = root;//Корень становится родителем
            root.connectChild(0, thisNode);//Связывание с родителем
        } else
            parent = thisNode.getParent();//Получение родителя

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
        if (find(dValue)==null) {
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
        else
            return;
    }

   /* public void remove (T dValue) {//Удаление элемента данных
        Node<T> nodeToDelete = find(dValue);
        Node<T> tempNode;
        Node<T> currentNode;
        if(!nodeToDelete.isLeaf()) {
            tempNode = nodeToDelete;
            currentNode = nodeToDelete.getChild(nodeToDelete.findItem(dValue)+1);
            while (!currentNode.isLeaf()) {
                currentNode = currentNode.getChild(0);
                if (currentNode.getNumItems()==2);
            }
        }
    } */

    public Tree234<T> remove(T dValue) {
        Iterator iter =  this.iterator();
        Tree234 <T> newTree = new Tree234<T>();
        while (iter.hasNext()) {
            T item = (T)iter.next();
            if (!item.equals(dValue)) {
                newTree.insert(item);
            }
        }
        if (!(findMin().getItem(0).dData.equals(dValue)))
            newTree.insert(findMin().getItem(0).dData);
        return newTree;
    }



    public int height() {
        int i = 1;
        if (!this.isEmpty()) {
            Node<T> currentNode = root;
            while (currentNode.getChild(0) != null) {
                currentNode = currentNode.getChild(0);
                i++;
            }
        }
        return i;
    }


    private void recDisplayTree(StringBuilder sb, Node<T> thisNode, int level, int childNumber) {
        sb.append("level=").append(level).append("; child=").append(childNumber).append(" ");
        sb.append(thisNode);

        //Вызов для каждого потомка текущего узла (присутствует рекурсия)
        int numItems = thisNode.getNumItems();
        int i = 0;
        for (int j = 0; j < numItems + 1; j++) {
            Node<T> nextNode = thisNode.getChild(j);
            if (nextNode != null) {
                recDisplayTree(sb, nextNode, level + 1, j);
            }
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
            while ((currentNode.getChild(currentNode.getNumItems())) != null) {
                currentNode = currentNode.getChild(currentNode.getNumItems());
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

        public TreeIterator() {
            currentNode = findMin();
            currentItem = findMin().getItem(0);
        }
        public DataItem<T> getCurrentItem() {
            return currentItem;
        }

        public boolean hasNext() {
            if (currentItem == findMax().getItem(findMax().getNumItems()-1)){
                return false;
            }
            return true;
        }

        public T next() {
            StringBuffer str = new StringBuffer();
            str.append(currentItem);
            int iItem = currentNode.findItem(str.deleteCharAt(0).toString())+1;
            if (iItem+1<=currentNode.getNumItems()) {

                while (!currentNode.isLeaf()) {
                    currentNode = currentNode.getChild(iItem);
                    iItem = 0;
                }
                currentItem = currentNode.getItem(iItem);
                return currentItem.dData;
            }
            else {
                if (!currentNode.isLeaf()) {
                    currentNode = currentNode.getChild(iItem);
                    while(!currentNode.isLeaf()) {
                        currentNode = currentNode.getChild(0);
                    }
                    currentItem = currentNode.getItem(0);
                }
                else {
                    //currentNode = currentNode.getParent();
                    //while (currentNode.getNumItems()>iItem) {
                      //  currentNode = currentNode.getParent();
                    //}
                    Node<T> parent = currentNode.getParent();
                    while (haveElement(currentItem, parent)==false) {
                        parent = parent.getParent();
                    }
                    for (int i = 0; i<parent.getNumItems(); i++) {
                        if (currentItem.dData.compareTo(parent.getItem(i).dData)<0) {
                            currentItem = parent.getItem(i);
                            currentNode = parent;
                            break;
                        }
                    }
                   // currentItem = currentNode.getItem(iItem-1);
                }
                return currentItem.dData;
            }
        }

        public void remove() {
            throw new UnsupportedOperationException("remove");
        }

    }

    private boolean haveElement(DataItem<T> item, Node<T> parent) {
        for (int i = 0; i<parent.getNumItems(); i++) {
            if (item.dData.compareTo(parent.getItem(i).dData)<0) {
                item = parent.getItem(i);
                return true;
            }
        }
        return false;
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