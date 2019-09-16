package com.merchant.extractors;

import com.merchant.QuestionInfo;
import com.merchant.RomanNumeral;

import java.util.HashMap;
import java.util.Map;

/**
 * @author created by zzz at 2019/9/2 18:14
 **/
@SuppressWarnings("unused")
public class WordMappingExtractor implements Extractor {

    private Map<String, RomanNumeral> wordMapper = new HashMap<>(16);

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public boolean canLearn(QuestionInfo questionInfo) {
        return questionInfo.getSource().split(" ").length == 3;
    }

    @Override
    public void learn(QuestionInfo questionInfo) {
        String rule = questionInfo.getSource();
        String[] ss = rule.split(" ");
        wordMapper.put(ss[0], RomanNumeral.parse(ss[2]));
    }

    @Override
    public boolean canExtract(QuestionInfo questionInfo) {
        return questionInfo.getSource() != null;
    }

    @Override
    public void extract(QuestionInfo questionInfo) {
        String[] ss = questionInfo.getSource().split(" ");
        for (String s : ss) {
            if (wordMapper.containsKey(s)) {
                questionInfo.getOriginalWords().add(s);
                questionInfo.getRomansFromWords().add(wordMapper.get(s));
            }
        }
    }
}
