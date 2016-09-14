package com.zsw.testmodel.entity;

import java.util.List;

/**
 * author  z.sw
 * time  2016/9/12.
 * email  zhusw@visionet.com.cn
 * Description-
 */
public class Phone {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModels() {
        return models;
    }

    public void setModels(String models) {
        this.models = models;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private String name;
    private String models;
    private int price;


    public List<Integer> getColors() {
        return colors;
    }

    public void setColors(List<Integer> colors) {
        this.colors = colors;
    }

    private List<Integer> colors;


}
