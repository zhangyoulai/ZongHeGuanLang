package com.mingjiang.android.app.bean;

/**
 * Created by CaoBin on 2016/9/21.
 */
public class Performance {


    /**
     * bad_count : 0
     * count : 2
     */

    private Mdxn3Bean mdxn3;
    /**
     * mdxn3 : {"bad_count":0,"count":2}
     * product_type : NR-W55PM1
     * mld : {"bad_count":2,"count":4}
     * mdxn5 : {"bad_count":0,"count":0}
     * mlj : {"bad_count":0,"count":1}
     * wifi : {"bad_count":0,"count":4}
     * MLIR : {"bad_count":0,"count":0}
     */

    private String product_type;
    /**
     * bad_count : 2
     * count : 4
     */

    private MldBean mld;
    /**
     * bad_count : 0
     * count : 0
     */

    private Mdxn5Bean mdxn5;
    /**
     * bad_count : 0
     * count : 1
     */

    private MljBean mlj;
    /**
     * bad_count : 0
     * count : 4
     */

    private WifiBean wifi;
    /**
     * bad_count : 0
     * count : 0
     */

    private MLIRBean MLIR;

    public Mdxn3Bean getMdxn3() {
        return mdxn3;
    }

    public void setMdxn3(Mdxn3Bean mdxn3) {
        this.mdxn3 = mdxn3;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public MldBean getMld() {
        return mld;
    }

    public void setMld(MldBean mld) {
        this.mld = mld;
    }

    public Mdxn5Bean getMdxn5() {
        return mdxn5;
    }

    public void setMdxn5(Mdxn5Bean mdxn5) {
        this.mdxn5 = mdxn5;
    }

    public MljBean getMlj() {
        return mlj;
    }

    public void setMlj(MljBean mlj) {
        this.mlj = mlj;
    }

    public WifiBean getWifi() {
        return wifi;
    }

    public void setWifi(WifiBean wifi) {
        this.wifi = wifi;
    }

    public MLIRBean getMLIR() {
        return MLIR;
    }

    public void setMLIR(MLIRBean MLIR) {
        this.MLIR = MLIR;
    }

    public static class Mdxn3Bean {
        private int bad_count;
        private int count;

        public int getBad_count() {
            return bad_count;
        }

        public void setBad_count(int bad_count) {
            this.bad_count = bad_count;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static class MldBean {
        private int bad_count;
        private int count;

        public int getBad_count() {
            return bad_count;
        }

        public void setBad_count(int bad_count) {
            this.bad_count = bad_count;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static class Mdxn5Bean {
        private int bad_count;
        private int count;

        public int getBad_count() {
            return bad_count;
        }

        public void setBad_count(int bad_count) {
            this.bad_count = bad_count;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static class MljBean {
        private int bad_count;
        private int count;

        public int getBad_count() {
            return bad_count;
        }

        public void setBad_count(int bad_count) {
            this.bad_count = bad_count;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static class WifiBean {
        private int bad_count;
        private int count;

        public int getBad_count() {
            return bad_count;
        }

        public void setBad_count(int bad_count) {
            this.bad_count = bad_count;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static class MLIRBean {
        private int bad_count;
        private int count;

        public int getBad_count() {
            return bad_count;
        }

        public void setBad_count(int bad_count) {
            this.bad_count = bad_count;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
