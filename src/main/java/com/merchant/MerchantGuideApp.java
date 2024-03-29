package com.merchant;

import com.merchant.components.FileLoader;
import com.merchant.components.LineProcessor;
import com.merchant.components.ProcessorInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;

public class MerchantGuideApp {

    private static final Logger logger = LogManager.getLogger(MerchantGuideApp.class);

    public static void main(String[] args) {
        BufferedReader reader = new FileLoader().loadFile(args).getBufferReader();
        LineProcessor processor = new LineProcessor();
        new ProcessorInitializer(MerchantGuideApp.class.getClassLoader(), "com.merchant")
                .init(processor);
        if (reader != null) {
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    handleLine(processor, line);
                }
            } catch (IOException e) {
                logger.error("can't read file", e);
            }
        }
    }

    private static void handleLine(LineProcessor processor, String line) {
        try {
            processor.process(line.trim());
        } catch (UnintelligibleException e) {
            logger.info(e.getMessage());
        }
    }
}
