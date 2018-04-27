package org.myan.banner;

import java.awt.*;
import java.io.*;

public class Application {
    public static void main(String[] args) {
        if (args.length < 1){
            System.err.println("the param must be filepath");
            System.exit(1);
        }
        File imgFile = new File(args[0]);
        if (!imgFile.isFile() || !imgFile.exists()){
            System.err.println("image file dosnt exist or is a real file");
        }
        ImageBanner banner = new ImageBanner();
        try(PrintStream out = new PrintStream(new FileOutputStream("test-banner.txt"), true)) {
            banner.printBanner(imgFile, out);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
