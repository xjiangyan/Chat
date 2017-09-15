package huiiuh.com.chat.model.bean;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class UserInfo {
    private String nick;//用户昵称
    private String hxid;//环信id
    private String photo;//用户头像
    private String name;//用户名称

    public UserInfo(String name) {
        this.nick = name;
        this.hxid = name;
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "nick='" + nick + '\'' +
                ", hxid='" + hxid + '\'' +
                ", photo='" + photo + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getHxid() {
        return hxid;
    }

    public void setHxid(String hxid) {
        this.hxid = hxid;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
