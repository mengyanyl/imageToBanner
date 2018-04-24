package org.myan.banner;
import org.junit.Test;

import static org.junit.Assert.*;

public class Tester {

    @Test
    public void testApplication(){
        Application app = new Application();
        assertNotNull(app);
    }
}
