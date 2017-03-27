package deniskuliev.yandextranslator.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import deniskuliev.yandextranslator.R;
import deniskuliev.yandextranslator.translation.TranslateLanguages;
import deniskuliev.yandextranslator.yandexTranslatorApi.YandexTranslator;
import deniskuliev.yandextranslator.yandexTranslatorApi.YandexTranslatorResponseParser;

public class TranslationFragment extends Fragment
{
    public Spinner _originalLanguage;
    public Spinner _translatedLanguage;

    private volatile TranslateTask translateTask;

    private String getTranslationLanguages()
    {
        String originalTextLanguage = TranslateLanguages
                .getLanguageStringByCode(
                        _originalLanguage.getSelectedItemPosition());

        String translationTextLanguage = TranslateLanguages
                .getLanguageStringByCode(
                        _translatedLanguage.getSelectedItemPosition());

        return String.format("%s-%s", originalTextLanguage, translationTextLanguage);
    }

    private void translateInBackground()
    {
        String translationLanguages = getTranslationLanguages();
        EditText originalText = (EditText) getView().findViewById(R.id.original_text);

        if (translateTask != null)
        {
            translateTask.cancel(true);
        }

        translateTask = new TranslateTask();

        translateTask.execute(originalText.getText().toString(), translationLanguages);
    }

    private void initializeSpinnersValues()
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.translation_languages,
                                    android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        _originalLanguage.setAdapter(adapter);
        _translatedLanguage.setAdapter(adapter);
        //TODO Load From preferences
        _translatedLanguage.setSelection(TranslateLanguages.RUSSIAN_LANGUAGE);
    }

    private void attachSpinnerHandlers()
    {
        _originalLanguage
                .setOnItemSelectedListener(new OnLanguageSelectedListener(_translatedLanguage));
        _translatedLanguage
                .setOnItemSelectedListener(new OnLanguageSelectedListener(_originalLanguage));
    }

    private void attachTranslationHandler()
    {
        EditText originalText = (EditText) getView().findViewById(R.id.original_text);

        originalText.setHorizontallyScrolling(false);
        originalText.setMaxLines(Integer.MAX_VALUE);

        originalText.addTextChangedListener(
                new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
                    {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
                    {
                        translateInBackground();
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
        attachSpinnerHandlers();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_translation, container, false);
    }

    private class OnLanguageSelectedListener implements AdapterView.OnItemSelectedListener
    {
        private int previousSelectedLanguage;
        private Spinner _oppositeSpinner;

        public OnLanguageSelectedListener(Spinner oppositeSpinner)
        {
            _oppositeSpinner = oppositeSpinner;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            int spinnerToWatchSelectedLanguage = _oppositeSpinner.getSelectedItemPosition();

            if (position != previousSelectedLanguage)
            {
                if (position == spinnerToWatchSelectedLanguage)
                {
                    _oppositeSpinner.setSelection(previousSelectedLanguage);
                }
                previousSelectedLanguage = position;
                translateInBackground();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
        }
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