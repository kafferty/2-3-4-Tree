import junit.framework.TestCase;
import org.junit.Test;

import java.util.Iterator;

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
        String actual = new String ("level=0; child=0 /test/\n");
        assertEquals(tree.toString(), actual);
    }

    @Test
    public void testRemove() throws Exception {
        Tree234<String> tree = new Tree234<String>();
        tree.insert("for");
        tree.insert("test");
        tree = tree.remove("for");
        assertNull(tree.find("for"));
    }

    @Test
    public void testHeight() throws Exception {
        Tree234<String> tree = new Tree234<String>();
        tree.insert("for");
        tree.insert("test");
        tree.insert("some");
        tree.insert("elem");
        assertEquals(tree.height(), 2);
    }

    @Test
    public void testHasNext() throws Exception {
        Tree234<String> tree = new Tree234<String>();
        tree.insert("for");
        tree.insert("test");
        tree.insert("some");
        tree.insert("elem");
        Iterator iter =  tree.iterator();
        assertEquals(iter.next(), "for");
    }
}