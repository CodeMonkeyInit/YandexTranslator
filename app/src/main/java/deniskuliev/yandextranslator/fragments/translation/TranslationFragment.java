package deniskuliev.yandextranslator.fragments.translation;


import android.content.res.Configuration;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import deniskuliev.yandextranslator.R;
import deniskuliev.yandextranslator.translation.TranslateLanguages;
import deniskuliev.yandextranslator.translation.TranslatedText;
import deniskuliev.yandextranslator.translation.TranslationHistory;

public class TranslationFragment extends Fragment
{
    public Spinner originalLanguage;
    public Spinner translatedLanguage;
    private volatile TranslationTask translationTask;
    private TranslationHistory translationHistory;
    private TranslatedText lastTranslatedText;

    public TranslationFragment()
    {
        translationHistory = TranslationHistory.getInstance();
    }

    private String getTranslationLanguages()
    {
        String originalTextLanguage = TranslateLanguages
                .getLanguageStringByCode(
                        originalLanguage.getSelectedItemPosition());

        String translationTextLanguage = TranslateLanguages
                .getLanguageStringByCode(
                        translatedLanguage.getSelectedItemPosition());

        return String.format("%s-%s", originalTextLanguage, translationTextLanguage);
    }

    private void translateInBackground()
    {
        View view = getView();
        String translationLanguages = getTranslationLanguages();
        EditText originalText = (EditText) view.findViewById(R.id.original_text);
        TextView translatedText = (TextView) view.findViewById(R.id.translated_text);

        if (translationTask != null)
        {
            translationTask.cancel(true);
        }

        translationTask = new TranslationTask(getActivity(), translatedText);

        translationTask.execute(originalText.getText().toString(), translationLanguages);
    }

    private void initializeSpinnersValues()
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.translation_languages,
                                    android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        originalLanguage.setAdapter(adapter);
        translatedLanguage.setAdapter(adapter);
        //TODO Load From preferences
        translatedLanguage.setSelection(TranslateLanguages.RUSSIAN_LANGUAGE);
    }

    private void attachSpinnerHandlers()
    {
        originalLanguage
                .setOnItemSelectedListener(new OnLanguageSelectedListener(translatedLanguage));
        translatedLanguage
                .setOnItemSelectedListener(new OnLanguageSelectedListener(originalLanguage));
    }

    private void attachTranslationHandler()
    {
        EditText originalText = (EditText) getView().findViewById(R.id.original_text);

        originalText.setHorizontallyScrolling(false);
        originalText.setMaxLines(Integer.MAX_VALUE);

        originalText.addTextChangedListener(new TextWatcher()
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

        originalText.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (!hasFocus && translationTask != null)
                {
                    Thread thread = new Thread(new AddToHistoryRunnable(translationTask));
                    thread.start();
                }
            }
        });
    }

    private void swapLanguages()
    {
        int tempLanguageCode = originalLanguage.getSelectedItemPosition();

        originalLanguage.setSelection(translatedLanguage.getSelectedItemPosition());
        translatedLanguage.setSelection(tempLanguageCode);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        LinearLayout translationTextFields = (LinearLayout) getView()
                .findViewById(R.id.translation_text_fields);

        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            translationTextFields.setOrientation(LinearLayout.HORIZONTAL);
        }
        else
        {
            translationTextFields.setOrientation(LinearLayout.VERTICAL);
        }
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
        originalLanguage = (Spinner) currentView.findViewById(R.id.original_text_language);
        translatedLanguage = (Spinner) currentView.findViewById(R.id.translated_text_language);

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
}