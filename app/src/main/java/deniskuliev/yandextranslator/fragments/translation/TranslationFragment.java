package deniskuliev.yandextranslator.fragments.translation;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import deniskuliev.yandextranslator.fragments.settings.SettingsFragment;
import deniskuliev.yandextranslator.translationModel.TranslateLanguages;
import deniskuliev.yandextranslator.translationModel.TranslatedText;
import deniskuliev.yandextranslator.translationModel.TranslationFavorites;
import deniskuliev.yandextranslator.translationModel.TranslationHistory;

public class TranslationFragment extends Fragment
{
    public final static String PREFERENCES_TAG = "translation-fragment";
    private final static String ORIGINAL_LANGUAGE_TAG = "original-language";
    private final static String TRANSLATION_LANGUAGE_TAG = "translation-language";
    public Spinner originalLanguage;
    public Spinner translationLanguage;
    private volatile TranslationTask translationTask;

    private String getTranslationLanguages()
    {
        String originalTextLanguage = TranslateLanguages
                .getLanguageStringByCode(
                        originalLanguage.getSelectedItemPosition());

        String translationTextLanguage = TranslateLanguages
                .getLanguageStringByCode(
                        translationLanguage.getSelectedItemPosition());

        return String.format("%s-%s", originalTextLanguage, translationTextLanguage);
    }

    private void translateInBackground()
    {
        View view = getView();

        String translationLanguages = getTranslationLanguages();

        EditText originalText = (EditText) view.findViewById(R.id.original_text);

        TextView translatedText = (TextView) view.findViewById(R.id.translated_text);

        ImageButton translationFavoritesButton = (ImageButton) getView()
                .findViewById(R.id.translation_favorite_button);

        boolean autoDetectLanguage = getActivity()
                .getSharedPreferences(SettingsFragment.SETTINGS_TAG, Context.MODE_PRIVATE)
                .getBoolean(SettingsFragment.LANGUAGE_AUTODETECT_TAG, false);

        if (translationTask != null)
        {
            translationTask.cancel(true);
        }

        translationTask = new TranslationTask(getActivity(),
                                              translatedText,
                                              originalLanguage,
                                              translationFavoritesButton,
                                              autoDetectLanguage);

        translationTask.execute(originalText.getText().toString(), translationLanguages);
    }

