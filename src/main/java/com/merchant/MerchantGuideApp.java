package com.merchant;

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

  private static final String CLASS_FILE_EXTEND_NAME = ".class";

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
    initComponents();
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

  /**
   * Load all Extractors and QuestionHandlers
   */
  private static void initComponents() {
    Class<?>[] validInterfaces = {Extractor.class, QuestionHandler.class};
    String packageName = "com.merchant";
    String packagePath = packageName.replace('.', '/');
    List<String> classFiles = new LinkedList<>();
    ClassLoader cl = MerchantGuideApp.class.getClassLoader();
    getAllClass(cl, packagePath, classFiles);
    for (String path : classFiles) {
      try {
        Class<?> clazz = cl.loadClass(path);
        Class<?> inter = findValidInterface(clazz.getInterfaces(), validInterfaces);
        if (inter == null) {
          continue;
        }
        if (inter == Extractor.class) {
          Extractor instance = (Extractor) clazz.newInstance();
          extractors.put(instance.getClass(), instance);
        }
        if (inter == QuestionHandler.class) {
          QuestionHandler instance = (QuestionHandler) clazz.newInstance();
          handlers.add(instance);
        }
      } catch (ClassNotFoundException e) {
        logger.error("can't find class", e);
      } catch (IllegalAccessException e) {
        logger.error("illegal access", e);
      } catch (InstantiationException e) {
        logger.error("can't create instance", e);
      }
    }
  }

  /**
   * Find all class in path
   * @param classLoader classLoader
   * @param path package path
   * @param classList found classes
   */
  private static void getAllClass(ClassLoader classLoader, String path, List<String> classList) {
    URL url = classLoader.getResource(path);
    if (url == null) {
      return;
    }
    if (url.getFile() == null) {
      return;
    }
    File file = new File(url.getFile());
    if (file.isFile()) {
      if (file.getName().endsWith(CLASS_FILE_EXTEND_NAME)) {
        String fullPath = file.getPath();
        String neededPath = fullPath.substring(fullPath.indexOf(path.replace('/', File.separatorChar)));
        String finalClassName =
                neededPath.replace(CLASS_FILE_EXTEND_NAME, "").replace(File.separatorChar, '.');
        classList.add(finalClassName);
      }
    } else if (file.isDirectory() && file.list() != null) {
      File[] files = file.listFiles(pathname -> pathname.isDirectory() || pathname.getName().endsWith(CLASS_FILE_EXTEND_NAME));
      if (files != null) {
        for (File item : files) {
          getAllClass(classLoader, path + "/" + item.getName(), classList);
        }
      }
    }
  }

  /**
   * Find needed interface from all interfaces a class implemented
   * @param allInterfaces all interfaces a class implemented
   * @param validInterfaces needed interfaces
   * @return needed interface or null
   */
  private static Class<?> findValidInterface(Class<?>[] allInterfaces, Class<?>... validInterfaces) {
    for (Class<?> i : allInterfaces) {
      for (Class<?> vi : validInterfaces) {
        if (i == vi) {
          return i;
        }
      }
    }
    return null;
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
