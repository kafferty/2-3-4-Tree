import junit.framework.TestCase;
import org.junit.Test;

public class NodeTest extends TestCase {

    @Test
    public void testGetChild() throws Exception {
        Node <String> root = new Node<String>();
        Node <String> child = new Node<String>();
        Node <String> child2 = new Node<String>();
        root.connectChild(0, child);
    }

    public void testGetParent() throws Exception {

    }

    public void testIsLeaf() throws Exception {

    }

    public void testGetItem() throws Exception {

    }

    public void testIsFull() throws Exception {

    }

    public void testFindItem() throws Exception {

    }

    public void testInsertItem() throws Exception {

    }

    public void testRemoveItem() throws Exception {

    }
}