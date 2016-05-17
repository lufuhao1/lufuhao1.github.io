package com.wangfj.product.core.controller.support;

public class PrctureInfoPara {
	private String spuCode;
	private String color;
	private Integer ifDelete;
	private Integer isModel;//是否原图
	private Integer isThumbnail;// 是否缩略图

	public String getSpuCode() {
		return spuCode;
	}

	public void setSpuCode(String spuCode) {
		this.spuCode = spuCode;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getIfDelete() {
		return ifDelete;
	}

	public void setIfDelete(Integer ifDelete) {
		this.ifDelete = ifDelete;
	}

	public Integer getIsThumbnail() {
		return isThumbnail;
	}

	public void setIsThumbnail(Integer isThumbnail) {
		this.isThumbnail = isThumbnail;
	}

	public Integer getIsModel() {
		return isModel;
	}

	public void setIsModel(Integer isModel) {
		this.isModel = isModel;
	}

	@Override
	public String toString() {
		return "PrctureInfoPara [spuCode=" + spuCode + ", color=" + color + ", ifDelete="
				+ ifDelete + ", isThumbnail=" + isThumbnail + ", isModel=" + isModel + "]";
	}

}
