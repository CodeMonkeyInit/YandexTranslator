package deniskuliev.yandextranslator.yandexTranslatorApi;

public interface TranslatorApi
{
    String getOriginalLanguage(String text, String hint);

    String getTranslationPost(String text, String language);
}
