package meiling.mingjiang.appupdata;

/**
 * Created by kouzeping on 2016/3/15.
 * email：kouzeping@shmingjiang.org.cn
 */
public class UpdataInfo {
    //{"filename_field": "pack_name", "field": "pack", "version": "3", "id": 5}

    private String filename_field;
    private String field;
    private String version;
    private String id;

    public String getFilename_field() {
        return filename_field;
    }

    public void setFilename_field(String filename_field) {
        this.filename_field = filename_field;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
