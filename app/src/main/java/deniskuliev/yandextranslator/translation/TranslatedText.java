package deniskuliev.yandextranslator.translation;

/**
 * Created by deniskuliev on 28.03.17.
 */

public class TranslatedText
{
    private static int textsTranslated;
    public String original;
    public String translated;
    public String translationLanguages;
    public boolean isFavorite;
    private int id;

    public TranslatedText(String originalText, String translatedText, String tranlatedLanguages)
    {
        textsTranslated++;
        id = textsTranslated;
        this.original = originalText;
        this.translated = translatedText;
        this.translationLanguages = tranlatedLanguages;
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

    public boolean fieldsEqual(TranslatedText translatedText)
    {
        return (translatedText != null) && original.equals(translatedText.original)
                && translated.equals(translatedText.translated)
                && translationLanguages.equals(translatedText.translationLanguages);

    }
}
