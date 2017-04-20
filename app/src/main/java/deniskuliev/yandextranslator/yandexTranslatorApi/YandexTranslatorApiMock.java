package deniskuliev.yandextranslator.yandexTranslatorApi;

public class YandexTranslatorApiMock implements TranslatorApi
{
    private final static String RUSSIAN_TEXT = "тест";
    private final static String ENGLISH_TEXT = "test";

    @Override
    public String getOriginalLanguage(String text, String hint)
    {
        switch (text)
        {
            case RUSSIAN_TEXT:
                return "{    \"code\": 200,    \"lang\": \"ru\"}";

            default:
                return "{    \"code\": 200,    \"lang\": \"en\"}";
        }
    }

    @Override
    public String getTranslationPost(String text, String language)
    {
        switch (text)
        {
            case ENGLISH_TEXT:
                return String.format("{\"code\":200,\"lang\":\"%s\",\"text\":[\"%s\"]}", language,
                                     RUSSIAN_TEXT);

            default:
                return String
                        .format("{\"code\":200,\"lang\":\"%s\",\"text\":[\"%s\"]}", language, text);
        }
    }
}
