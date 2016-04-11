import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;


public class Tree234App {
    public static void main(String[] args) throws IOException {
        String value;
        Tree234<String> theTree = new Tree234<String>();
        theTree.insert("test");
        Iterator iter =  theTree.iterator();
        while(true) {
            System.out.println("Enter first letter of ");
            System.out.println("show, insert, next or find");
            char choice = getChar();
            switch(choice)
            {
                case 's':
                    theTree.displayTree();
                    break;
                case 'i':
                    System.out.print("Enter value to insert: ");
                    value  = getString();
                    Node found = theTree.find(value);
                    if (found!=null) {
                        System.out.println("This element is already in the tree");
                    } else {
                        theTree.insert(value);
                        iter = theTree.iterator();
                    }
                    break;
                case 'n':
                    if (iter.hasNext()) {
                        System.out.println(iter.next());
                    }
                    else {
                        System.out.println("This is the end.. ");
                    }
                    break;
                case 'f':
                    System.out.print("Enter value to find: ");
                    value = getString();
                    Node find = theTree.find(value);
                    if(find!=null)
                        System.out.println("Found " + value);
                    else
                        System.out.println("Could not find "+value);
                    break;
                default:
                    System.out.print("Invalid entry\n");
            }
        }
    }
    public static String getString() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;
    }

    public static char getChar() throws IOException {
        String s = getString();
        return s.charAt(0);
    }

    public static int getInt() throws IOException {
        String s = getString();
        return Integer.parseInt(s);
    }

}
