package deniskuliev.yandextranslator.fragments.historyAndFavorites;

import java.util.ArrayList;
import java.util.List;

import deniskuliev.yandextranslator.translationModel.TranslatedText;

/**
 * Created by deniskuliev on 28.03.17.
 */

public class FakeHistory
{
    private final static int itemsCount = 10;
    private static List<TranslatedText> fakeFavourites;

    public static List<TranslatedText> generateFakeFavorites()
    {
        ArrayList<TranslatedText> fakeTranslatdText = new ArrayList<>();
        TranslatedText favourite;

        for (int i = 0; i < itemsCount; i++)
        {
            favourite = new TranslatedText("Тест", "Test", "ru-en");
            favourite.isFavorite = true;

            fakeTranslatdText.add(favourite);
        }

        return fakeTranslatdText;
    }

    public static List<TranslatedText> getFakeHistory()
    {
        List<TranslatedText> fakeTranslatdText = new ArrayList<>();


        for (int i = 0; i < itemsCount; i++)
        {
            fakeTranslatdText.add(new TranslatedText("Тест", "Test", "ru-en"));
        }

        return fakeTranslatdText;
    }
}
