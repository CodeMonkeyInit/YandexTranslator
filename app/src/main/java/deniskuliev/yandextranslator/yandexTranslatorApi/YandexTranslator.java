package deniskuliev.yandextranslator.yandexTranslatorApi;

import com.google.common.base.Charsets;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class YandexTranslator implements TranslatorApi
{
    private final static String API_LINK = "https://translate.yandex.net/api/v1.5/tr.json/";
    private final static String API_KEY = "trnsl.1.1.20170322T123605Z.d3c47f5acd5771ea.880203db72f4ab703e8a65af7ce33a9e2a0ac456";
    private final static int RESPONSE_OK = 200;
    private static TranslatorCache _translatorCache;

    public YandexTranslator()
    {
        if (_translatorCache == null)
        {
            _translatorCache = TranslatorCache.getInstance();
        }
    }

    private void closeStream(Closeable stream)
    {
        if (stream != null)
        {
            try
            {
                stream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private String getServerResponse(HttpURLConnection connection)
    {
        String response = null;
        Scanner scanner = null;

        try
        {
            if (connection.getResponseCode() == RESPONSE_OK)
            {
                scanner = new Scanner(connection.getInputStream()).useDelimiter("\\A");

                if (scanner.hasNext())
                {
                    response = scanner.next();
                }

            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (scanner != null)
            {
                scanner.close();
            }
        }
        return response;
    }

    private HttpURLConnection sendPostRequestToServer(String apiURLString, String urlParameters)
    {
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;

        URL apiURL = null;

        try
        {
            apiURL = new URL(apiURLString);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        try
        {
            //noinspection ConstantConditions
            connection = (HttpURLConnection) apiURL.openConnection();

            connection.setRequestMethod("POST");

            connection.setDoOutput(true);

            outputStream = new DataOutputStream(connection.getOutputStream());

            outputStream.write(urlParameters.getBytes(Charsets.UTF_8));

            outputStream.flush();

        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            closeStream(outputStream);
        }

        return connection;
    }

    public String getOriginalLanguage(String text, String hint)
    {

        String detectApiUrl = String.format("%sdetect", API_LINK);
        HttpURLConnection connection;
        String urlParameters = null;

        try
        {
            urlParameters = String.format("key=%s&hint=%s&text=%s", API_KEY, hint,
                                          URLEncoder.encode(text, "UTF-8"));
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        connection = sendPostRequestToServer(detectApiUrl, urlParameters);

        return getServerResponse(connection);
    }


    public String getTranslationPost(String text, String language)
    {
        String translateApiUrl = String.format("%stranslate", API_LINK);
        String urlParameters = null;
        String response;
        HttpURLConnection connection;

        try
        {
            urlParameters = String.format("key=%s&lang=%s&text=%s", API_KEY, language,
                                          URLEncoder.encode(text, "UTF-8"));
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        response = _translatorCache.getIfPresent(text, language);

        if (response != null)
        {
            return response;
        }

        connection = sendPostRequestToServer(translateApiUrl, urlParameters);

        response = getServerResponse(connection);

        if (response != null)
        {
            _translatorCache.add(text, response, language);
        }

        return response;
    }
}