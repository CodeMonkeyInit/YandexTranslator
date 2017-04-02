package deniskuliev.yandextranslator.translation;

import com.google.common.collect.Lists;

import java.util.ArrayList;

import deniskuliev.yandextranslator.fragments.historyAndFavorites.FakeHistory;

/**
 * Created by deniskuliev on 30.03.17.
 */

public class TranslationHistory extends TranslationList
{
    private static TranslationHistory instance;

    private TranslationHistory()
    {
        super();
    }

    public static TranslationHistory getInstance()
    {
        if (instance == null)
        {
            instance = new TranslationHistory();
        }

        return instance;
    }

    @Override
    protected void initializeLists()
    {
        translatedTexts = new ArrayList<>();

        //TODO remove after test
        translatedTexts = FakeHistory.getFakeHistory();
        reversedTranslatedTexts = Lists.reverse(translatedTexts);
    }
}

