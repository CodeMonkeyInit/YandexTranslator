package deniskuliev.yandextranslator.yandexTranslatorApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by deniskuliev on 25.03.17.
 */

public class YandexTranslatorResponseParser
{
    public static String parseResponse(String apiJSONResponse)
    {
        String translatedText = "";

        if (apiJSONResponse == null)
        {
            return translatedText;
        }

        try
        {
            JSONObject jsonObject;
            JSONArray jsonTextArray;


            jsonObject = new JSONObject(apiJSONResponse);
            jsonTextArray = jsonObject.getJSONArray("text");

            for (int i = 0; i < jsonTextArray.length(); i++)
            {
                translatedText += jsonTextArray.optString(i);
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        return translatedText;
    }
}
