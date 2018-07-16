package com.wap.trans.entity.tr_1055;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

@XStreamAlias("responseBodyVo")
public class ResTrans1055BodyEntity {
	
	@XStreamAlias("list")
	private List<ResTrans1055MovecarMainDataEntity> resTrans1055MovecarMainDataEntities;

	public List<ResTrans1055MovecarMainDataEntity> getResTrans1055MovecarMainDataEntities() {
		return resTrans1055MovecarMainDataEntities;
	}

	public void setResTrans1055MovecarMainDataEntities(
			List<ResTrans1055MovecarMainDataEntity> resTrans1055MovecarMainDataEntities) {
		this.resTrans1055MovecarMainDataEntities = resTrans1055MovecarMainDataEntities;
	}

}
