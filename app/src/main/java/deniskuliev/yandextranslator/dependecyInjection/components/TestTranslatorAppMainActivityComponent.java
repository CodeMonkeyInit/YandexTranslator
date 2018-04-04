package deniskuliev.yandextranslator.dependecyInjection.components;

import javax.inject.Singleton;

import dagger.Component;
import deniskuliev.yandextranslator.dependecyInjection.modules.YandexApiMockModule;
import deniskuliev.yandextranslator.fragments.translation.TranslationTask;

@Singleton
@Component(modules = {YandexApiMockModule.class})
@SuppressWarnings("WeakerAccess")
public interface TestTranslatorAppMainActivityComponent extends TranslatorAppMainActivityComponent
{
    void injectInto(TranslationTask translationTask);
}