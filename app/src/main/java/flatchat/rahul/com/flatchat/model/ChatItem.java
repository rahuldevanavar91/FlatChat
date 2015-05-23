package flatchat.rahul.com.flatchat.model;

/**
 * Created by rahul on 5/21/15.
 */
public class ChatItem {
    private String mTimeStamp;
    private String mMsgId;
    private String mMsgType;
    private  String mMsgData;

    public ChatItem(String timeStamp,String msgId,String msgType,String msgData){
     mTimeStamp=timeStamp;
        mMsgId=msgId;
        mMsgData=msgData;
        mMsgType=msgType;
    }

    public String getTimeStamp() {
        return mTimeStamp;
    }

    public String getMsgId() {
        return mMsgId;
    }

    public String getMsgType() {
        return mMsgType;
    }

    public String getMsgData() {
        return mMsgData;
    }
}
