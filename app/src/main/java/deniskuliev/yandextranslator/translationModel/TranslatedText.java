package deniskuliev.yandextranslator.translationModel;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by deniskuliev on 28.03.17.
 */
@DatabaseTable(tableName = "History")
public class TranslatedText
{
    @DatabaseField()
    public String original;
    @DatabaseField()
    public String translated;
    @DatabaseField()
    public String translationLanguages;
    @DatabaseField()
    public boolean isFavorite;
    @DatabaseField
    public boolean isInHistory;
    @DatabaseField(generatedId = true)
    protected int id;

    public TranslatedText(String originalText, String translatedText, String tranlatedLanguages)
    {
        this.original = originalText;
        this.translated = translatedText;
        this.translationLanguages = tranlatedLanguages;
    }

    public TranslatedText()
    {
    }

    @Override
    public int hashCode()
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(original);
        stringBuilder.append(translated);
        stringBuilder.append(translationLanguages);

        return stringBuilder.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if ((obj != null) && (obj instanceof TranslatedText))
        {
            TranslatedText translatedTextInstance = (TranslatedText) obj;

            if (hashCode() == translatedTextInstance.hashCode())
            {
                return true;
            }
        }
        return false;
    }

    public boolean fieldsEqual(TranslatedText translatedText)
    {
        return (translatedText != null)
                && original.equals(translatedText.original)
                && translated.equals(translatedText.translated)
                && translationLanguages.equals(translatedText.translationLanguages);
    }

    public String getOriginalLanguage()
    {
        if (translationLanguages != null)
        {
            return translationLanguages.replaceAll("-(.*)", "");
        }
        else
        {
            return null;
        }
    }
}
