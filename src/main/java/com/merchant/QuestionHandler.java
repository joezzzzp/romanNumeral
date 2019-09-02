package com.merchant;

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