package deniskuliev.yandextranslator.dependecyInjection.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import deniskuliev.yandextranslator.yandexTranslatorApi.TranslatorApi;
import deniskuliev.yandextranslator.yandexTranslatorApi.YandexTranslator;

@Module
public class YandexApiModule
{
    @Provides
    @Singleton
    TranslatorApi provideYandexTranslatorApi()
    {
        return new YandexTranslator();
    }
}
