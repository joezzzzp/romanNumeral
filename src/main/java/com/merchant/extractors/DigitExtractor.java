package com.merchant.extractors;

import com.merchant.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zzz
 * @date 2019/9/2 18:26
 **/
@PreExtractors(RomanNumeralExtractor.class)
public class DigitExtractor implements Extractor {

    @Override
    public void extract(QuestionInfo questionInfo) {
        List<RomanNumeral> numbers = questionInfo.getTranslatedSymbols();
        int value = 0;
        int i = 0;
        RomanNumeral successionRoman = RomanNumeral.NONE;
        int successionTimes = 0;
        while (i < numbers.size()) {
            RomanNumeral current = numbers.get(i);
            if (current.compare(successionRoman) != 0) {
                successionRoman = current;
                successionTimes = 1;
            } else {
                successionTimes++;
            }
            if (successionTimes > successionRoman.getMaxRepeatTimes()) {
                String word = numbers.stream().map(romanNumeral -> romanNumeral.symbol).collect(Collectors.joining());
                throw new UnintelligibleException(String.format("\"%s\" is invalid roman numeral", word));
            }
            if (i == numbers.size() - 1) {
                value += current.value;
                break;
            }
            RomanNumeral next = numbers.get(i + 1);
            int temp = current.value;
            if (current.compare(next) < 0 && current.canBeSubtractedBy(next)) {
                temp = next.value - current.value;
                i++;
            }
            value += temp;
            i++;
        }
        questionInfo.setDigit(value);
    }
}
