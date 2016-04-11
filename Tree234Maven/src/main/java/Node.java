import java.util.ArrayList;

public class Node<T extends Comparable<T>>{

    private static final int ORDER = 4;
    private Node <T> parent;
    private int numItems; //текушее количество элементов данных в узле
    private ArrayList<Node <T>> children = new ArrayList<Node <T>>(ORDER);//Хранятся ссылки на потомков узла
    private ArrayList<DataItem<T>> items = new ArrayList<DataItem<T>>(ORDER - 1); //Ссылки на объекты элементов данных, хранящиеся в узле
    public Node() {
        for (int i = 0; i < ORDER; i++) children.add(i, null);
        for (int i = 0; i < ORDER - 1; i++) items.add(i, null);
    }
    public void connectChild(int childNum, Node<T> child) { //Связывание узла с потомком
        children.set(childNum, child);
        if (child != null)
            child.parent = this;
    }

    public Node<T> disconnectChild(int childNum) {//Отсоединяет потомка от узла и возвращает его
        Node<T> tempNode = children.get(childNum);
        children.set(childNum, null);
        return tempNode;
    }

    public Node<T> getChild(int childNum) {//Возвращает потомка
        return children.get(childNum);
    }

    public Node<T> getParent() {//Возвращает родителя
        return parent;
    }

    public boolean isLeaf() {
        return (children.get(0) == null);
    }

    public int getNumItems() {
        return numItems;
    }

    public DataItem<T> getItem(int index) {//Получение объекта DataItem с заданным индексом
        return items.get(index);
    }

    public boolean isFull() {
        return (numItems == ORDER - 1);
    }

    public int findItem(Object key) {//Определение индекса элемента в пределах узла
        for (int j = 0; j < ORDER - 1; j++) {
            if (items.get(j) == null)
                break;
            else
                if (items.get(j).dData.equals(key))
                    return j;
        }
        return -1;
    }

    public int insertItem(DataItem<T> newItem) {//Добавление нового элемента
        numItems++;
        T newKey = newItem.dData;//Ключ нового элемента

        for (int j  = ORDER -2; j>=0; j--)  {//Начиная справа, проверяем элементы. Если ячейка пуста, перейти на одну ячейку влево.
            if (items.get(j) != null)//Если нет - получить ее ключ.
            {
                T itsKey = items.get(j).dData;
                if(newKey.compareTo(itsKey) < 0)//Если новый ключ больше, то сдивинуть вправо
                    items.set(j + 1, items.get(j));
                else
                {
                    items.set(j+1, newItem);//Вставка нового элемента
                    return j+1;
                }
            }
        }
        items.set(0, newItem);//Все элементы сдвинуты. Вставка нового элемента.
        return 0;
    }

    public DataItem<T> removeItem() {//Удаление наибольшего элемента
        DataItem<T> temp = items.get(numItems - 1);//Сохранение элемента
        items.set(numItems - 1, null);//Отсоединение элемента
        numItems--;
        return temp;//Возвращаем удаленный элемент
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int j=0; j<numItems; j++)
            sb.append(items.get(j));
        sb.append("/");
        return sb.toString();
    }
}
