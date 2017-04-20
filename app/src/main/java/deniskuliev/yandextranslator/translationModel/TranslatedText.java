package deniskuliev.yandextranslator.translationModel;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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
    @SuppressWarnings("unused")
    @DatabaseField(generatedId = true)
    protected int id;

    @SuppressWarnings("SameParameterValue")
    public TranslatedText(String originalText, String translatedText, String translationLanguages)
    {
        this.original = originalText;
        this.translated = translatedText;
        this.translationLanguages = translationLanguages;
    }

    public TranslatedText()
    {
    }

    @Override
    public int hashCode()
    {
        String hashCodeString = String.format("%s%s%s", original, translated, translationLanguages);

        return hashCodeString.hashCode();
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
