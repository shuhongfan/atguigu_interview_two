package com.shf.demo02.thread;

import lombok.Getter;
import lombok.Setter;

public enum Country {
    ONE(1, "齐"),
    TWO(2, "楚"),
    THREE(3, "燕"),
    FOUR(4, "赵"),
    FIVE(5, "魏"),
    SIX(6, "韩");

    @Getter private Integer retCode;
    @Getter private String retMessage;

    Country(Integer retCode, String retMessage) {
        this.retCode = retCode;
        this.retMessage = retMessage;
    }

    public static Country forEach_countryEnum(int index) {
        Country[] myCountry = Country.values();
        for (Country country : myCountry) {
            if (index == country.retCode) {
                return country;
            }
        }
        return null;
    }
}
