package deniskuliev.yandextranslator.yandexTranslatorApi;

import com.google.common.base.Charsets;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by deniskuliev on 22.03.17.
 */

public class YandexTranslator
{
    private final static String API_LINK = "https://translate.yandex.net/api/v1.5/tr.json/translate";
    private final static String API_KEY = "trnsl.1.1.20170322T123605Z.d3c47f5acd5771ea.880203db72f4ab703e8a65af7ce33a9e2a0ac456";
    private final static int RESPONSE_OK = 200;
    private static TranslatorCache _translatorCache;

    private URL _apiUrl;

    public YandexTranslator()
    {
        if (_translatorCache == null)
        {
            _translatorCache = TranslatorCache.getInstance();
        }

        try
        {
            _apiUrl = new URL(API_LINK);

        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    private void closeStream(Closeable stream)
    {
        if (stream != null)
        {
            try
            {
                stream.close();

            } catch (IOException e)
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
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (scanner != null)
            {
                scanner.close();
            }
        }
        return response;
    }

    public String getTranslationPost(String text, String language)
    {
        HttpURLConnection connection;
        DataOutputStream outputStream = null;
        String urlParameters = String.format("key=%s&lang=%s&text=%s", API_KEY, language, text);

        String response = _translatorCache.getIfPresent(text, language);

        if (response != null)
        {
            return response;
        }

        try
        {
            connection = (HttpURLConnection) _apiUrl.openConnection();

            connection.setRequestMethod("POST");

            connection.setDoOutput(true);

            outputStream = new DataOutputStream(connection.getOutputStream());

            outputStream.write(urlParameters.getBytes(Charsets.UTF_8));

            outputStream.flush();

            response = getServerResponse(connection);

            _translatorCache.add(text, response, language);


        } catch (IOException exception)
        {
            exception.printStackTrace();
        } finally
        {
            closeStream(outputStream);
        }

        return response;
    }
}