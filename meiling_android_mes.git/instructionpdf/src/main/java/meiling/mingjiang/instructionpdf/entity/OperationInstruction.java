package meiling.mingjiang.instructionpdf.entity;

/**
 * Created by kouzeping on 2016/3/21.
 * email：kouzeping@shmingjiang.org.cn
 *
 * "operation_instruction":
       {
         "file_path": {"__last_update": "2016-03-21 04:57:28", "id": 532, "file_size": 146852},
         "operation_id": "\u538b\u7f29\u673a\u5b89\u88c5",
         "id": 1,
         "name": "\u538b\u7f29\u673a\u5b89\u88c5TY-XZ-07"
       }
 */
public class OperationInstruction {
    FilePath file_path;
    String operation_id;
    String id;
    String name;

    public FilePath getFile_path() {
        return file_path;
    }

    public void setFile_path(FilePath file_path) {
        this.file_path = file_path;
    }

    public String getOperation_id() {
        return operation_id;
    }

    public void setOperation_id(String operation_id) {
        this.operation_id = operation_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
