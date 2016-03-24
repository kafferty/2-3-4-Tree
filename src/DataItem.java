public class DataItem<T extends Comparable<T> > {//Элементы данных, хранящиеся в узлах
        public T dData;

        public DataItem(T dd) {
            dData = dd;
        }
        public void displayItem() {
            System.out.print("/" + dData);
        }

}
