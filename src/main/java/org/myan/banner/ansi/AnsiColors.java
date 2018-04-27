package org.myan.banner.ansi;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by myan on 2018/4/27.
 */
public final class AnsiColors {
    private static final Map<AnsiColor, LabColor> ANSI_COLOR_MAP;

    private AnsiColors() {
    }

    public static AnsiColor getClosest(Color color) {
        return getClosest(new AnsiColors.LabColor(color));
    }

    private static AnsiColor getClosest(AnsiColors.LabColor color) {
        AnsiColor result = null;
        double resultDistance = 3.4028234663852886E38D;
        Iterator var4 = ANSI_COLOR_MAP.entrySet().iterator();

        while(true) {
            Map.Entry entry;
            double distance;
            do {
                if(!var4.hasNext()) {
                    return result;
                }

                entry = (Map.Entry)var4.next();
                distance = color.getDistance((AnsiColors.LabColor)entry.getValue());
            } while(result != null && distance >= resultDistance);

            resultDistance = distance;
            result = (AnsiColor)entry.getKey();
        }
    }

    static {
        LinkedHashMap colorMap = new LinkedHashMap();
        colorMap.put(AnsiColor.BLACK, new AnsiColors.LabColor(Integer.valueOf(0)));
        colorMap.put(AnsiColor.RED, new AnsiColors.LabColor(Integer.valueOf(11141120)));
        colorMap.put(AnsiColor.GREEN, new AnsiColors.LabColor(Integer.valueOf('ꨀ')));
        colorMap.put(AnsiColor.YELLOW, new AnsiColors.LabColor(Integer.valueOf(11162880)));
        colorMap.put(AnsiColor.BLUE, new AnsiColors.LabColor(Integer.valueOf(170)));
        colorMap.put(AnsiColor.MAGENTA, new AnsiColors.LabColor(Integer.valueOf(11141290)));
        colorMap.put(AnsiColor.CYAN, new AnsiColors.LabColor(Integer.valueOf('ꪪ')));
        colorMap.put(AnsiColor.WHITE, new AnsiColors.LabColor(Integer.valueOf(11184810)));
        colorMap.put(AnsiColor.BRIGHT_BLACK, new AnsiColors.LabColor(Integer.valueOf(5592405)));
        colorMap.put(AnsiColor.BRIGHT_RED, new AnsiColors.LabColor(Integer.valueOf(16733525)));
        colorMap.put(AnsiColor.BRIGHT_GREEN, new AnsiColors.LabColor(Integer.valueOf(5635840)));
        colorMap.put(AnsiColor.BRIGHT_YELLOW, new AnsiColors.LabColor(Integer.valueOf(16777045)));
        colorMap.put(AnsiColor.BRIGHT_BLUE, new AnsiColors.LabColor(Integer.valueOf(5592575)));
        colorMap.put(AnsiColor.BRIGHT_MAGENTA, new AnsiColors.LabColor(Integer.valueOf(16733695)));
        colorMap.put(AnsiColor.BRIGHT_CYAN, new AnsiColors.LabColor(Integer.valueOf(5636095)));
        colorMap.put(AnsiColor.BRIGHT_WHITE, new AnsiColors.LabColor(Integer.valueOf(16777215)));
        ANSI_COLOR_MAP = Collections.unmodifiableMap(colorMap);
    }

    private static final class LabColor {
        private static final ColorSpace XYZ_COLOR_SPACE = ColorSpace.getInstance(1001);
        private final double l;
        private final double a;
        private final double b;

        LabColor(Integer rgb) {
            this(rgb == null?(Color)null:new Color(rgb.intValue()));
        }

        LabColor(Color color) {
//            System.err.println(color.toString() + "Color must not be null");
            float[] lab = this.fromXyz(color.getColorComponents(XYZ_COLOR_SPACE, (float[])null));
            this.l = (double)lab[0];
            this.a = (double)lab[1];
            this.b = (double)lab[2];
        }

        private float[] fromXyz(float[] xyz) {
            return this.fromXyz(xyz[0], xyz[1], xyz[2]);
        }

        private float[] fromXyz(float x, float y, float z) {
            double l = (this.f((double)y) - 16.0D) * 116.0D;
            double a = (this.f((double)x) - this.f((double)y)) * 500.0D;
            double b = (this.f((double)y) - this.f((double)z)) * 200.0D;
            return new float[]{(float)l, (float)a, (float)b};
        }

        private double f(double t) {
            return t > 0.008856451679035631D?Math.cbrt(t):0.3333333333333333D * Math.pow(4.833333333333333D, 2.0D) * t + 0.13793103448275862D;
        }

        public double getDistance(AnsiColors.LabColor other) {
            double c1 = Math.sqrt(this.a * this.a + this.b * this.b);
            double deltaC = c1 - Math.sqrt(other.a * other.a + other.b * other.b);
            double deltaA = this.a - other.a;
            double deltaB = this.b - other.b;
            double deltaH = Math.sqrt(Math.max(0.0D, deltaA * deltaA + deltaB * deltaB - deltaC * deltaC));
            return Math.sqrt(Math.max(0.0D, Math.pow((this.l - other.l) / 1.0D, 2.0D) + Math.pow(deltaC / (1.0D + 0.045D * c1), 2.0D) + Math.pow(deltaH / (1.0D + 0.015D * c1), 2.0D)));
        }
    }
}

