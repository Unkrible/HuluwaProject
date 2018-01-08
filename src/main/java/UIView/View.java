package UIView;

import java.awt.Image;
import java.awt.Dimension;
import java.net.URL;
import javax.swing.ImageIcon;

public class View {
    public static final long animationFps = 10;

    private int x;
    private int y;
    private Dimension dim;

    private Image image;

    public View(int width, int height) {
        this.dim = new Dimension(width, height);
    }

    public View(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.dim = new Dimension(width, height);
    }

    public void loadImage(String srcName){
        URL loc = this.getClass().getClassLoader().getResource(srcName);
        if(loc==null)
            return;
        ImageIcon iia = new ImageIcon(loc);
        Image scaledImage = iia.getImage().getScaledInstance(this.dim.width, this.dim.height, Image.SCALE_DEFAULT);
        this.image = scaledImage;
    }

    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setRect(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.dim.setSize(width, height);
    }

    public void setDim(int width, int height) {
        this.dim.setSize(width, height);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
    public Dimension getDim() {
        return this.dim;
    }

    public Image getImage() {
        return this.image;
    }
}
