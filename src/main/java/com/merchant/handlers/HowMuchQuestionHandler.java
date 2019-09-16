package com.merchant.handlers;

import com.merchant.QuestionInfo;
import com.merchant.QuestionHandler;

/**
 * @author created by zzz at 2019/9/2 18:51
 **/
@SuppressWarnings("unused")
public class HowMuchQuestionHandler implements QuestionHandler {

    @Override
    public boolean supportQuestion(String question) {
        return question.startsWith("how much is");
    }

    @Override
    public String handleQuestionInfo(QuestionInfo info) {
        String words = String.join(" ", info.getOriginalWords());
        return words + " is " + info.getArabicFromRomans();
    }
}
