package deniskuliev.yandextranslator.fragments;


import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import deniskuliev.yandextranslator.R;
import deniskuliev.yandextranslator.translation.Languages;
import deniskuliev.yandextranslator.yandexTranslatorApi.YandexTranslator;
import deniskuliev.yandextranslator.yandexTranslatorApi.YandexTranslatorResponseParser;

public class TranslationFragment extends Fragment
{
    public Spinner _originalLanguage;
    public Spinner _translatedLanguage;

    private void initializeSpinnersValues()
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.translation_languages,
                                    android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        _originalLanguage.setAdapter(adapter);
        _translatedLanguage.setAdapter(adapter);
        //TODO Load From preferences
        _translatedLanguage.setSelection(1);
    }

    private void attachTranslationHandler()
    {
        EditText textToAttach = (EditText) getView().findViewById(R.id.original_text);

        textToAttach.setHorizontallyScrolling(false);
        textToAttach.setMaxLines(Integer.MAX_VALUE);

        textToAttach.addTextChangedListener(
                new TextWatcher()
                {
                    private volatile TranslateTask currentTask;

                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
                    {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
                    {
                        String originalTextLanguage = Languages
                                .getLanguageStringByCode(
                                        _originalLanguage.getSelectedItemPosition());
                        String translationTextLanguage = Languages
                                .getLanguageStringByCode(
                                        _translatedLanguage.getSelectedItemPosition());

                        String translationLanguages = String
                                .format("%s-%s", originalTextLanguage, translationTextLanguage);


                        if (currentTask != null)
                        {
                            currentTask.cancel(true);
                        }

                        currentTask = new TranslateTask();

                        currentTask.execute(charSequence.toString(), translationLanguages);
                    }

                    @Override
                    public void afterTextChanged(Editable editable)
                    {
                    }
                }
        );
    }

    private void swapLanguages()
    {
        int tempLanguageCode = _originalLanguage.getSelectedItemPosition();

        _originalLanguage.setSelection(_translatedLanguage.getSelectedItemPosition());
        _translatedLanguage.setSelection(tempLanguageCode);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        View currentView = getView();
        ImageButton swapLanguageButton = (ImageButton) currentView
                .findViewById(R.id.swap_translation_languages);

        swapLanguageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                swapLanguages();
            }
        });
        attachTranslationHandler();
        _originalLanguage = (Spinner) currentView.findViewById(R.id.original_text_language);
        _translatedLanguage = (Spinner) currentView.findViewById(R.id.translated_text_language);

        initializeSpinnersValues();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_translation, container, false);
    }

    private class TranslateTask extends AsyncTask<String, Void, String>
    {
        final static String EMPTY_STRING = "";

        final static int ORIGINAL_TEXT = 0;
        final static int TRANSLATION_LANGUAGES = 1;

        @Override
        protected String doInBackground(String... text)
        {
            String response = null;
            YandexTranslator translator = new YandexTranslator();

            if (text[ORIGINAL_TEXT].equals(EMPTY_STRING))
            {
                return EMPTY_STRING;
            }

            if (!isCancelled())
            {
                response = translator
                        .getTranslationPost(text[ORIGINAL_TEXT], text[TRANSLATION_LANGUAGES]);
            }

            return response;
        }

        @Override
        protected void onPostExecute(String jsonResponse)
        {
            TextView translated = (TextView) getView().findViewById(R.id.translated_text);
            String translatedText = "";

            if (jsonResponse != null)
            {
                translatedText = YandexTranslatorResponseParser.parseResponse(jsonResponse);
            }
            else
            {
                Toast.makeText(getActivity(), getString(R.string.connection_problem),
                               Toast.LENGTH_SHORT).show();
            }
            translated.setText(translatedText);
        }
    }
}