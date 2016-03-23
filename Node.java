
public class Node {
    private static final int ORDER = 4;
    private Node parent;
    private int numItems; //текушее количество элементов данных в узле
    private Node childArray[] = new Node[ORDER]; //Хранятся ссылки на потомков узла
    private DataItem itemArray[] = new DataItem[ORDER - 1]; //Ссылки на объекты элементов данных, хранящиеся в узле

    public void connectChild(int childNum, Node child) { //Связывание узла с потомком
        childArray[childNum] = child;
        if (child != null)
            child.parent = this;
    }

    public Node disconnectChild(int childNum) {//Отсоединяет потомка от узла и возвращает его
        Node tempNode = childArray[childNum];
        childArray[childNum] = null;
        return tempNode;
    }

    public Node getChild(int childNum) {//Возвращает потомка
        return childArray[childNum];
    }

    public Node getParent() {//Возвращает родителя
        return parent;
    }

    public boolean isLeaf() {
        return (childArray[0] == null);
    }

    public int getNumItems() {
        return numItems;
    }

    public DataItem getItem(int index) {//Получение объекта DataItem с заданным индексом
        return itemArray[index];
    }

    public boolean isFull() {
        return (numItems == ORDER - 1);
    }

    public int findItem(long key) {//Определение индекса элемента в пределах узла
        for (int j = 0; j < ORDER - 1; j++) {
            if (itemArray[j] == null)
                break;
            else if (itemArray[j].dData == key)
                return j;
        }
        return -1;
    }

    public int insertItem(DataItem newItem) {//Добавление нового элемента
        numItems++;
        long newKey = newItem.dData;//Ключ нового элемента

        for (int j  = ORDER -2; j>=0; j--)  {//Начиная справа, проверяем элементы. Если ячейка пуста, перейти на одну ячейку влево.
            if (itemArray[j]==null)
                continue;
            else //Если нет - получить ее ключ.
            {
                long itsKey = itemArray[j].dData;
                if(newKey <itsKey)//Если новый ключ больше, то сдивинуть вправо
                    itemArray[j+1] = itemArray[j];
                else
                {
                    itemArray[j+1] = newItem;//Вставка нового элемента
                    return j+1;
                }
            }
        }
        itemArray[0] = newItem;//Все элементы сдвинуты. Вставка нового элемента.
        return 0;
    }

    public DataItem removeItem() {//Удаление наибольшего элемента
        DataItem temp = itemArray[numItems - 1];//Сохранение элемента
        itemArray[numItems - 1] = null;//Отсоединение элемента
        numItems--;
        return temp;//Возвращаем удаленный элемент
    }

    public void displayNode() {
        for (int j=0; j<numItems; j++)
            itemArray[j].displayItem();
        System.out.println("/");
    }
}
