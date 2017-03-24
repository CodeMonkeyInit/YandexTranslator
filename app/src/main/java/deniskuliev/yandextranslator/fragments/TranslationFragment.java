package deniskuliev.yandextranslator.fragments;


import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import deniskuliev.yandextranslator.R;
import deniskuliev.yandextranslator.yandexTranslatorApi.YandexTranslator;

public class TranslationFragment extends Fragment
{
    private void attachTranslationHandler()
    {
        EditText originalText = (EditText) getView().findViewById(R.id.original_text);

        originalText.setHorizontallyScrolling(false);
        originalText.setMaxLines(Integer.MAX_VALUE);

        originalText.addTextChangedListener(
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
                        if (currentTask != null)
                        {
                            currentTask.cancel(true);
                        }

                        currentTask = new TranslateTask();

                        currentTask.execute(charSequence.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable)
                    {
                    }
                }
        );
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        attachTranslationHandler();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_translation, container, false);
    }

    private class TranslateTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... text)
        {
            String response = null;
            YandexTranslator translator = new YandexTranslator();
            if (!isCancelled())
            {
                response = translator.getTranslationPost(text[0], "en-ru");
            }

            return response;
        }

        @Override
        protected void onPostExecute(String jsonResponse)
        {
            TextView translated = (TextView) getView().findViewById(R.id.translated_text);
            String translatedText = "";

            try
            {
                JSONObject jsonObject;
                JSONArray jsonTextArray;

                if (jsonResponse != null)
                {
                    jsonObject = new JSONObject(jsonResponse);
                    jsonTextArray = jsonObject.getJSONArray("text");

                    for (int i = 0; i < jsonTextArray.length(); i++)
                    {
                        translatedText += jsonTextArray.optString(i);
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), getString(R.string.connection_problem), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            translated.setText(translatedText);
        }
    }
}