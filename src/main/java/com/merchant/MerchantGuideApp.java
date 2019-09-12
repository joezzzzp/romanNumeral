package com.merchant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MerchantGuideApp {

  private static final Logger logger = LogManager.getLogger(MerchantGuideApp.class);

  private static final List<QuestionHandler> handlers = new LinkedList<>();

  private static final Map<Class<? extends Extractor>, Extractor> extractors = new HashMap<>(8);

  public static void main(String[] args) {
    BufferedReader reader = new FileLoader().loadFile(args).getBufferReader();
    new ComponentInitializer("com.merchant").initComponents(extractors, handlers);
    if (reader != null) {
      try {
        mainProcess(reader);
      } catch (IOException e) {
        logger.error("can't read file", e);
      }
    }
  }

  private static void mainProcess(BufferedReader reader) throws IOException {
    String line;
    while ((line = reader.readLine()) != null) {
      line = line.trim();
      try {
        if (line.endsWith("?")) {
          handleQuestion(line);
        } else {
          updateExtractor(line);
        }
      } catch (UnintelligibleException e) {
        logger.info(e.getMessage());
      }
    }
  }

  private static void handleQuestion(String question) {
    QuestionHandler handler = findHandler(question);
    ExtractorChain extractorChain = new ExtractorChain(question, handler.getClass(), extractors);
    extractorChain.extract();
    String answer = handler.handleQuestionInfo(extractorChain.getQuestionInfo());
    logger.info(answer);
  }

  private static void updateExtractor(String rule) {
    extractors.forEach((clazz, extractor) -> {
      if (extractor.canLearn(rule)) {
        ExtractorChain extractorChain = new ExtractorChain(rule, extractor.getClass(), extractors);
        extractorChain.extract();
        extractor.learn(extractorChain.getQuestionInfo());
      }
    });
  }

  private static QuestionHandler findHandler(String question){
    for (QuestionHandler handler : handlers) {
      if (handler.supportQuestion(question)) {
        return handler;
      }
    }
    throw new UnintelligibleException();
  }
}
