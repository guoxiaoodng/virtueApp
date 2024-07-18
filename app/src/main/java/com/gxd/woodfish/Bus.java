package com.gxd.woodfish;

/**
 * 创建时间：2023/2/23
 * 创建人：夏中尉
 * 描述：
 **/
public class Bus {
    public String type;
    public Object data;

    public Bus(String type, Object data) {
        this.type = type;
        this.data = data;
    }

    public Bus(String type) {
        this.type = type;
    }
}
