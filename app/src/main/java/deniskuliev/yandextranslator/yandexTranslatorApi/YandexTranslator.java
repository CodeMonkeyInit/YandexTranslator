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

/**
 * Created by deniskuliev on 22.03.17.
 */

public class YandexTranslator
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

    public String getOriginalLanguage(String text, String hint)
    {
        HttpURLConnection connection;
        String detectApiUrl = String.format("%sdetect", API_LINK);
        DataOutputStream outputStream = null;
        String urlParameters;
        String response = null;
        URL apiURL = null;

        try
        {
            apiURL = new URL(detectApiUrl);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        urlParameters = String.format("key=%s&hint=%s&text=%s", API_KEY, hint, text);

        try
        {
            connection = (HttpURLConnection) apiURL.openConnection();

            connection.setRequestMethod("POST");

            connection.setDoOutput(true);

            outputStream = new DataOutputStream(connection.getOutputStream());

            outputStream.write(urlParameters.getBytes(Charsets.UTF_8));

            outputStream.flush();

            response = getServerResponse(connection);
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            closeStream(outputStream);
        }

        return response;
    }


    public String getTranslationPost(String text, String language)
    {
        HttpURLConnection connection;
        DataOutputStream outputStream = null;
        String detectApiUrl = String.format("%stranslate", API_LINK);
        String urlParameters = null;
        try
        {
            urlParameters = String.format("key=%s&lang=%s&text=%s", API_KEY, language,
                                          URLEncoder.encode(text, "UTF-8"));
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        String response = _translatorCache.getIfPresent(text, language);

        URL apiURL = null;

        try
        {
            apiURL = new URL(detectApiUrl);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        if (response != null)
        {
            return response;
        }

        try
        {
            connection = (HttpURLConnection) apiURL.openConnection();

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