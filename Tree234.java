public class Tree234 {//Объект класса представляет все дерево. Три основных метода: удаление, вставка и поиск.
    private Node root = new Node();//создание корневого узла


    public void split(Node thisNode) //Разбиение узла
    {
        DataItem itemB, itemC;
        Node parent, child2, child3;
        int itemIndex;

        itemC = thisNode.removeItem();//Удаление элементов из текущего узла
        itemB = thisNode.removeItem();
        child2 = thisNode.disconnectChild(2);//Отсоединение потомков от текущего узла
        child3 = thisNode.disconnectChild(3);
        Node newRight = new Node();// Создание нового узла

        if(thisNode==root)//если узел является корневым
        {
            root = new Node();//Создание нового корня
            parent = root;//Корень становится родителем
            root.connectChild(0, thisNode);//Связывание с родителем
        }
        else
            parent = thisNode.getParent();//Получения родителя

        itemIndex = parent.insertItem(itemB);//B вставляется в родителя
        int n = parent.getNumItems();

        for (int j = n-1; j>itemIndex; j--) //Перемещение связей родителя на одного потомка вправо
        {
            Node temp = parent.disconnectChild(j);
            parent.connectChild(j+1, temp);

        }
        parent.connectChild(itemIndex+1, newRight);//Связывание newRight с родителями

        newRight.insertItem(itemC);//Элемент С в newRight
        newRight.connectChild(0, child2);//Связывание 0 и 1 с newRight
        newRight.connectChild(1, child3);
    }

    //Получение соответствующего потомка при поиске значения
    public Node getNextChild(Node theNode, long theValue) {
        int j; //предп, что узел не пуст, не полон и не является листом
        int numItems = theNode.getNumItems();
        for (j=0; j<numItems;j++) {//Для каждого элемента в узле
            if(theValue <theNode.getItem(j).dData)//Наше значение меньше --> вернуть левого потомка, иначе правого
                return theNode.getChild(j);
        }
        return theNode.getChild(j);
    }

    public int find(long key) {
        Node curNode = root;
        int childNumber;
        while(true) {
            if ((childNumber=curNode.findItem(key))!=-1)
                return childNumber;//узел найден
            else if (curNode.isLeaf())
                return -1; //узел не найден
            else//искать глубже
                curNode=getNextChild(curNode, key);
        }
    }


    public void insert(long dValue)  {//Вставка элемента данных
        Node curNode = root;
        DataItem tempItem = new DataItem(dValue);
        while (true)
        {
          if (curNode.isFull()) //если узел полон
          {
              split(curNode);//он разбивается
              curNode = curNode.getParent();//Возврат уровнем выше
              curNode = getNextChild(curNode, dValue);
          }
            else if (curNode.isLeaf())//если узел листовой
            break;//переход к вставке
            //узел не полный & не листовой ---> спускаемся ниже
            else
              curNode = getNextChild(curNode, dValue);

        }

        curNode.insertItem(tempItem);//Вставка нового элемента DataItem
    }


    private void recDisplayTree(Node thisNode, int level, int childNumber) {
        System.out.print("level="+level+"; child="+childNumber+" ");
        thisNode.displayNode();
        //Вызов для каждого потомка текущего узла (присутствует рекурсия)
        int numItems = thisNode.getNumItems();
        for (int j=0; j<numItems+1; j++)
        {
            Node nextNode = thisNode.getChild(j);
            if (nextNode!=null)
                    recDisplayTree(nextNode, level+1, j);
            else
                return;
        }

    }



    public void displayTree() {
        recDisplayTree(root, 0, 0);
    }


}