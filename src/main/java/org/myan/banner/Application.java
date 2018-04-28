package org.myan.banner;

import java.awt.*;
import java.io.*;

public class Application {
    public static void main(String[] args) {
        if (args.length < 1){  //必须有一个图片路径
            System.err.println("the param must be filepath");
            System.exit(1);
        }
        File imgFile = new File(args[0]);
        if (!imgFile.isFile() || !imgFile.exists()){  //判断文件是否存在
            System.err.println("image file dosnt exist or is a real file");
            System.exit(1);
        }
        String bannerFileName = imgFile.getName().substring(0, imgFile.getName().lastIndexOf("."));
        ImageBanner banner = new ImageBanner();
        try(PrintStream out = new PrintStream(new FileOutputStream(bannerFileName + ".txt"), true)) {
            banner.printBanner(imgFile, out);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
