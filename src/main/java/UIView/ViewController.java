package UIView;

import java.awt.*;

public interface ViewController {
    public void loadView(int x, int y, int width, int height, String srcName);
    public int getViewX();
    public int getViewY();
    public Dimension getDim();
    public Image getViewImage();
}
