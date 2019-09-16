package com.merchant.extractors;

import com.merchant.QuestionInfo;

/**
 * @author created by zzz at 2019/9/2 16:44
 **/

public interface Extractor {

    /**
     * The order of this extractor in extractor list
     * @return order
     */
    int getOrder();

    /**
     * Check can this translator learn the rule or not
     * @param questionInfo input rule
     * @return can learn or not
     */
    default boolean canLearn(QuestionInfo questionInfo) {
        return false;
    }

    /**
     * Update translator by input rule
     * @param questionInfo constructed question data
     */
    default void learn(QuestionInfo questionInfo) {

    }

    /**
     * Present this extractor can extract info from this question
     * @param questionInfo constructed question data
     * @return can extract
     */
    boolean canExtract(QuestionInfo questionInfo);

    /**
     * Extract info from question
     * @param questionInfo constructed question data
     */
    default void extract(QuestionInfo questionInfo) {

    }
}
