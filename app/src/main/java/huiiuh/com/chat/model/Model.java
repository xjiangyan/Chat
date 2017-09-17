package huiiuh.com.chat.model;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import huiiuh.com.chat.model.bean.UserInfo;
import huiiuh.com.chat.model.dao.UserAccountDao;
import huiiuh.com.chat.model.db.DBManager;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */


//    需要去MyApplication初始化
public class Model {
    //创建对象
    private static Model model = new Model();
    private Context mContext;
    private ExecutorService mExecutorService = Executors.newCachedThreadPool();
    private UserAccountDao mUserAccountDao;
    private DBManager dbManager;

    //私有化构造
    private Model() {
    }

    //获取单例对象
    public static Model getInstance() {
        return model;

    }

    public void init(Context context) {
        mContext = context;
        mUserAccountDao = new UserAccountDao(mContext);
        // 开启全局监听
        EventListener eventListener = new EventListener(mContext);
    }


    //获取全局线程池对象
    public ExecutorService getGlobalThreadPool() {
        return mExecutorService;
    }
    // 获取用户账号数据库的操作类对象

    public UserAccountDao getUserAccountDao() {
        return mUserAccountDao;
    }

    public void loginSucess(UserInfo account) {
        // 校验
        if (account == null) {
            return;
        }

        if (dbManager != null) {
            dbManager.close();
        }

        dbManager = new DBManager(mContext, account.getName());
        Log.d("MainActivity", "数据库名称" + account.getName());

    }

    public DBManager getDbManager() {
        return dbManager;
    }

}
