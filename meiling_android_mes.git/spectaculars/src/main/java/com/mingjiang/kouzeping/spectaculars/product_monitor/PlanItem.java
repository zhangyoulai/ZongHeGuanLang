package com.mingjiang.kouzeping.spectaculars.product_monitor;

/**
 * Created by kouzeping on 2016/2/2.
 * email：kouzeping@shmingjiang.org.cn
 *
 * "section_gd0020": 总装数量,
 * "section_gd0010": 预装数量,
 * "order_id": 订单号,
 * "id": 日计划号,
 * "plan_quantity": 计划数量,
 * "store_quantity": 完成数量
 *
 */
public class PlanItem {
    int section_gd0010;
    int section_gd0020;
    int section_gd0030;
    int section_gd0040;
    int section_gd0050;


    String order_id;
    String id;
    int plan_quantity;
    int store_quantity;
    String spec;

    public int getSection_gd0020() {
        return section_gd0020;
    }

    public void setSection_gd0020(int section_gd0020) {
        this.section_gd0020 = section_gd0020;
    }

    public int getSection_gd0010() {
        return section_gd0010;
    }

    public void setSection_gd0010(int section_gd0010) {
        this.section_gd0010 = section_gd0010;
    }

    public int getSection_gd0030() {
        return section_gd0030;
    }

    public void setSection_gd0030(int section_gd0030) {
        this.section_gd0030 = section_gd0030;
    }

    public int getSection_gd0050() {
        return section_gd0050;
    }

    public void setSection_gd0050(int section_gd0050) {
        this.section_gd0050 = section_gd0050;
    }

    public int getSection_gd0040() {
        return section_gd0040;
    }

    public void setSection_gd0040(int section_gd0040) {
        this.section_gd0040 = section_gd0040;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPlan_quantity() {
        return plan_quantity;
    }

    public void setPlan_quantity(int plan_quantity) {
        this.plan_quantity = plan_quantity;
    }

    public int getStore_quantity() {
        return store_quantity;
    }

    public void setStore_quantity(int store_quantity) {
        this.store_quantity = store_quantity;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }
}
