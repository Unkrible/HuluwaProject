package Sort;

import Field.Field;
import Model.Creature.Creature;
import Model.Position.Position;

import java.util.ArrayList;

public class Formation {
    public void embattle(String formationName, Field field, ArrayList<Creature> creatures){
        ArrayList<Position> formations = null;
        if((formations = new FormationFromStr().formation(formationName)) == null){
            new RandomSorter().sort(creatures);
        }
        new EmbattleFormationSorter().formation(field, creatures, formations);
    }

    public void embattle(String formationName, Field field, ArrayList<Creature> creatures, Position startPos){
        ArrayList<Position> formations = null;
        if((formations = new FormationFromStr().formation(formationName)) == null || startPos == null){
            new RandomSorter().sort(creatures);
        }
        new EmbattleFormationSorter().formation(field, creatures, formations, startPos);
    }
}
