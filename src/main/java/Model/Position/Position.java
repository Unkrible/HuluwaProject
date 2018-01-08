package Model.Position;

import Model.Creature.Creature;

// 1-Dimensional position
public class Position {
    protected int x;
    protected Creature holder;

    public Position(int x){
        this.x = x;
    }

    public int index(){
        return x;
    }

    public int getX(){
        return this.x;
    }

    public Creature getHolder(){
        return this.holder;
    }

    public void setHolder(Creature holder){
        if(holder == null){
            this.holder = null;
            return;
        }
        this.holder = holder;
        holder.setPosition(this);
    }

    public void clearHolder(){
        if(holder == null)
            return;
        holder = null;
        return;
    }

    @Override
    public String toString(){
        return String.valueOf(x);
    }
}
