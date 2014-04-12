package com.lifedjtu.jw.util.fetcher;

/**
 * Created by Li He on 2014/4/12.
 * Simple Cookie
 * @author Li He
 */
public class Cookie {

    private String name;
    private String value;

    public Cookie(String name, String value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
