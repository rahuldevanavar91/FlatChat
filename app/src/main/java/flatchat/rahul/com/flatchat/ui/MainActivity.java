package flatchat.rahul.com.flatchat.ui;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import flatchat.rahul.com.flatchat.R;
import flatchat.rahul.com.flatchat.adapter.MessageListAdapter;
import flatchat.rahul.com.flatchat.common.DataBaseHandeler;
import flatchat.rahul.com.flatchat.common.MessageProvider;
import flatchat.rahul.com.flatchat.common.MySingleton;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "MainActivity";
    private MessageListAdapter mAdapter;
    final String[] fromColumns = {DataBaseHandeler._id, DataBaseHandeler.MESSAGE_DATA, DataBaseHandeler.MESSAGE_TYPE};
    final int[] toViews = {R.id.message_id, R.id.message_type_text, R.id.message_time_stamp};
    private ListView mMessageListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMessageListView = (ListView) findViewById(R.id.message_list);
        loadData();

    }

    private void loadData() {
        //  final DataBaseHandeler dataBaseHandeler = new DataBaseHandeler(this);
        Log.i(TAG, "Load Data");
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, "http://pastebin.com/raw.php?i=aqziuquq", null, new Response.Listener<JSONObject>() {

                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "Success Response:" + response.toString());
                        try {
                            JSONArray array = response.getJSONArray("chats");
                            for (int i = 0; i < array.length(); i++) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put(DataBaseHandeler.MESSAGE_TIMESTAMP, array.getJSONObject(i).getString("timestamp"));
                                contentValues.put(DataBaseHandeler._id, array.getJSONObject(i).getString("msg_id"));
                                contentValues.put(DataBaseHandeler.MESSAGE_DATA, array.getJSONObject(i).getString("msg_data"));
                                contentValues.put(DataBaseHandeler.MESSAGE_TYPE, array.getJSONObject(i).getString("msg_type"));
                                Log.i(TAG, "Uri" + getContentResolver().insert(MessageProvider.CONTENT_URI, contentValues));
                            }
                            getSupportLoaderManager().initLoader(0, null, MainActivity.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage());

                        // TODO Auto-generated method stub

                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = MessageProvider.CONTENT_URI;
        return new CursorLoader(this, uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.e(TAG, "Sqlite Count" + data.getCount());
        mAdapter = new MessageListAdapter(MainActivity.this, R.layout.message_list_item, data
                , fromColumns, toViews, 0);
        mMessageListView.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}
