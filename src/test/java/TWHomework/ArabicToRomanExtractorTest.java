package TWHomework;

import com.merchant.QuestionInfo;
import com.merchant.extractors.RomanToArabicExtractor;
import com.merchant.extractors.ArabicToRomanExtractor;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author created by zzz at 2019/9/3 14:49
 **/

public class ArabicToRomanExtractorTest {

    @Test
    public void testDigitRomanConvert() {
        ArabicToRomanExtractor arabicToRomanExtractor = new ArabicToRomanExtractor();
        RomanToArabicExtractor romanToArabicExtractor = new RomanToArabicExtractor();
        QuestionInfo questionInfo = new QuestionInfo("");
        for (int i = 1; i < 4000; i++) {
            questionInfo.setArabic(i);
            arabicToRomanExtractor.extract(questionInfo);
            questionInfo.setRomansFromWords(questionInfo.getRomansFromArabic());
            romanToArabicExtractor.extract(questionInfo);
            System.out.println(i + " " + questionInfo.getArabicFromRomans());
            Assert.assertEquals(String.format("Expected: %d Actual: %d", i, questionInfo.getArabic()),
                    i, (int) questionInfo.getArabicFromRomans());
        }
    }
}
