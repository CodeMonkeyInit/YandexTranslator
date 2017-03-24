package deniskuliev.yandextranslator.yandexTranslatorApi;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

/**
 * Created by deniskuliev on 22.03.17.
 */

public class TranslatorCache
{
    private final static int CACHE_SIZE = 10_000;
    private final static long CACHE_EXPIRATION_DAYS = 60;

    private Cache<String, String> _translationsCache;
    private static TranslatorCache instance;

    private TranslatorCache()
    {
        _translationsCache = CacheBuilder.newBuilder()
                .maximumSize(CACHE_SIZE)
                .expireAfterAccess(CACHE_EXPIRATION_DAYS, TimeUnit.DAYS)
                .build();
    }

    public String getIfPresent(String text)
    {
         return _translationsCache.getIfPresent(text);
    }

    public void add(String text, String translatedText)
    {
        _translationsCache.put(text, translatedText);
    }

    public static TranslatorCache getInstance()
    {
        if (instance == null)
        {
            instance = new TranslatorCache();
        }

        return instance;
    }
}
