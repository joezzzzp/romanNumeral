package com.merchant.handlers;

import com.merchant.PreExtractors;
import com.merchant.QuestionHandler;
import com.merchant.QuestionInfo;
import com.merchant.extractors.DigitExtractor;
import com.merchant.extractors.MetalOrDirtExtractor;
import com.merchant.extractors.RomanNumeralExtractor;

/**
 * @author zzz
 * @date 2019/9/2 18:57
 **/
@PreExtractors({RomanNumeralExtractor.class, DigitExtractor.class, MetalOrDirtExtractor.class})
public class HowManyQuestionHandler implements QuestionHandler {

    @Override
    public boolean supportQuestion(String question) {
        return question.startsWith("how many");
    }

    @Override
    public String handleQuestionInfo(QuestionInfo info) {
        float ret = info.getCredits() * info.getDigit();
        String word = String.join(" ", info.getOriginalSymbols());
        return String.format("%s %s is %.0f Credits", word, info.getMetalOrDirt(), ret);
    }
}
