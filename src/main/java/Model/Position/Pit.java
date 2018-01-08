package Model.Position;

import Model.Creature.Creature;

// 结合
public class Pit {
    public static final int PIT_WIDTH = 100;
    public static final int PIT_HEIGHT = 100;

    private Position position;

    public Pit(Position position){
        super();
        this.position = position;
    }

    public Position getPosition(){
        return position;
    }

    public int getX() {
        return position.getX() * Pit.PIT_WIDTH;
    }

    public int getY() {
        if(position instanceof PositionXY)
            return ((PositionXY) position).getY() * Pit.PIT_HEIGHT;
        return 0;
    }
}
