package deniskuliev.yandextranslator.translationModel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by deniskuliev on 06.04.17.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper
{
    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "yandextranslatortestapp.db";

    private static final int DATABASE_VERSION = 1;

    private HistoryDAO historyDAO;

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        //TODO delete after debug
        //context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource)
    {
        try
        {
            TableUtils.createTable(connectionSource, TranslatedText.class);
        }
        catch (SQLException e)
        {
            Log.e(TAG, "Error while creating Database" + DATABASE_NAME);
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1)
    {
        try
        {
            TableUtils.dropTable(connectionSource, TranslatedText.class, true);

            onCreate(sqLiteDatabase, connectionSource);
        }
        catch (SQLException e)
        {
            Log.e(TAG, "Error while upgrading Database" + DATABASE_NAME);
            e.printStackTrace();
        }
    }

    public HistoryDAO getHistoryDAO() throws SQLException
    {
        if (historyDAO == null)
        {
            historyDAO = new HistoryDAO(getConnectionSource(), TranslatedText.class);
        }
        return historyDAO;
    }

    @Override
    public void close()
    {
        super.close();
        historyDAO = null;
    }
}
