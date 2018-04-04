package deniskuliev.yandextranslator.translationModel;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

@SuppressWarnings("WeakerAccess")
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

    @SuppressWarnings("unused")
    public static void releaseHelper()
    {
        OpenHelperManager.releaseHelper();
        _databaseHelper = null;
    }
}
