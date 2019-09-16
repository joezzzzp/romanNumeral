package com.merchant.handlers;

import com.merchant.QuestionInfo;

/**
 * @author created by zzz at 2019/9/2 18:57
 **/
@SuppressWarnings("unused")
public class HowManyQuestionHandler implements QuestionHandler {

    @Override
    public boolean supportQuestion(String question) {
        return question.startsWith("how many");
    }

    @Override
    public String handleQuestionInfo(QuestionInfo info) {
        float ret = info.getCredits() * info.getArabicFromRomans();
        String word = String.join(" ", info.getOriginalWords());
        return String.format("%s %s is %.0f Credits", word, info.getMetalOrDirt(), ret);
    }
}
