import junit.framework.TestCase;
import org.junit.Test;

public class Tree234Test extends TestCase {

    @Test
    public void testFind() throws Exception {
        Tree234<String> tree = new Tree234<String>();
        tree.insert("for");
        tree.insert("test");
        Node find = tree.find("for");
        assertNotNull(find);
    }

    @Test
    public void testInsert() throws Exception {
        Tree234<String> tree = new Tree234<String>();
        tree.insert("for");
        tree.insert("test");
        Node find = tree.find("test");
        Node findTwo = tree.find("for");
        assertNotNull(find);
        assertNotNull(findTwo);
    }

    @Test
    public void testInsertTwo() throws Exception {
        Tree234<String> tree = new Tree234<String>();
        tree.insert("test");
        tree.insert("test");
        String actual = new String ("level=0; child=0 /test/");
        assertEquals(tree.toString(), actual);
    }
}