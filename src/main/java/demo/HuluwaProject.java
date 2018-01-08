package demo;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;

import Field.Field;
import Log.HuluwaLogger;
import Model.Creature.Creature;
import Model.Creature.Huluwa;
import Model.Position.PositionXY;
import Sort.Formation;

public class HuluwaProject extends JFrame {

    FileDialog fileDialog = null;

    public HuluwaProject() {
        init();
    }

    public void init() {
        this.add(Field.getInstance());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Field.getInstance().getDim().width, Field.getInstance().getDim().height+36);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Huluwa");
        this.addKeyListener(new TAdapter());
        this.setFocusable(true);
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            switch (key){
                case KeyEvent.VK_L:
                    fileDialog = new FileDialog(HuluwaProject.this, "Open Record File", FileDialog.LOAD);
                    fileDialog.setVisible(true);
                    HuluwaLogger.getInstance().readFile(fileDialog.getDirectory()+fileDialog.getFile());
                default:
                    Field.getInstance().keyCodeHandle(key);
                    break;
            }

            repaint();
        }
    }
}

