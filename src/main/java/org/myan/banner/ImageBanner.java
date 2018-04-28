package org.myan.banner;

import org.myan.banner.ansi.AnsiBackground;
import org.myan.banner.ansi.AnsiColor;
import org.myan.banner.ansi.AnsiColors;
import org.myan.banner.ansi.AnsiOutput;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by myan on 2018/4/26.
 * 代码取自SpringBoot，图片加载方式简化了，没有使用SpringBoot中更快速的Frame，直接使用ImageIO读取出BufferedImage
 *
 * @author myan
 */
public class ImageBanner implements BannerIntf {

    private static final double[] RGB_WEIGHT = new double[]{0.2126D, 0.7152D, 0.0722D};
    private static final char[] PIXEL = new char[]{' ', '.', '*', ':', 'o', '&', '8', '#', '@'};
    private static final int LUMINANCE_INCREMENT = 10;
    private static final int LUMINANCE_START;
    static {
        LUMINANCE_START = 10 * PIXEL.length;
    }

    private static final int MAX_IMG_WIDTH_DEFAULT = 75;

        public String printBanner(File imgFile, PrintStream out) {
        String headlessProperty = System.getProperty("java.awt.headless");
        String banner = "";
        try {
            System.setProperty("java.awt.headless", "true");
            BufferedImage bufferedImage = ImageIO.read(imgFile);
            //springboot中对图片进行resize，考虑到图片很大的情况下，将图片等比例缩小，与Springboot处理不同
            BufferedImage resized = this.resizeImage(bufferedImage);
            printBanner(resized, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (headlessProperty != null) {   //handless属性还原
                System.setProperty("java.awt.headless", headlessProperty);
            }
        }

        return null;
    }

    private BufferedImage resizeImage(BufferedImage srcImage) {
        int width;
        double resizeRatio;
        if (srcImage.getWidth() > MAX_IMG_WIDTH_DEFAULT) {
            width = MAX_IMG_WIDTH_DEFAULT;
            resizeRatio = MAX_IMG_WIDTH_DEFAULT / ((double) srcImage.getWidth());
        }else{
            width = srcImage.getWidth();
            resizeRatio = 1d;
        }
        int height = (int) (Math.ceil(resizeRatio * (double) srcImage.getHeight())); //取稍大的临近数字

        Image image = srcImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage resized1 = new BufferedImage(image.getWidth(null),
                image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        resized1.getGraphics().drawImage(image, 0, 0, null);

        return resized1;
    }

    private void printBanner(BufferedImage image, PrintStream out) {
        //sprintboot中默认值为2
        int margin = 2;

        AnsiBackground background = AnsiBackground.DEFAULT;
        out.print(AnsiOutput.encode(AnsiColor.DEFAULT));
        out.print(AnsiOutput.encode(background));
        out.println();
        out.println();
        AnsiColor lastColor = AnsiColor.DEFAULT;

        for (int y = 0; y < image.getHeight(); ++y) {
            int x;
            for (x = 0; x < margin; ++x) {
                out.print(" ");
            }

            for (x = 0; x < image.getWidth(); ++x) {
                Color color = new Color(image.getRGB(x, y), false);
                AnsiColor ansiColor = AnsiColors.getClosest(color);
                if (ansiColor != lastColor) {
                    out.print(AnsiOutput.encode(ansiColor));
                    lastColor = ansiColor;
                }

                //invert直接复制为false，不反转背景色
                out.print(this.getAsciiPixel(color, false));
            }

            out.println();
        }

        out.print(AnsiOutput.encode(AnsiColor.DEFAULT));
        out.print(AnsiOutput.encode(AnsiBackground.DEFAULT));
        out.println();
    }

    private char getAsciiPixel(Color color, boolean dark) {
        double luminance = (double) this.getLuminance(color, dark);

        for (int i = 0; i < PIXEL.length; ++i) {
            if (luminance >= (double) (LUMINANCE_START - i * 10)) {
                return PIXEL[i];
            }
        }

        return PIXEL[PIXEL.length - 1];
    }

    private int getLuminance(Color color, boolean inverse) {
        double luminance = 0.0D;
        luminance += this.getLuminance(color.getRed(), inverse, RGB_WEIGHT[0]);
        luminance += this.getLuminance(color.getGreen(), inverse, RGB_WEIGHT[1]);
        luminance += this.getLuminance(color.getBlue(), inverse, RGB_WEIGHT[2]);
        return (int) Math.ceil(luminance / 255.0D * 100.0D);
    }

    private double getLuminance(int component, boolean inverse, double weight) {
        return (double) (inverse ? 255 - component : component) * weight;
    }

    public static class Frames {
        private final BufferedImage image;
        private final int delayTime;

        public Frames(BufferedImage image, int delayTime) {
            this.image = image;
            this.delayTime = delayTime;
        }

        public BufferedImage getImage() {
            return image;
        }

        public int getDelayTime() {
            return delayTime;
        }
    }
}
