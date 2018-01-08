package Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Field.Field;
import Model.Creature.Creature;
import Model.Position.Pit;
import Model.Position.Position;

public class RandomSorter implements Sorter {

    @Override
    public void sort(ArrayList<Creature> creatures){
        ArrayList<Pit> pits = Field.getInstance().getPits();
        Random random = new Random();
        for(int i=0; i<creatures.size(); i++){
            int sel = random.nextInt(creatures.size()-i);
            Creature creature = creatures.get(i);
            Position position = creatures.get(sel).getPosition();
            pits.get(creature.getPosition().index()).getPosition().setHolder(creatures.get(sel));
            pits.get(position.index()).getPosition().setHolder(creature);
        }
    }
}
