package TWHomework;

import com.merchant.QuestionInfo;
import com.merchant.extractors.DigitExtractor;
import com.merchant.extractors.RomanExtractor;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author created by zzz at 2019/9/3 14:49
 **/

public class RomanExtractorTest {

    @Test
    public void testDigitRomanConvert() {
        RomanExtractor romanExtractor = new RomanExtractor();
        DigitExtractor digitExtractor = new DigitExtractor();
        QuestionInfo questionInfo = new QuestionInfo("");
        for (int i = 1; i < 4000; i++) {
            questionInfo.setDigit(i);
            romanExtractor.extract(questionInfo);
            digitExtractor.extract(questionInfo);
            System.out.println(i + " " + questionInfo.getDigit());
            Assert.assertEquals(String.format("Expected: %d Actual: %d", i, questionInfo.getDigit()), i, questionInfo.getDigit());
        }
    }
}
