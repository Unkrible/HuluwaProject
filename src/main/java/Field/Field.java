package Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Log.HuluwaLogger;
import Model.Creature.*;
import Model.Position.Pit;
import Model.Position.PositionXY;
import Sort.Formation;
import UIView.View;
import UIView.ViewController;
import Action.Move;
import Action.Attack;
import org.junit.Test;

public class Field extends JPanel implements ViewController{
    public static final int FIELD_SIZE_OF_X = 12;
    public static final int FIELD_SIZE_OF_Y = 8;

    private View fieldView;
    private Image ground;
    private Image bufferedImage;
    private Thread replay;

    public STATE state = STATE.START;
    private ArrayList<Pit> pits = new ArrayList<Pit>();
    private ExecutorService exec = null;
    public ArrayList<Creature> creatures = new ArrayList<Creature>();
    public ArrayList<Creature> huluwas = new ArrayList<Creature>();
    public ArrayList<Creature> yaojings = new ArrayList<Creature>();


    // Singleton
    private static Field battleField = new Field();

    public static synchronized Field getInstance() {
        return battleField;
    }

    // constructor
    private Field() {
        startField();
    }

    void startField(){
        int width = FIELD_SIZE_OF_X * Pit.PIT_WIDTH;
        int height = FIELD_SIZE_OF_Y * Pit.PIT_HEIGHT;
        fieldView = new View(width, height);
        loadView(0, 0, width, height, "background.png");
    }

    void initField() {
        pits.clear();
        creatures.clear();
        huluwas.clear();
        yaojings.clear();

        // init Data
        for (int i = 0; i < FIELD_SIZE_OF_Y; ++i) {
            for (int j = 0; j < FIELD_SIZE_OF_X; ++j) {
                pits.add(new Pit(new PositionXY(i, j, FIELD_SIZE_OF_X)));
            }
        }

        // init View
        URL loc = this.getClass().getClassLoader().getResource("ground.png");
        ImageIcon groundIcon = new ImageIcon(loc);
        Image scaledImage = groundIcon.getImage().getScaledInstance(Pit.PIT_WIDTH, Pit.PIT_HEIGHT, Image.SCALE_DEFAULT);
        ground = scaledImage;

        Creature creature;
        for (int i = 0; i < Huluwa.allNum; ++i) {
            creature = new Huluwa();
            creatures.add(creature);
            huluwas.add(creature);
            pits.get(i).getPosition().setHolder(creature);
        }
        new Formation().embattle("长蛇", this, huluwas, new PositionXY(1, 0, FIELD_SIZE_OF_X));

        creature = new Yeye();
        creatures.add(creature);
        huluwas.add(creature);
        pits.get(0).getPosition().setHolder(creature);

        for (int i = 0; i < Loulou.allNum; ++i) {
            if (i == 1) {
                creature = new XieziJing();
            } else {
                creature = new Loulou();
            }
            pits.get(i * FIELD_SIZE_OF_X + FIELD_SIZE_OF_X - 1).getPosition().setHolder(creature);
            creatures.add(creature);
            yaojings.add(creature);
        }
        new Formation().embattle("锋矢", this, yaojings, new PositionXY(2, 6, FIELD_SIZE_OF_X));

        for(Creature each: creatures){
            each.updateView();
        }
    }

