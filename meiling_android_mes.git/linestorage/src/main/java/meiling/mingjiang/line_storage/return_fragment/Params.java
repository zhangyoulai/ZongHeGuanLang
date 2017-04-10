package meiling.mingjiang.line_storage.return_fragment;

import java.util.List;

/**
 * Created by kouzeping on 2016/3/23.
 * email：kouzeping@shmingjiang.org.cn
 * "params": {
     "args": [],
     "kwargs": {
         "area_id": "1011",下料点
         "reason": "123",退料原因
         "material_list": [
             {
             "material_id": "12",物料编码
             "alter_number": "12"退料数量
             }
        ]
     }
 }
 */
public class Params {
    public List args;
    public Kwargs kwargs;

    public Params(List args, Kwargs kwargs) {
        this.args = args;
        this.kwargs = kwargs;
    }
}
