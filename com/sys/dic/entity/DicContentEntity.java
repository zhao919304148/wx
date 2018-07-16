package com.sys.dic.entity;

import java.io.Serializable;


/**
 * 系统字典对象
 * @ClassName  : DicContentEntity.java
 * @Brief      : [brief of the class]
 * @Author     : huanghui
 * @CreateDate : Mar 11, 2009
 * @CopyRight  : 
 * @Descript   :[class struct descript] 
 *        No.1 :
 */
public class DicContentEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1389432634121446369L;

	//字典类型
	private String dicTypeId;

	//内容ID
	private String dicId;
	
	//内容值
	private String idValue;
	
	//内容值2
	private String idValue2;
	
	
	
	public String getDicTypeId() {
		return dicTypeId;
	}

	public void setDicTypeId(String dicTypeId) {
		this.dicTypeId = dicTypeId;
	}

	public String getDicId() {
		return dicId;
	}

	public void setDicId(String dicId) {
		this.dicId = dicId;
	}

	public String getIdValue() {
		return idValue;
	}

	public void setIdValue(String idValue) {
		this.idValue = idValue;
	}

	public String getIdValue2() {
		return idValue2;
	}

	public void setIdValue2(String idValue2) {
		this.idValue2 = idValue2;
	}
	
	
}