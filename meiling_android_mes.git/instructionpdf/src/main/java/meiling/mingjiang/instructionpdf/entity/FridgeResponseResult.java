package meiling.mingjiang.instructionpdf.entity;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by kouzeping on 2016/3/20.
 * email：kouzeping@shmingjiang.org.cn
 *
 * {"jsonrpc": "2.0", "id": null,
 *  "result": {
 *      "warning": [],
 *      "operation_instruction":
 *      {
 *      "file_path": {"__last_update": "2016-03-21 04:57:28", "id": 532, "file_size": 146852},
 *      "operation_id": "\u538b\u7f29\u673a\u5b89\u88c5",
 *      "id": 1,
 *      "name": "\u538b\u7f29\u673a\u5b89\u88c5TY-XZ-07"
 *      }
 *  }
 */
public class FridgeResponseResult {
    public List<String> warning;
    OperationInstruction operation_instruction;

    public OperationInstruction getOperation_instruction() {
        return operation_instruction;
    }

    public void setOperation_instruction(OperationInstruction operation_instruction) {
        this.operation_instruction = operation_instruction;
    }

    public List<String> getWarning() {
        return warning;
    }

    public void setWarning(List<String> warning) {
        this.warning = warning;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
