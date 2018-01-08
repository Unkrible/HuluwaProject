package Sort;

import java.util.ArrayList;
import java.util.List;

import Field.Field;
import Model.Creature.Creature;
import Model.Position.*;

public class EmbattleFormationSorter implements FormationSorter {
    @Override
    public void formation(Field field, ArrayList<Creature> creatures,  ArrayList<Position> formations) {
        formation(field, creatures, formations, null);
    }

    @Override
    public void formation(Field field, ArrayList<Creature> creatures, ArrayList<Position> formations, Position startPos){
        if(field == null)
            return;
        ArrayList<Pit> pits =field.getPits();
        int fromIndex = 0, toIndex = 0;
        int baseIndex = 0;
        if(startPos == null)
            baseIndex = creatures.get(0).getPosition().index();
        else
            baseIndex = startPos.index();
        for(int i=0; i<formations.size(); i++){
            if(i>=creatures.size())
                break;
            Position position = formations.get(i);
            Creature creature = creatures.get(i);
            fromIndex = creature.getPosition().index();
            toIndex = baseIndex + position.index();
            if(toIndex < pits.size()) {
                pits.get(fromIndex).getPosition().setHolder(pits.get(toIndex).getPosition().getHolder());
                pits.get(toIndex).getPosition().setHolder(creature);
            }
        }
    }
}
