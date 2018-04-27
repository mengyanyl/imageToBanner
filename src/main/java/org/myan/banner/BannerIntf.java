package org.myan.banner;

import java.io.File;
import java.io.PrintStream;

/**
 * Created by myan on 2018/4/26.
 */
public interface BannerIntf {
    String printBanner(File imgFile, PrintStream out);
}
