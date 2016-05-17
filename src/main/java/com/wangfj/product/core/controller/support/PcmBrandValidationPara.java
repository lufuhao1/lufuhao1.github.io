package com.wangfj.product.core.controller.support;

/**
 * Created by wangxuan on 2016-03-23 0023.
 */
public class PcmBrandValidationPara {

    private Integer brandType;//品牌类型

    private Integer shopType;//门店类型

    private String brandName;//品牌名称

    public Integer getBrandType() {
        return brandType;
    }

    public void setBrandType(Integer brandType) {
        this.brandType = brandType;
    }

    public Integer getShopType() {
        return shopType;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Override
    public String toString() {
        return "PcmBrandValidationPara{" +
                "brandType=" + brandType +
                ", shopType=" + shopType +
                ", brandName='" + brandName + '\'' +
                '}';
    }
}
