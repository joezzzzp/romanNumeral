package com.merchant;

/**
 * @author created by zzz at 2019/9/2 16:44
 **/

public interface Extractor {

    /**
     * Check can this translator learn the rule or not
     * @param rule input rule
     * @return can learn or not
     */
    default boolean canLearn(String rule) {
        return false;
    }

    /**
     * Update translator by input rule
     * @param questionInfo constructed question data
     */
    default void learn(QuestionInfo questionInfo) {

    }

    /**
     * Extract info from question
     * @param questionInfo constructed question data
     */
    void extract(QuestionInfo questionInfo);
}
