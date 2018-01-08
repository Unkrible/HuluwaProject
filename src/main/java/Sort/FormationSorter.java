package Sort;

import java.util.ArrayList;
import java.util.List;

import Field.Field;
import Model.Creature.Creature;
import Model.Position.Position;

public interface FormationSorter {
    public void formation(Field field, ArrayList<Creature> creatures, ArrayList<Position> formations);
    public void formation(Field field, ArrayList<Creature> creatures, ArrayList<Position> formations, Position startPos);
}

