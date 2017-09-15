package huiiuh.com.chat.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import huiiuh.com.chat.model.bean.UserInfo;
import huiiuh.com.chat.model.db.UserAccountDB;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class UserAccountDao {


    private final UserAccountDB mHelper;


    public UserAccountDao(Context context) {
        mHelper = new UserAccountDB(context);
    }

    public void addAccount(UserInfo userInfo) {
        //获取数据库对象
        SQLiteDatabase db = mHelper.getReadableDatabase();
        //执行添加操作
        ContentValues values = new ContentValues();
        values.put(UserAccountTable.COL_HXID, userInfo.getHxid());
        values.put(UserAccountTable.COL_NAME, userInfo.getName());
        values.put(UserAccountTable.COL_NICK, userInfo.getNick());
        values.put(UserAccountTable.COL_PHOTO, userInfo.getPhoto());
        db.replace(UserAccountTable.TAB_NAME, null, values);//有就替换 没有就添加
        //从下往上写

    }

    public UserInfo getAccountByHxId(String hxid) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String sql = "select * from " + UserAccountTable.TAB_NAME + " where " + UserAccountTable.COL_HXID + "= ?";
        Cursor cursor = db.rawQuery(sql, new String[]{hxid});

        UserInfo userinfo = null;
        if(cursor.moveToNext()){
            userinfo = new UserInfo(hxid);
            userinfo.setHxid(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_HXID)));
            userinfo.setName(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NAME)));
            userinfo.setNick(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NICK)));
            userinfo.setPhoto(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_PHOTO)));
        }

        cursor.close();
        return userinfo;
    }
}
