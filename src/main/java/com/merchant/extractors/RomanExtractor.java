package com.merchant.extractors;

import com.merchant.Extractor;
import com.merchant.QuestionInfo;
import com.merchant.RomanNumeral;
import com.merchant.UnintelligibleException;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author created by zzz at 2019/9/3 14:14
 **/

public class RomanExtractor implements Extractor {

    @Override
    public void extract(QuestionInfo questionInfo) {
        int value = questionInfo.getDigit();
        if (value > 3999 || value < 1) {
            throw new UnintelligibleException("Invalid number");
        }
        Deque<Integer> digits = new ArrayDeque<>();
        int remainValue = value;
        while (remainValue > 0) {
            digits.push(remainValue % 10);
            remainValue /= 10;
        }
        questionInfo.getTranslatedSymbols().clear();
        while (!digits.isEmpty()) {
            int length = digits.size();
            questionInfo.getTranslatedSymbols().addAll(getRomanNumeral(digits.pop(), length));
        }
    }

    private static final RomanNumeral[][] rules = {
            {RomanNumeral.I, RomanNumeral.V, RomanNumeral.X},
            {RomanNumeral.X, RomanNumeral.L, RomanNumeral.C},
            {RomanNumeral.C, RomanNumeral.D, RomanNumeral.M},
            {RomanNumeral.M, null, null}};

    private List<RomanNumeral> getRomanNumeral(int digit, int position) {
        List<RomanNumeral> ret = new LinkedList<>();
        RomanNumeral[] rule = rules[position - 1];
        if (digit > 0 && digit < 4) {
            for (int i = 0; i < digit; i++) {
                ret.add(rule[0]);
            }
        }
        if (digit == 4) {
            ret.add(rule[0]);
            ret.add(rule[1]);
        }
        if (digit == 5) {
            ret.add(rule[1]);
        }
        if (digit == 6) {
            ret.add(rule[1]);
            ret.add(rule[0]);
        }
        if (digit > 6 && digit < 9) {
            ret.add(rule[1]);
            for (int i = 0; i < digit - 5; i++) {
                ret.add(rule[0]);
            }
        }
        if (digit == 9) {
            ret.add(rule[0]);
            ret.add(rule[2]);
        }
        return ret;
    }
}
