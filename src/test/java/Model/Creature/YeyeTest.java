package Model.Creature;

import org.junit.Test;

import static org.junit.Assert.*;

public class YeyeTest {
    @Test
    public void testYeyeConstructor(){
        Creature yeye = new Yeye();
        yeye.updateView();
    }
}