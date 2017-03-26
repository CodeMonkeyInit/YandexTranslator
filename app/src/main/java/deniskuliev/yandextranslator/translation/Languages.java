package deniskuliev.yandextranslator.translation;

/**
 * Created by deniskuliev on 22.03.17.
 */

public class Languages
{
    private final static String ENGLISH = "en";
    private final static String RUSSIAN = "ru";
    private final static String FRENCH = "fr";
    private final static String GERMAN = "de";

    public static String getLanguageStringByCode(int code)
    {
        switch (code)
        {
            case 0:
                return ENGLISH;

            case 1:
                return RUSSIAN;

            case 2:
                return FRENCH;

            case 3:
                return GERMAN;
            default:
                return null;
        }
    }


}