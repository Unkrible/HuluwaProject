package Model.Creature;

import Field.Field;
import Model.Position.Pit;
import Model.Position.Position;
import Model.Position.PositionXY;
import UIView.View;

import java.util.ArrayList;

public class Yeye extends Creature{
    protected static String dieSrc = "HuluwaDie.png";

    public Yeye() {
        alive = true;
        camp = CAMP.HULUWA;
        name = "爷爷";
        view = new View(Pit.PIT_WIDTH, Pit.PIT_HEIGHT);
        view.loadImage("爷爷.png");
    }

    //MARK: Creature
    @Override
    protected void loadDieImage(){
        view.loadImage(Loulou.dieSrc);
    }
    @Override
    protected Position getEnemyPos(){
        return new PositionXY(0, Field.FIELD_SIZE_OF_X);
    }

    @Override
    public String toString() {
        return name + "Position: " + position.toString() + " ;";
    }
}
