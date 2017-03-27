package deniskuliev.yandextranslator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import deniskuliev.yandextranslator.yandexTranslatorApi.YandexTranslatorResponseParser;

import static junit.framework.Assert.assertEquals;

/**
 * Created by deniskuliev on 25.03.17.
 */

@RunWith(RobolectricTestRunner.class)
public class YandexTranslatorResponseParserUnitTest
{
    @Test
    public void parseResponse_parsedText()
    {
        String apiResponse = "{\"code\":200,\"lang\":\"en-ru\",\"text\":[\"Здравствуй, Мир!\"]}";
        String expectedParsedText = "Здравствуй, Мир!";
        String parsedText = YandexTranslatorResponseParser.parseResponse(apiResponse);

        assertEquals(parsedText, expectedParsedText);
    }
}