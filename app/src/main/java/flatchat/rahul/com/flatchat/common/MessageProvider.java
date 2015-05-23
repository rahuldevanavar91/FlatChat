package flatchat.rahul.com.flatchat.common;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * Created by rahul on 5/22/15.
 */
public class MessageProvider extends ContentProvider {
    private static final String TAG="MessageProvider";
    public static final String PROVIDER_NAME = "com.rahul.flatchat.messages";
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/messages");
    private static final int CUSTOMERS = 1;
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "messages", CUSTOMERS);
    }

    DataBaseHandeler dataBaseHandeler;

    @Override
    public boolean onCreate() {
        dataBaseHandeler = new DataBaseHandeler(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (uriMatcher.match(uri) == CUSTOMERS) {
            Cursor cursor = dataBaseHandeler.selectData();
            Log.i(TAG, "" + cursor.getCount());
            return cursor;
        } else {
            return null;
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Long id = dataBaseHandeler.insertData(values);
        return Uri.parse("messages" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
