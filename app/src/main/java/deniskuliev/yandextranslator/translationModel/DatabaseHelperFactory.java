package deniskuliev.yandextranslator.translationModel;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Created by deniskuliev on 06.04.17.
 */

public class DatabaseHelperFactory
{
    private static DatabaseHelper _databaseHelper;

    public static DatabaseHelper getDatabaseHelper()
    {
        return _databaseHelper;
    }

    public static void setDatabaseHelper(Context context)
    {
        _databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    public static void releaseHelper()
    {
        OpenHelperManager.releaseHelper();
        _databaseHelper = null;
    }
}
