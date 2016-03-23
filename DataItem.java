
public class DataItem {//Элементы данных, хранящиеся в узлах
        public long dData;

        public DataItem(long dd) {
            dData = dd;
        }
        public void displayItem() {
            System.out.print("/" + dData);
        }
}
