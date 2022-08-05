package com.example.networkdemo;

import java.io.Serializable;
import java.lang.reflect.Type;
//import com.example.networkdemo.Types;

public class Message implements Serializable {

    private Object data;
    private Typess type;
//    private String description;   // is part of Types

    public Message(Object data, Typess type){

        this.data = data;
        this.type = type;
//        this.description = type.getDescription();

    }

    public Object getData(){
        return data;
    }

    public Typess getType(){
        return type;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setType(Typess type) {
        this.type = type;
    }

}