    void attachRemoveTextButtonHandler()
    {
        ImageButton removeTextButton = (ImageButton) getView()
                .findViewById(R.id.remove_text_button);

        removeTextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String EMPTY_STRING = "";
                View view = getView();
                EditText originalTextEditable = (EditText) view
                        .findViewById(R.id.original_text);

                originalTextEditable.setText(EMPTY_STRING);

                translateInBackground();
            }
        });
    }

    private boolean translationTaskIsFinished()
    {
        return translationTask != null
                && translationTask.getStatus() == AsyncTask.Status.FINISHED
                && translationTask.translatedText != null;

    }

    private void restoreFavoritesButton()
    {
        ImageButton favoritesButton = (ImageButton) getView()
                .findViewById(R.id.translation_favorite_button);

        if (translationTaskIsFinished() && translationTask.translatedText.isFavorite)
        {
            favoritesButton.setImageResource(R.drawable.ic_favorites_history_activated);
        }
    }

    private void attachFavoritesButtonHandler()
    {
        ImageButton favoritesButton = (ImageButton) getView()
                .findViewById(R.id.translation_favorite_button);

        favoritesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ImageButton favoritesButton = (ImageButton) getView()
                        .findViewById(R.id.translation_favorite_button);
                TranslationFavorites translationFavorites = TranslationFavorites.getInstance();

                if (translationTaskIsFinished() && !translationTask.translatedText.translated
                        .isEmpty())
                {
                    TranslatedText translatedText = translationTask.translatedText;

                    if (translatedText.isFavorite)
                    {
                        int lastTranslatedTextPosition = translationFavorites
                                .indexOf(translatedText);

                        if (lastTranslatedTextPosition != -1)
                        {
                            translationFavorites.remove(lastTranslatedTextPosition);
                        }

                        translatedText.isFavorite = false;

                        favoritesButton.setImageResource(R.drawable.ic_favorites_history);
                        Log.d("Favorites button", "unset attachFavoritesButtonHandler()");
                    }
                    else
                    {
                        TranslationHistory translationHistory = TranslationHistory.getInstance();

                        if (!translatedText.isInHistory)
                        {
                            translationHistory.add(translatedText);
                        }

                        translatedText.isFavorite = true;

                        translationFavorites.add(translatedText);

                        favoritesButton.setImageResource(R.drawable.ic_favorites_history_activated);
                        Log.d("Favorites button", "set attachFavoritesButtonHandler()");
                    }
                }
            }
        });
    }

    private Bundle getSpinnerLanguagesBundle()
    {
        Bundle spinnerSelection = new Bundle();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFERENCES_TAG,
                                                                                 Context.MODE_PRIVATE);
        int originalLanguageSelection;
        int translatedLanguageSelection;

        originalLanguageSelection = sharedPreferences
                .getInt(ORIGINAL_LANGUAGE_TAG, TranslateLanguages.ENGLISH_LANGUAGE);
        translatedLanguageSelection = sharedPreferences
                .getInt(TRANSLATION_LANGUAGE_TAG, TranslateLanguages.RUSSIAN_LANGUAGE);

        spinnerSelection.putInt(ORIGINAL_LANGUAGE_TAG, originalLanguageSelection);
        spinnerSelection.putInt(TRANSLATION_LANGUAGE_TAG, translatedLanguageSelection);

        return spinnerSelection;
    }

    public void saveSpinnerValuesToPreferences()
    {
        SharedPreferences.Editor preferencesEditor = getActivity()
                .getSharedPreferences(PREFERENCES_TAG, Context.MODE_PRIVATE).edit();
        int originalLanguageSelection = originalLanguage.getSelectedItemPosition();
        int translatedLangugaeSelection = translationLanguage.getSelectedItemPosition();

        preferencesEditor.putInt(ORIGINAL_LANGUAGE_TAG, originalLanguageSelection);
        preferencesEditor.putInt(TRANSLATION_LANGUAGE_TAG, translatedLangugaeSelection);

        preferencesEditor.apply();
    }

    private void initializeSpinnersValues()
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.translation_languages,
                                    android.R.layout.simple_spinner_item);
        Bundle spinnerSelection = getSpinnerLanguagesBundle();

        int originalLanguageSelection = spinnerSelection.getInt(ORIGINAL_LANGUAGE_TAG);
        int translatedLanguageSelection = spinnerSelection.getInt(TRANSLATION_LANGUAGE_TAG);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        originalLanguage.setAdapter(adapter);
        translationLanguage.setAdapter(adapter);

        originalLanguage.setSelection(originalLanguageSelection);
        translationLanguage.setSelection(translatedLanguageSelection);
    }

    private void attachSpinnerHandlers()
    {
        originalLanguage
                .setOnItemSelectedListener(new OnLanguageSelectedListener(translationLanguage));
        translationLanguage
                .setOnItemSelectedListener(new OnLanguageSelectedListener(originalLanguage));
    }

    private void setOriginalEditTextParameters()
    {
        EditText originalText = (EditText) getView().findViewById(R.id.original_text);

        originalText.setHorizontallyScrolling(false);
        originalText.setMaxLines(Integer.MAX_VALUE);
    }

    private void attachTranslationHandler()
    {
        final EditText originalText = (EditText) getView().findViewById(R.id.original_text);

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
                        if (getActivity().getCurrentFocus() == originalText)
                        {
                            translateInBackground();
                        }
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
                    AddToHistoryTask addToHistoryTask = new AddToHistoryTask();

                    addToHistoryTask.execute(translationTask);
                }
            }
        });
    }

    private void restoreTranslatedText()
    {
        TextView translatedTextView = (TextView) getView().findViewById(R.id.translated_text);
        String translatedText;

        if (translationTask != null && translationTask.translatedText != null)
        {
            translatedText = translationTask.translatedText.translated;
            translatedTextView.setText(translatedText);
        }
    }

    private void attachTranslatedWithYandexTextHandler()
    {
        TextView translatedWithYandexTextView = (TextView) getView()
                .findViewById(R.id.translated_with_yanex_text);

        translatedWithYandexTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent openYandexTranslatorURL = new Intent(Intent.ACTION_VIEW, Uri.parse(
                        "http://translate.yandex.ru/"));

                startActivity(openYandexTranslatorURL);
            }
        });

    }

    private void swapLanguages()
    {
        int tempLanguageCode = originalLanguage.getSelectedItemPosition();

        originalLanguage.setSelection(translationLanguage.getSelectedItemPosition());
        translationLanguage.setSelection(tempLanguageCode);
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

        ImageButton swapLanguageButton = (ImageButton) view
                .findViewById(R.id.swap_translation_languages);


        originalLanguage = (Spinner) view.findViewById(R.id.original_text_language);
        translationLanguage = (Spinner) view.findViewById(R.id.translated_text_language);

        swapLanguageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                swapLanguages();
            }
        });

        setOriginalEditTextParameters();
        attachSpinnerHandlers();
        attachRemoveTextButtonHandler();
        attachTranslationHandler();
        attachFavoritesButtonHandler();
        attachTranslatedWithYandexTextHandler();
        initializeSpinnersValues();
        restoreTranslatedText();
        restoreFavoritesButton();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_translation, container, false);

        return view;
    }

    @Override
    public void onStop()
    {
        super.onStop();
        saveSpinnerValuesToPreferences();
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
            }

            //TODO rewrite this "brilliant" solution
            if (getActivity().getCurrentFocus() != null)
            {
                translateInBackground();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
        }
    }
}