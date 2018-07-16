package com.wap.trans.entity.tr_1033;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class ResTrans1033BodyEntity {
	
	@XStreamAlias("BASE_PART")
	private ResTrans1033BodyBasePart basePart;

	public ResTrans1033BodyBasePart getBasePart() {
		return basePart;
	}

	public void setBasePart(ResTrans1033BodyBasePart basePart) {
		this.basePart = basePart;
	}
	
}
