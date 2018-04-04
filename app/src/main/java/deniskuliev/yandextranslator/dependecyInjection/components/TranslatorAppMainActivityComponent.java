package deniskuliev.yandextranslator.dependecyInjection.components;

import javax.inject.Singleton;

import dagger.Component;
import deniskuliev.yandextranslator.dependecyInjection.modules.YandexApiModule;
import deniskuliev.yandextranslator.fragments.translation.TranslationTask;


@Singleton
@Component(modules = {YandexApiModule.class})
public interface TranslatorAppMainActivityComponent
{
    @SuppressWarnings({"EmptyMethod", "UnusedParameters"})
    void injectInto(TranslationTask translationTask);
}