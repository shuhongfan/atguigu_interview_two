package com.shf.CountDownLatch;

import lombok.Getter;

/**
 * 枚举
 */
public enum CountryEnum {
    ONE(1, "齐"),
    TWO(2, "楚"),
    THREE(3, "燕"),
    FOUR(4, "赵"),
    FIFE(5, "魏"),
    SIX(6, "韩");

    @Getter
    private Integer retCode;
    @Getter
    private String retMessage;

    CountryEnum(Integer retCode, String retMessage) {
        this.retCode = retCode;
        this.retMessage = retMessage;
    }

    public static CountryEnum forEach_countryEnum(int index){
        CountryEnum[] values = CountryEnum.values();
        for (CountryEnum element : values) {
            if (index==element.getRetCode()){
                return element;
            }
        }
        return null;
    }
}
