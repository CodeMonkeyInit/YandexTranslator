package deniskuliev.yandextranslator.dependecyInjection.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import deniskuliev.yandextranslator.yandexTranslatorApi.TranslatorApi;
import deniskuliev.yandextranslator.yandexTranslatorApi.YandexTranslatorApiMock;

@Module
public class YandexApiMockModule
{
    @Provides
    @Singleton
    TranslatorApi provideYandexTranslatorApi()
    {
        return new YandexTranslatorApiMock();
    }
}
