package com.merchant.handlers;

import com.merchant.QuestionInfo;

/**
 * @author created by zzz at 2019/9/2 17:25:
 */
public interface QuestionHandler {

  /**
   * Whether this handler can handle the input question
   * @param question Question String
   * @return Can handle or not
   */
  boolean supportQuestion(String question);

  /**
   * Use extracted information to calculate result and print
   * @param info extracted information from the question
   * @return final answer
   */
  String handleQuestionInfo(QuestionInfo info);
}
