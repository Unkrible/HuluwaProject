package Field;

import org.junit.Test;

import java.awt.event.KeyEvent;

import static org.junit.Assert.*;

public class HighlightReplayTest {

    @Test
    public void testNullReplay() {
        Field.getInstance().keyCodeHandle(KeyEvent.VK_R);
    }
}