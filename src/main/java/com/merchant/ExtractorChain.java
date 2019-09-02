package com.merchant;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author zzz
 * @date 2019/9/2 16:05
 **/

public class ExtractorChain {

    private List<Extractor> extractors = new LinkedList<>();

    private QuestionInfo questionInfo;

    public ExtractorChain(String src, Class<?> clazz, Map<Class<? extends Extractor>, Extractor> pool) {
        questionInfo = new QuestionInfo(src);
        PreExtractors ann = clazz.getAnnotation(PreExtractors.class);
        if (ann != null) {
            Class<?>[] classes = ann.value();
            for (Class item : classes) {
                extractors.add(pool.get(item));
            }
        }
    }

    /**
     * Get constructed question data
     * @return Constructed question data
     */
    QuestionInfo getQuestionInfo() {
        return questionInfo;
    }

    /**
     * Invoke all extractors
     */
    void extract() {
        for (Extractor extractor : extractors) {
            extractor.extract(questionInfo);
        }
    }
}
