import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Tree234App {
    public static void main(String[] args) throws IOException {
        String value;
        Tree234<String> theTree = new Tree234<>();
        theTree.insert("a");
        theTree.insert("meow");
        theTree.insert("kuku");
        theTree.insert("politech");
        while(true) {
            System.out.println("Enter first letter of ");
            System.out.println("show, insert or find");
            char choice = getChar();
            switch(choice)
            {
                case 's':
                    theTree.displayTree();
                    break;
                case 'i':
                    System.out.print("Enter value to insert: ");
                    value  = getString();
                    theTree.insert(value);
                    break;
                case 'f':
                    System.out.print("Enter value to find: ");
                    value = getString();
                    int found = theTree.find(value);
                    if(found!=-1)
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
