package com.jiyun.qmdemo4;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Girls {
    @Id(autoincrement = true)
    @Property(nameInDb = "list")
    private Long id;
    private String _id;
    private String url;

    @Generated(hash = 331620401)
    public Girls(Long id, String _id, String url) {
        this.id = id;
        this._id = _id;
        this.url = url;
    }

    @Generated(hash = 1244608458)
    public Girls() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Girls{" +
                "id=" + id +
                ", _id='" + _id + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
