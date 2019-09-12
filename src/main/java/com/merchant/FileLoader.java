package com.merchant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

/**
 * @author created by zzz at 2019/9/12 17:48
 **/

class FileLoader {

    private static final Logger logger = LogManager.getLogger(FileLoader.class);

    private File file;

    FileLoader loadFile(String... args) {
        if (args.length == 0) {
            URL url = MerchantGuideApp.class.getClassLoader().getResource("test.txt");
            if (url != null) {
                file = new File(url.getFile());
            }
        } else {
            file = new File(args[0]);
        }
        return this;
    }

    BufferedReader getBufferReader() {
        try {
            return new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
             logger.error("can't load file");
        }
        return null;
    }
}
