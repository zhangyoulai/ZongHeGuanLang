package meiling.mingjiang.line_storage.adjustment_fragment;

import com.google.gson.Gson;

/**
 * Created by kouzeping on 2016/3/5.
 * email：kouzeping@shmingjiang.org.cn
 */
public class AdjustmentItem {
    public String material_id;        //物料id
    public String material_name;    //物料名称
    public int alter_number=0;          //android端添加，为了记录提交调整的数量

    public AdjustmentItem() {
    }
    public AdjustmentItem(String material_id,String material_name, int alter_number) {
        this.material_id = material_id;
        this.material_name = material_name;
        this.alter_number = alter_number;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
