package deniskuliev.yandextranslator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import deniskuliev.yandextranslator.translationModel.TranslatedText;
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
        TranslatedText translatedText = new TranslatedText();

        YandexTranslatorResponseParser.parseTranslationResponse(apiResponse, translatedText);

        assertEquals(translatedText.translated, expectedParsedText);
    }

    @Test
    public void parseTranlationDetectionResponse_LanguageString()
    {
        String apiResponse = "{    \"code\": 200,    \"lang\": \"en\"}";
        String expectedLanguageString = "en-ru";

        TranslatedText translatedText = new TranslatedText("Привет мир!", null, "frbjbjbjh-ru");

        YandexTranslatorResponseParser.parseLanguageDetectionResponse(apiResponse, translatedText);

        assertEquals(translatedText.translationLanguages, expectedLanguageString);
    }
}