package Model.Creature;

import Field.Field;
import Model.Position.Pit;
import Model.Position.Position;
import Model.Position.PositionXY;
import UIView.View;

import java.awt.*;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Random;

public class Huluwa extends Creature {

    private static int curNum=0;    // 葫芦娃现个数
    public final static int allNum = 7; // 葫芦娃总个数

    private static String dieSrc = "HuluwaDie.png";

    private SENIORITY seniority;        // 葫芦娃各自序号
    private COLOR color;    // 葫芦娃的颜色

    public Huluwa(){
        alive = true;
        camp = CAMP.HULUWA;
        color = COLOR.values()[curNum];
        seniority = SENIORITY.values()[curNum];
        curNum = (curNum+1)%allNum;
        name = seniority.toString();
        view = new View(Pit.PIT_WIDTH, Pit.PIT_HEIGHT);
        view.loadImage(name+".png");
    }

    //MARK: Creature
    @Override
    protected void loadDieImage(){
        view.loadImage(Huluwa.dieSrc);
    }
    @Override
    protected Position getEnemyPos(){
        //int index = Field.FIELD_SIZE_OF_X * Field.FIELD_SIZE_OF_Y - 1;
        int index = 0;
        int num = 1;
        ArrayList<Creature> enemies = Field.getInstance().getYaojings();
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
        return name + " Color: " + color.toString() + " Position: " + position.toString() + " ;";
    }

    enum COLOR{
        RED(1),ORANGE(2),YELLOW(3),GREEN(4),CYAN(5),BLUE(6),PURPLE(7);

        private int pri;    // 规定颜色的优先级

        private COLOR(int _pri){
            this.pri = _pri;
        }
        public int getPri() {
            return pri;
        }
    }

    enum SENIORITY {
        大娃, 二娃, 三娃, 四娃, 五娃, 六娃, 七娃;
    }
}
