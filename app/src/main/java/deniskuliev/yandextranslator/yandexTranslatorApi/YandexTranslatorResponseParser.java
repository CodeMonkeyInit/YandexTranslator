package deniskuliev.yandextranslator.yandexTranslatorApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import deniskuliev.yandextranslator.translationModel.TranslatedText;

public class YandexTranslatorResponseParser
{
    @SuppressWarnings("WeakerAccess")
    public final static String REGEXP_REPLACE_ORIGINAL_LANGUAGE = "^.*?(?=-)";

    @SuppressWarnings("UnusedReturnValue")
    public static boolean parseLanguageDetectionResponse(String apiJSONResponse, TranslatedText translatedText)
    {
        if (apiJSONResponse == null)
        {
            return false;
        }

        try
        {
            JSONObject translationJSONObject;
            String language;

            translationJSONObject = new JSONObject(apiJSONResponse);
            language = translationJSONObject.getString("lang");

            translatedText.translationLanguages = translatedText.translationLanguages
                    .replaceFirst(REGEXP_REPLACE_ORIGINAL_LANGUAGE, language);

            return true;

        }
        catch (JSONException e)
        {
            e.printStackTrace();

            return false;
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    public static boolean parseTranslationResponse(String apiJSONResponse, TranslatedText translatedText)
    {
        translatedText.translated = "";

        if (apiJSONResponse == null)
        {
            translatedText.translated = null;
            return false;
        }

        try
        {
            JSONObject translationJSONObject;
            JSONArray translationJSONArray;

            translationJSONObject = new JSONObject(apiJSONResponse);
            translationJSONArray = translationJSONObject.getJSONArray("text");

            translatedText.translationLanguages = translationJSONObject.getString("lang");

            for (int i = 0; i < translationJSONArray.length(); i++)
            {
                translatedText.translated += translationJSONArray.optString(i);
            }

            return true;

        } catch (JSONException e)
        {
            e.printStackTrace();

            return false;
        }
    }
}
