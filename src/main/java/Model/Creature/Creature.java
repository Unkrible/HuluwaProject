package Model.Creature;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import Action.Attack;
import Action.Move;
import Field.Field;
import Log.HuluwaLogger;
import Model.Position.Pit;
import Model.Position.Position;
import Model.Position.PositionXY;
import UIView.View;
import UIView.ViewController;
import javafx.geometry.Pos;

import javax.swing.*;

public abstract class Creature implements ViewController, Runnable{

    //MARK: Properties
    protected String name;
    protected Position position;
    protected View view;
    protected CAMP camp;
    protected boolean alive;

    //MARK: Methods
    protected abstract void loadDieImage();
    protected abstract Position getEnemyPos();

    protected final boolean judgeLimit(DIRECTION direction){
        if(!(this.position instanceof PositionXY))
            return position.getX()>0 && position.getX() < Field.FIELD_SIZE_OF_X-1;
        PositionXY positionXY = (PositionXY)this.position;
        boolean result = false;
        switch (direction){
            case UP:
                result = positionXY.getY() > 0;
                break;
            case DOWN:
                result = positionXY.getY() < Field.FIELD_SIZE_OF_Y-1;
                break;
            case LEFT:
                result =  positionXY.getX() > 0;
                break;
            case RIGHT:
                result = positionXY.getX() < Field.FIELD_SIZE_OF_X-1;
                break;
            case UPLEFT:
                result = (positionXY.getY() > 0) && (positionXY.getX() > 0);
                break;
            case UPRIGHT:
                result = (positionXY.getY() > 0) && (positionXY.getX() < Field.FIELD_SIZE_OF_X-1);
                break;
            case DOWNLEFT:
                result = (positionXY.getY() < Field.FIELD_SIZE_OF_Y-1) && (positionXY.getX() > 0);
                break;
            case DOWNRIGHT:
                result = (positionXY.getY() < Field.FIELD_SIZE_OF_Y-1) && (positionXY.getX() < Field.FIELD_SIZE_OF_X-1);
        }
        return result;
    }
    protected final DIRECTION randomDirection(){
        Random rand = new Random();
        DIRECTION direction = DIRECTION.values()[rand.nextInt(8)];
        return  direction;
    }
    protected final PositionXY enemyPos(){
        PositionXY enemyPos = (PositionXY)getEnemyPos();
        PositionXY myPos = (PositionXY)this.position;
        int x = enemyPos.getX() > myPos.getX() ? 1 : -1;
        int y = enemyPos.getY() > myPos.getY() ? 1 : -1;
        if (enemyPos.getX() == myPos.getX())
            x = 0;
        if (enemyPos.getY() == myPos.getY())
            y = 0;
        return new PositionXY(y, x, Field.FIELD_SIZE_OF_X);
    }

    protected final DIRECTION enemyDirection() {
        PositionXY enemyPos = enemyPos();
        int x = enemyPos.getX();
        int y = enemyPos.getY();
        // x>0 往右 y>0往下
        if(x>0 && y>0){
            return DIRECTION.DOWNRIGHT;
        } else if (x>0 && y<0){
            return DIRECTION.UPRIGHT;
        } else if (x<0 && y>0){
            return DIRECTION.DOWNLEFT;
        } else if (x<0 && y<0){
            return DIRECTION.UPLEFT;
        } else if (x==0 && y>0){
            return DIRECTION.DOWN;
        } else if (x==0 && y<0){
            return DIRECTION.UP;
        } else if (x>0 && y==0){
            return DIRECTION.RIGHT;
        } else if (x<0 && y==0){
            return DIRECTION.LEFT;
        } else {
            return randomDirection();
        }
    }
    protected final void beDead(){
        HuluwaLogger.getInstance().append(this.position+ "\n");
            this.loadDieImage();
            this.getPosition().clearHolder();
            this.position = null;
    }

    public final Position getPosition(){
        return this.position;
    }
    public final CAMP getCamp() {
        return this.camp;
    }
    public final boolean getAlive() {
        return this.alive;
    }
    public final View getView() {
        return this.view;
    }
    public final String getName(){
        return this.name;
    }

    public final void setPosition(Position position){
        this.position = position;
    }
    public final void setAlive(boolean alive) {
        this.alive = alive;
        if(!alive)
            beDead();
    }

    public final void updateView() {
        if(!(position instanceof PositionXY)){
            return;
        }
        PositionXY posXY = (PositionXY)position;
        view.setXY(posXY.getX()*Pit.PIT_WIDTH, posXY.getY()*Pit.PIT_HEIGHT);
    }
    //Interface: ViewController
    @Override
    public final void loadView(int x, int y, int width, int height, String srcName){
        view.setRect(x, y, width, height);
        view.loadImage(srcName);
    }
    @Override
    public final int getViewX(){
        return view.getX();
    }
    @Override
    public final int getViewY(){
        return view.getY();
    }
    @Override
    public final Dimension getDim() {
        return view.getDim();
    }
    @Override
    public final Image getViewImage() {
        return this.view.getImage();
    }

    @Override
    public final void run(){
        while (!Thread.interrupted() && !Field.getInstance().getCompleted() && alive) {
            // Move
            DIRECTION direction = this.enemyDirection();
            do {
                if (judgeLimit(direction)) {
                    Position position = Field.getInstance().getPits().get(this.position.index() + direction.getOffset()).getPosition();
                    synchronized (position) {
                        if (position != null && position.getHolder() == null) {
                            new Move(this, this.position, position, Field.getInstance()).action();
                            break;
                        } else if (position != null && position.getHolder() != null
                                && position.getHolder().getCamp() != this.camp) {
                            new Attack(this, position.getHolder(), Field.getInstance()).action();
                            break;
                        }
                    }
                }
                direction = randomDirection();
            } while (alive);
            Random rand = new Random();
            try {
                Thread.sleep(rand.nextInt(1000));
                Field.getInstance().repaint();
                Field.getInstance().judgeCompleted();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    enum CAMP {
       HULUWA(true), YAOJING(false);
        private boolean _bool;

        private CAMP(boolean _bool){
            this._bool = _bool;
        }
        public boolean getCAMP() {
            return this._bool;
        }
    }

    enum DIRECTION {
        UP(-Field.FIELD_SIZE_OF_X), DOWN(Field.FIELD_SIZE_OF_X),
        LEFT(-1), RIGHT(1),
        UPLEFT(-Field.FIELD_SIZE_OF_X-1), UPRIGHT(-Field.FIELD_SIZE_OF_X+1),
        DOWNLEFT(Field.FIELD_SIZE_OF_X-1), DOWNRIGHT(Field.FIELD_SIZE_OF_X+1);

        private int offset;    // 规定颜色的优先级
        private DIRECTION(int offset){
            this.offset = offset;
        }
        public int getOffset() {
            return offset;
        }
    }
}
