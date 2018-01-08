package Action;

import Field.Field;
import Log.HuluwaLogger;
import Model.Creature.Creature;
import Model.Position.Pit;
import Model.Position.Position;
import Model.Position.PositionXY;
import UIView.Animate;
import UIView.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Move implements Action, Animate{
    PositionXY lhs;
    PositionXY rhs;
    Creature creature;
    JPanel panel;
    long num;
    long i;
    int xConstant;
    int yConstant;
    Timer timer;

    public Move(Creature creature, Position lhs, Position rhs, JPanel panel){
        this.creature = creature;
        this.lhs = (PositionXY)lhs;
        this.rhs = (PositionXY)rhs;
        this.panel = panel;
    }

    public void action(){
        synchronized (creature) {
            HuluwaLogger.getInstance().append(lhs + " " + rhs + "\n");
            lhs.clearHolder();
            rhs.setHolder(creature);
            animationWithDuration(200);
        }
    }

    public void animationWithDuration(final long millis){
        num = millis/View.animationFps;
        i=0;
        xConstant = (rhs.getX() - lhs.getX())*Pit.PIT_WIDTH/(int)num;
        yConstant = (rhs.getY() - lhs.getY())*Pit.PIT_HEIGHT/(int)num;
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (i != num+1) {
                    try {
                        creature.getView().setXY(lhs.getX() * Pit.PIT_WIDTH + xConstant * (int) i,
                                lhs.getY() * Pit.PIT_HEIGHT + yConstant * (int) i);
                    } catch (Exception e){
                        e.printStackTrace();
                        Thread.interrupted();
                    }
                    panel.repaint();
                    ++i;
                } else {
                    timer.stop();
                }
            }
        };
        timer = new Timer(10, taskPerformer);

        timer.start();
        while (timer.isRunning());
    }
}
