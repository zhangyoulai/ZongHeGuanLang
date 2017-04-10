package com.example.melificent.xuweizongheguanlang.Bean;

import java.io.Serializable;

/**
 * Created by p on 2017/3/1.
 */

public class Task implements Serializable {
    public static final long serialVersionUID = -758459502806858414L;
    public String taskTime ;
    public String taskBody;

    public Task(String taskTime, String taskBody) {
        this.taskTime = taskTime;
        this.taskBody = taskBody;
    }
}
