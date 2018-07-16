package com.wap.trans.entity.tr_1037;

/**
 * 
		
		* 描述:投保手机号变更（提交资料审核）的file实体 对象
		*
		* @author 骆利锋
		* @version 1.0
		* @since 2017年6月5日 上午10:03:46
 */
public class ReqTrans1037FileEntity {
	/**主键ID**/
	private String id;
	/**文件名**/
	private String fileName;
	/**BASE64编码**/
	private String base64Code;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getBase64Code() {
		return base64Code;
	}
	public void setBase64Code(String base64Code) {
		this.base64Code = base64Code;
	}
	
}
