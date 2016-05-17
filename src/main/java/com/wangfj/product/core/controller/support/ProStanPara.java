package com.wangfj.product.core.controller.support;

import javax.validation.constraints.NotNull;

public class ProStanPara {
	/**
	 * 色系编码
	 */
	@NotNull(message = "{colorSid.isNotNull}")
	private String colorSid;
	/**
	 * 款号
	 */
	@NotNull(message = "{modelCode.isNotNull}")
	private String modelCode;

	@Override
	public String toString() {
		return "ProStanPara [colorSid=" + colorSid + ", modelCode=" + modelCode + "]";
	}

	public String getColorSid() {
		return colorSid;
	}

	public void setColorSid(String colorSid) {
		this.colorSid = colorSid;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
}
