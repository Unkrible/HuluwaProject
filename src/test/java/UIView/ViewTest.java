package UIView;

import org.junit.Test;

import static org.junit.Assert.*;

public class ViewTest {
    private View view;

    @Test
    public void testLoadNullImage() throws Exception {
        view = new View(100, 100);
        view.loadImage("fasdfg.png");
    }

    @Test
    public void testLoadShejingImage() throws Exception {
        view = new View(100, 100);
        view.loadImage("蛇精.png");
    }
}