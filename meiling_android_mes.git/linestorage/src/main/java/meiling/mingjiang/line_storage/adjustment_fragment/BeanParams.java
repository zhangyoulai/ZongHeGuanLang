package meiling.mingjiang.line_storage.adjustment_fragment;

import java.util.List;

/**
 * Created by kouzeping on 2016/3/8.
 * email：kouzeping@shmingjiang.org.cn
 */
public class BeanParams {
    List args;
    BeanKwargs kwargs;

    public BeanParams(List args, BeanKwargs kwargs) {
        this.args = args;
        this.kwargs = kwargs;
    }
}
