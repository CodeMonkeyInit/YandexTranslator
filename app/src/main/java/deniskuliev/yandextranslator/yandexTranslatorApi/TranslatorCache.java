package deniskuliev.yandextranslator.yandexTranslatorApi;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Created by deniskuliev on 22.03.17.
 */

public class TranslatorCache
{
    private final static int CACHE_SIZE = 10_000;
    private final static long CACHE_EXPIRATION_DAYS = 60;
    private static TranslatorCache instance;
    private Cache<String, String> _translationsCache;

    private TranslatorCache()
    {
        _translationsCache = CacheBuilder.newBuilder()
                .maximumSize(CACHE_SIZE)
                .expireAfterAccess(CACHE_EXPIRATION_DAYS, TimeUnit.DAYS)
                .build();
    }

    public static TranslatorCache getInstance()
    {
        if (instance == null)
        {
            instance = new TranslatorCache();
        }

        return instance;
    }

    public String getIfPresent(String text, String translationLanguages)
    {
        return _translationsCache.getIfPresent(text + translationLanguages);
    }

    public void add(String text, String translatedText, String translationLanguages)
    {
        _translationsCache.put(text + translationLanguages, translatedText);
    }
}
