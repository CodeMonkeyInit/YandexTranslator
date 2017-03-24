package deniskuliev.yandextranslator;

import org.junit.Test;

import deniskuliev.yandextranslator.yandexTranslatorApi.TranslatorCache;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest
{
    @Test
    public void addDataToCache_Data()
    {
        TranslatorCache cache = TranslatorCache.getInstance();
        String key = "key";
        String text = "ключ";
        String anotherKey = "d";
        String anotherText = "д";

        cache.add(key, text);
        cache.add(anotherKey, anotherText);
        cache.add(key, text);

        assertEquals(cache.getIfPresent(key), text);
    }
}