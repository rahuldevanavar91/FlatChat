package flatchat.rahul.com.flatchat.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import flatchat.rahul.com.flatchat.R;
import flatchat.rahul.com.flatchat.common.DataBaseHandeler;
import flatchat.rahul.com.flatchat.common.MySingleton;

/**
 * Created by rahul on 5/21/15.
 */
public class MessageListAdapter extends SimpleCursorAdapter {

    private final Context mContaxt;
    LayoutInflater mLayoutInflater;
    Cursor mCursor;

    public MessageListAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        mLayoutInflater = LayoutInflater.from(context);
        mCursor = c;
        mContaxt = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (mCursor.moveToPosition(position)) {
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mLayoutInflater.inflate(R.layout.message_list_item, parent, false);
                holder.mMessageId = (TextView) convertView.findViewById(R.id.message_id);
                holder.mMesdageTypeText = (TextView) convertView.findViewById(R.id.message_type_text);
                holder.mTimeStamp = (TextView) convertView.findViewById(R.id.message_time_stamp);
                holder.mMessageTypeImage = (ImageView) convertView.findViewById(R.id.message_type_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String id = mCursor.getString(mCursor.getColumnIndex(DataBaseHandeler._id));
            String data = mCursor.getString(mCursor.getColumnIndex(DataBaseHandeler.MESSAGE_DATA));
            String time = mCursor.getString(mCursor.getColumnIndex(DataBaseHandeler.MESSAGE_TIMESTAMP));
            if (mCursor.getString(mCursor.getColumnIndex(DataBaseHandeler.MESSAGE_TYPE)).equalsIgnoreCase("0")) {
                holder.mMesdageTypeText.setVisibility(View.VISIBLE);
                holder.mMesdageTypeText.setText(data);
                holder.mMessageTypeImage.setVisibility(View.GONE);
            } else {
                holder.mMesdageTypeText.setVisibility(View.GONE);
                holder.mMessageTypeImage.setVisibility(View.VISIBLE);

                ImageRequest request = new ImageRequest(data,
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                holder.mMessageTypeImage.setImageBitmap(bitmap);
                            }
                        }, 0, 0, null,
                        new Response.ErrorListener() {
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                MySingleton.getInstance(mContaxt).addToRequestQueue(request);
            }
            holder.mMessageId.setText(id);
            holder.mMesdageTypeText.setText(data);
            holder.mTimeStamp.setText(time);
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView mMessageId;
        TextView mMesdageTypeText;
        TextView mTimeStamp;
        ImageView mMessageTypeImage;
    }
}
