package Sort;

import java.util.ArrayList;

import Model.Creature.Creature;

public class BubbleSorter implements Sorter{
    @Override
    public void sort(ArrayList<Creature> creatures){
        Creature creature = null;
        Creature lhs = null;
        Creature rhs = null;
        for(int i=creatures.size()-1;i>0;i--){
            lhs = creatures.get(i);
            for(int j=0;j<i;j++){
                rhs = creatures.get(j);
            }
        }
    }
}
