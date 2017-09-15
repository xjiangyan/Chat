package huiiuh.com.chat.model;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import huiiuh.com.chat.model.dao.UserAccountDao;

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
    }


    //获取全局线程池对象
    public ExecutorService getGlobalThreadPool() {
        return mExecutorService;
    }

    public UserAccountDao getUserAccountDao() {
        return mUserAccountDao;
    }
}
