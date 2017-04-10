package com.shmingjiang.www.mldaq.bean;

/**
 * 灌注机基础数据
 * Created by wangdongjia on 16-7-29.
 */
public class PerfusionMachine {
    private String id;
    private String BarCode;
    private String ReportDateTime;
    private String SISTEMA;
    private String CICLO;
    private String VUOTO;
    private String E_VUOTO;
    private String DOSE_IMP;
    private String DOSE_CAR;
    private String E_CARICA;
    private String REFRIG;
    private String REFRIG_ID;
    private String Temperature;
    private String PRESS;

    public String getPRESS() {
        return PRESS;
    }

    public void setPRESS(String PRESS) {
        this.PRESS = PRESS;
    }

    public PerfusionMachine(String id, String barCode, String reportDateTime, String SISTEMA,
                            String CICLO, String VUOTO, String e_VUOTO, String DOSE_IMP,
                            String DOSE_CAR, String e_CARICA, String REFRIG, String REFRIG_ID,
                            String temperature, String PRESS) {
        this.id = id;
        BarCode = barCode;
        ReportDateTime = reportDateTime;
        this.SISTEMA = SISTEMA;
        this.CICLO = CICLO;
        this.VUOTO = VUOTO;
        E_VUOTO = e_VUOTO;
        this.DOSE_IMP = DOSE_IMP;
        this.DOSE_CAR = DOSE_CAR;
        E_CARICA = e_CARICA;
        this.REFRIG = REFRIG;
        this.REFRIG_ID = REFRIG_ID;
        Temperature = temperature;
        this.PRESS = PRESS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public String getReportDateTime() {
        return ReportDateTime;
    }

    public void setReportDateTime(String reportDateTime) {
        ReportDateTime = reportDateTime;
    }

    public String getSISTEMA() {
        return SISTEMA;
    }

    public void setSISTEMA(String SISTEMA) {
        this.SISTEMA = SISTEMA;
    }

    public String getCICLO() {
        return CICLO;
    }

    public void setCICLO(String CICLO) {
        this.CICLO = CICLO;
    }

    public String getVUOTO() {
        return VUOTO;
    }

    public void setVUOTO(String VUOTO) {
        this.VUOTO = VUOTO;
    }

    public String getE_VUOTO() {
        return E_VUOTO;
    }

    public void setE_VUOTO(String e_VUOTO) {
        E_VUOTO = e_VUOTO;
    }

    public String getDOSE_IMP() {
        return DOSE_IMP;
    }

    public void setDOSE_IMP(String DOSE_IMP) {
        this.DOSE_IMP = DOSE_IMP;
    }

    public String getDOSE_CAR() {
        return DOSE_CAR;
    }

    public void setDOSE_CAR(String DOSE_CAR) {
        this.DOSE_CAR = DOSE_CAR;
    }

    public String getE_CARICA() {
        return E_CARICA;
    }

    public void setE_CARICA(String e_CARICA) {
        E_CARICA = e_CARICA;
    }

    public String getREFRIG() {
        return REFRIG;
    }

    public void setREFRIG(String REFRIG) {
        this.REFRIG = REFRIG;
    }

    public String getREFRIG_ID() {
        return REFRIG_ID;
    }

    public void setREFRIG_ID(String REFRIG_ID) {
        this.REFRIG_ID = REFRIG_ID;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }
}
