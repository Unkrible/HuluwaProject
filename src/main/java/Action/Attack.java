package Action;

import Model.Creature.Creature;
import UIView.Animate;
import UIView.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Attack implements Action, Animate {
    Creature lhs,rhs;
    JPanel panel;
    long num;
    long i;
    Timer timer;

    public Attack(Creature lhs, Creature rhs, JPanel panel){
        this.lhs = lhs;
        this.rhs = rhs;
        this.panel = panel;
    }

    public void action(){
        if(lhs.getCamp() == rhs.getCamp())
            return;
        if(!lhs.getAlive() || !rhs.getAlive())
            return;
        synchronized (lhs){
            Random rand = new Random();
            animationWithDuration(500);
            int winner = rand.nextInt(2);
            lhs.setAlive(winner == 0);
        }
    }

    public void animationWithDuration(final long millis){
        num = millis/View.animationFps;
        i=0;
        lhs.getView().loadImage(lhs.getName()+"攻击.png");
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (i != num+1) {
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
        lhs.getView().loadImage(lhs.getName()+".png");
    }
}
