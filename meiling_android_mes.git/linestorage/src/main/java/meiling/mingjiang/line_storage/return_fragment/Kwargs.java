package meiling.mingjiang.line_storage.return_fragment;

import java.util.List;

/**
 * Created by kouzeping on 2016/3/23.
 * email：kouzeping@shmingjiang.org.cn
 *
 * "kwargs": {
     "area_id": "1011",下料点
     "reason": "123",退料原因
     "material_list": [
         {
         "material_id": "12",物料编码
         "alter_number": "12"退料数量
         }
     ]
 }
 */
public class Kwargs {
    public  String area_id ="";
    public  String reason="";
    public  List<MaterialList> material_list=null;

    public Kwargs() {
    }

    public Kwargs(String area_id, String reason, List<MaterialList> material_list) {
        this.area_id = area_id;
        this.reason = reason;
        this.material_list = material_list;
    }
}
