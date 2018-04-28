package org.myan.banner;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class Tester {

    @Test
    public void testApplication(){
        Application app = new Application();
        assertNotNull(app);
    }

    @Test
    public void testFileName(){
        File file = new File("test-img.jpg");
        assertTrue(file.getName().substring(0, file.getName().lastIndexOf(".")).equals("test-img"));
    }


}
