package com.merchant;

import com.merchant.extractors.DigitExtractor;
import com.merchant.extractors.MetalOrDirtExtractor;
import com.merchant.extractors.RomanNumeralExtractor;
import com.merchant.handlers.HowManyQuestionHandler;
import com.merchant.handlers.HowMuchQuestionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MerchantGuideApp {

  private static final Logger logger = LogManager.getLogger(MerchantGuideApp.class);

  private static final List<QuestionHandler> handlers = new LinkedList<>();

  private static final Map<Class<? extends Extractor>, Extractor> extractors = new HashMap<>(8);

  public static void main(String[] args) {
    try(BufferedReader reader = new BufferedReader(new FileReader(getFile(args)))) {
      mainProcess(reader);
    } catch (IOException e) {
      logger.error("can't read file", e);
    } catch (Exception e) {
      logger.error("unknown error", e);
    }
  }

  private static File getFile(String... args) {
    File file = null;
    if (args.length > 0) {
      file = new File(args[0]);
    } else {
      URL url = MerchantGuideApp.class.getClassLoader().getResource("test.txt");
      if (url != null) {
        file = new File(url.getFile());
      }
    }
    return file;
  }

  private static void mainProcess(BufferedReader reader) throws IOException {
    initComponents("com.zzz.merchant");
    String line;
    while ((line = reader.readLine()) != null) {
      line = line.trim();
      try {
        if (line.endsWith("?")) {
          handleQuestion(line);
        } else {
          updateTranslator(line);
        }
      } catch (UnintelligibleException e) {
        logger.info(e.getMessage());
      }
    }
  }

  private static void initComponents(String packageName) {
    handlers.add(new HowMuchQuestionHandler());
    handlers.add(new HowManyQuestionHandler());
    extractors.put(RomanNumeralExtractor.class, new RomanNumeralExtractor());
    extractors.put(DigitExtractor.class, new DigitExtractor());
    extractors.put(MetalOrDirtExtractor.class, new MetalOrDirtExtractor());
  }

  private static void handleQuestion(String question) {
    QuestionHandler handler = findHandler(question);
    ExtractorChain extractorChain = new ExtractorChain(question, handler.getClass(), extractors);
    extractorChain.extract();
    String answer = handler.handleQuestionInfo(extractorChain.getQuestionInfo());
    logger.info(answer);
  }

  private static void updateTranslator(String rule) {
    extractors.forEach((clazz, translator) -> {
      if (translator.canLearn(rule)) {
        ExtractorChain extractorChain = new ExtractorChain(rule, translator.getClass(), extractors);
        extractorChain.extract();
        translator.learn(extractorChain.getQuestionInfo());
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
