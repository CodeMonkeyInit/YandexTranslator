package deniskuliev.yandextranslator.translationModel;

/**
 * Created by deniskuliev on 22.03.17.
 */

public class TranslateLanguages
{
    public final static int UNKNOWN_LANGUAGE = -1;

    public final static int ENGLISH_LANGUAGE = 0;
    public final static int RUSSIAN_LANGUAGE = 1;
    public final static int FRENCH_LANGUAGE = 2;
    public final static int GERMAN_LANGUAGE = 3;

    private final static String ENGLISH = "en";
    private final static String RUSSIAN = "ru";
    private final static String FRENCH = "fr";
    private final static String GERMAN = "de";

    public static String getLanguageStringByCode(int code)
    {
        switch (code)
        {
            case ENGLISH_LANGUAGE:
                return ENGLISH;

            case RUSSIAN_LANGUAGE:
                return RUSSIAN;

            case FRENCH_LANGUAGE:
                return FRENCH;

            case GERMAN_LANGUAGE:
                return GERMAN;

            default:
                return null;
        }
    }

    public static int getLangugeCodeByString(String languageString)
    {
        switch (languageString)
        {
            case ENGLISH:
                return ENGLISH_LANGUAGE;

            case RUSSIAN:
                return RUSSIAN_LANGUAGE;

            case FRENCH:
                return FRENCH_LANGUAGE;

            case GERMAN:
                return GERMAN_LANGUAGE;

            default:
                return UNKNOWN_LANGUAGE;
        }
    }
}