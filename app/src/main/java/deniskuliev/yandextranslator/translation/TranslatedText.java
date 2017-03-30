package deniskuliev.yandextranslator.translation;

/**
 * Created by deniskuliev on 28.03.17.
 */

public class TranslatedText
{
    private static int textsTranslated;
    public String originalText;
    public String translatedText;
    public String translatedLanguages;
    public boolean isFavorite;
    private int id;

    public TranslatedText(String originalText, String translatedText, String tranlatedLanguages)
    {
        textsTranslated++;
        id = textsTranslated;
        this.originalText = originalText;
        this.translatedText = translatedText;
        this.translatedLanguages = tranlatedLanguages;
    }

    public TranslatedText()
    {
        textsTranslated++;
        id = textsTranslated;
    }

    @Override
    public boolean equals(Object obj)
    {
        if ((obj != null) && (obj instanceof TranslatedText))
        {
            TranslatedText translatedTextInstance = (TranslatedText) obj;
            if (id == translatedTextInstance.id)
            {
                return true;
            }
        }
        return false;
    }
}
