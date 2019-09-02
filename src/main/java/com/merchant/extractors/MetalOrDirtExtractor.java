package com.merchant.extractors;

import com.merchant.Extractor;
import com.merchant.QuestionInfo;
import com.merchant.PreExtractors;

import java.util.HashMap;
import java.util.Map;

/**
 *  * @author created by zzz at 2019/9/2 18:32
 **/
@PreExtractors({RomanNumeralExtractor.class, DigitExtractor.class})
public class MetalOrDirtExtractor implements Extractor {

    private Map<String, Float> creditsMapper = new HashMap<>(16);

    @Override
    public boolean canLearn(String rule) {
        return rule.endsWith("Credits");
    }

    @Override
    public void learn(QuestionInfo questionInfo) {
        String[] ss = questionInfo.getSource().split(" ");
        String metalOrDirt = null;
        float value = 0f;
        for (String s : ss) {
            if (s.matches("[A-Z][a-z]*") && !"Credits".equals(s)) {
                metalOrDirt = s;
            }
            if (s.matches("[0-9]*")) {
                value = Float.parseFloat(s);
            }
        }
        if (null != metalOrDirt) {
            creditsMapper.put(metalOrDirt, value / questionInfo.getDigit());
        }
    }

    @Override
    public void extract(QuestionInfo questionInfo) {
        String[] ss = questionInfo.getSource().split(" ");
        for (String s : ss) {
            if (creditsMapper.containsKey(s)) {
                questionInfo.setMetalOrDirt(s);
                questionInfo.setCredits(creditsMapper.get(s));
            }
        }
    }
}
