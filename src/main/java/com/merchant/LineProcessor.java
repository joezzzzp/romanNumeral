package com.merchant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * @author created by zzz at 2019/9/16 15:08
 **/

class LineProcessor {

    private static final Logger logger = LogManager.getLogger(LineProcessor.class);

    private static final Class<?>[] validInterfaces = {Extractor.class, QuestionHandler.class};

    private final List<QuestionHandler> handlers = new LinkedList<>();

    private final List<Extractor> extractors = new ArrayList<>();

    void addComponent(Class<?> clazz) {
        Class inter = findValidInterface(clazz.getInterfaces());
        if (inter == null) {
            return;
        }
        if (inter == Extractor.class) {
            addExtractor(clazz, extractors);
        }
        if (inter == QuestionHandler.class) {
            addHandler(clazz, handlers);
        }
    }

    void init() {
        if (!extractors.isEmpty()) {
            extractors.sort(Comparator.comparingInt(Extractor::getOrder));
        }
    }

    void process(String line) {
        if (line.endsWith("?")) {
            handleQuestion(line);
        } else {
            updateExtractor(line);
        }
    }

    /**
     * Find needed interface from all interfaces a class implemented
     * @param allInterfaces all interfaces a class implemented
     * @return needed interface or null
     */
    private Class<?> findValidInterface(Class<?>[] allInterfaces) {
        for (Class<?> i : allInterfaces) {
            for (Class<?> vi : validInterfaces) {
                if (i == vi) {
                    return i;
                }
            }
        }
        return null;
    }

    private void addExtractor(Class<?> clazz, List<Extractor> extractors) {
        Extractor instance;
        try {
            instance = (Extractor) clazz.newInstance();
            extractors.add(instance);
        } catch (InstantiationException e) {
            logger.error("can't initialize class", e);
        } catch (IllegalAccessException e) {
            logger.error("illegal access", e);
        }
    }

    private void addHandler(Class<?> clazz, List<QuestionHandler> handlers) {
        QuestionHandler instance;
        try {
            instance = (QuestionHandler) clazz.newInstance();
            handlers.add(instance);
        } catch (InstantiationException e) {
            logger.error("can't initialize class", e);
        } catch (IllegalAccessException e) {
            logger.error("illegal access", e);
        }
    }

    private void handleQuestion(String question) {
        QuestionHandler handler = handlers.stream().filter(item -> item.supportQuestion(question))
                .findFirst().orElse(null);
        if (null == handler) {
            throw new UnintelligibleException();
        }
        QuestionInfo questionInfo = new QuestionInfo(question);
        for (Extractor extractor : extractors) {
            if (extractor.canExtract(questionInfo)) {
                extractor.extract(questionInfo);
            }
        }
        String answer = handler.handleQuestionInfo(questionInfo);
        logger.info(answer);
    }

    private void updateExtractor(String rule) {
        QuestionInfo questionInfo = new QuestionInfo(rule);
        for (Extractor extractor : extractors) {
            if (extractor.canExtract(questionInfo)) {
                extractor.extract(questionInfo);
            }
            if (extractor.canLearn(questionInfo)) {
                extractor.learn(questionInfo);
            }
        }
    }
}
