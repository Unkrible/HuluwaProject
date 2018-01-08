package Model.Creature;

import Field.Field;
import Model.Position.Pit;
import Model.Position.Position;
import Model.Position.PositionXY;
import UIView.View;

import java.util.ArrayList;
import java.util.Random;

public class Loulou extends Creature {

    private static int curNum = 0 ;    // 喽啰个数
    public final static int allNum = 8;

    protected static String dieSrc = "LoulouDie.png";

    private int no;

    public Loulou() {
        alive = true;
        no = curNum++;
        camp = CAMP.YAOJING;
        name = "喽喽"+no;
        view = new View(Pit.PIT_WIDTH, Pit.PIT_HEIGHT);
        view.loadImage("喽喽.png");
    }

    //MARK: Creature
    @Override
    protected void loadDieImage(){
        view.loadImage(Loulou.dieSrc);
    }
    @Override
    protected Position getEnemyPos(){
        int index = 0;
        int num = 1;
        ArrayList<Creature> enemies = Field.getInstance().getHuluwas();
        for(Creature enemy : enemies){
            if(enemy.getAlive()) {
                index += enemy.getPosition().index();
                ++num;
            }
        }
        PositionXY positionXY = new PositionXY(index/num, Field.FIELD_SIZE_OF_X);
        return positionXY;
    }

    @Override
    public String toString() {
        return name + "Position: " + position.toString() + " ;";
    }
}
