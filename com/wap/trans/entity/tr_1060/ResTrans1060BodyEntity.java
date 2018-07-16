package com.wap.trans.entity.tr_1060;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ResTrans1060BodyEntity {
	@XStreamAlias("BASE_PART")
	private ResTrans1060BasePart basePart;

	public ResTrans1060BasePart getBasePart() {
		return basePart;
	}

	public void setBasePart(ResTrans1060BasePart basePart) {
		this.basePart = basePart;
	}
}