    void playingField() {
        exec = Executors.newCachedThreadPool();
        try {

            for (Creature each : creatures) {
                exec.execute(each);
            }
            exec.shutdown();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void drawBufferedImage() {
        bufferedImage = createImage(fieldView.getDim().width, fieldView.getDim().height);
        Graphics g = bufferedImage.getGraphics();
        try {
            if (state == STATE.START) {
                g.drawImage(fieldView.getImage(), 0, 0, this);
            } else {
                // background image
                for (Pit pit : pits) {
                    g.drawImage(ground, pit.getX(), pit.getY(), this);
                }
                // Creatures image
                for (Creature dead : creatures) {
                    if (!dead.getAlive())
                        g.drawImage(dead.getViewImage(), dead.getViewX(), dead.getViewY(), this);
                }
                for (Creature creature : creatures) {
                    if (creature.getAlive())
                        g.drawImage(creature.getViewImage(), creature.getViewX(), creature.getViewY(), this);
                }

                if (state == STATE.COMPLETED) {
                    g.setColor(new Color(0, 0, 0));
                    g.setFont(new Font("MS UI Gothic", Font.BOLD, 40));
                    g.drawString("Game Over", this.getDim().width / 2 - 120, this.getDim().height / 2);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void buildWorld(Graphics g) {
        // paint creatures
        drawBufferedImage();
        g.drawImage(bufferedImage, 0, 0, this);
    }

    public synchronized ArrayList<Pit> getPits() {
        return pits;
    }
    public synchronized ArrayList<Creature> getHuluwas() {
        return huluwas;
    }
    public synchronized ArrayList<Creature> getYaojings() {
        return yaojings;
    }
    public boolean getCompleted() { return state == STATE.COMPLETED; }
    public void judgeCompleted() {
        if(judgeCreatureDie(huluwas) || judgeCreatureDie(yaojings)){
            state = STATE.COMPLETED;
            HuluwaLogger.getInstance().writeFile();
        }
    }
    public boolean judgeCreatureDie(ArrayList<Creature> creatures) {
        boolean flag = true;
        for (Creature creature : creatures){
            if(creature.getAlive()){
                flag = false;
                break;
            }
        }
        return flag;
    }

    //Interface ViewController
    @Override
    public void loadView(int x, int y, int width, int height, String srcName){
        fieldView.setRect(x, y, width, height);
        fieldView.loadImage(srcName);
    }
    @Override
    public int getViewX(){
        return fieldView.getX();
    }
    @Override
    public int getViewY(){
        return fieldView.getY();
    }
    @Override
    public Dimension getDim() {
        return fieldView.getDim();
    }
    @Override
    public Image getViewImage(){
        return fieldView.getImage();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        buildWorld(g);
    }

    public int getPitIndex(String action){
        String[] pos = action.split(",");
        int y = new Integer(pos[0]);
        int x = new Integer(pos[1]);
        return x + y * FIELD_SIZE_OF_X;
    }

    private void highlightReplay() {
        if(state != STATE.REPLAY) {
            replay = new Thread(new HighlightReplay());
            replay.start();
        } else {
            replay.interrupt();
            replay = new Thread(new HighlightReplay());
            replay.start();
        }
    }

    class HighlightReplay implements Runnable {
        @Override
        public void run() {
            if(HuluwaLogger.getInstance().getStr().isEmpty()){
                return;
            }
            String battle = HuluwaLogger.getInstance().getStr();
            String[] highlights = battle.split("\n");
            String[] actions;
            if(state == STATE.PLAYING){
                restartGame();
            } else {
                initField();
            }
            state = STATE.REPLAY;
            repaint();
            try{
                Thread.sleep(1000);
            } catch (Exception e){
                e.printStackTrace();
            }
            try {
                for (String highlight : highlights) {
                    actions = highlight.split(" ");
                    if (actions.length == 1) {
                        pits.get(getPitIndex(actions[0])).getPosition().getHolder().setAlive(false);
                    } else {
                        new Move(pits.get(getPitIndex(actions[0])).getPosition().getHolder(),
                                pits.get(getPitIndex(actions[0])).getPosition(),
                                pits.get(getPitIndex(actions[1])).getPosition(),
                                Field.this).action();
                       //pits.get(getPitIndex(actions[0])).getPosition().getHolder().moveTo(getPitIndex(actions[1]));
                    }
                    repaint();
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void restartGame() {
        if(exec != null) {
            try {
                exec.shutdownNow();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        initField();
    }

    private void startGame(){
        if(state == STATE.PLAYING){
            restartGame();
        } else if(state == STATE.REPLAY){
            replay.interrupt();
            replay = null;
            initField();
        } else{
            initField();
        }
        state = STATE.PLAYING;
        playingField();
    }

    public void keyCodeHandle(int keyCode){
        switch (keyCode){
            case KeyEvent.VK_SPACE:
                startGame();
                System.out.println("VK_SPACE");
                break;
            case KeyEvent.VK_R:
                restartGame();
                break;
            case KeyEvent.VK_1:
                try {
                    new Formation().embattle("长蛇", this, huluwas, new PositionXY(0, 0, FIELD_SIZE_OF_X));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            case KeyEvent.VK_2:
                try {
                    new Formation().embattle("锋矢", this, yaojings, new PositionXY(2, 6, FIELD_SIZE_OF_X));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            case KeyEvent.VK_L:
                highlightReplay();
                break;
        }
        repaint();
    }

    enum STATE {
        START, COMPLETED, PLAYING, REPLAY;
    }
}
