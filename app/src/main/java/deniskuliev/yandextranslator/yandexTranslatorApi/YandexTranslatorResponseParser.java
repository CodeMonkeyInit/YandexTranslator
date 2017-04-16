package deniskuliev.yandextranslator.yandexTranslatorApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import deniskuliev.yandextranslator.translationModel.TranslatedText;

/**
 * Created by deniskuliev on 25.03.17.
 */

public class YandexTranslatorResponseParser
{
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
                    .replaceFirst("^.*?(?=-)", language);

            return true;

        }
        catch (JSONException e)
        {
            e.printStackTrace();

            return false;
        }
    }

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
