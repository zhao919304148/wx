package com.wap.trans.entity.tr_1066;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ResTrans1066BodyEntity {
	@XStreamAlias("BASE_PART")
	private ResTrans1066BasePartEntity basePart;

	public ResTrans1066BasePartEntity getBasePart() {
		return basePart;
	}

	public void setBasePart(ResTrans1066BasePartEntity basePart) {
		this.basePart = basePart;
	}
}
