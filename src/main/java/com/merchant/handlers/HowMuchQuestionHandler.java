package com.merchant.handlers;

import com.merchant.QuestionInfo;
import com.merchant.extractors.DigitExtractor;
import com.merchant.extractors.RomanNumeralExtractor;
import com.merchant.PreExtractors;
import com.merchant.QuestionHandler;

/**
 * @author created by zzz at 2019/9/2 18:51
 **/
@PreExtractors({RomanNumeralExtractor.class, DigitExtractor.class})
public class HowMuchQuestionHandler implements QuestionHandler {

    @Override
    public boolean supportQuestion(String question) {
        return question.startsWith("how much is");
    }

    @Override
    public String handleQuestionInfo(QuestionInfo info) {
        String words = String.join(" ", info.getOriginalSymbols());
        return words + " is " + info.getDigit();
    }
}
