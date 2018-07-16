package com.sys.exception;

public class EpiccException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errMess;
	public EpiccException(){
		super();
	}
	public EpiccException(String errorMsg){
		super();
		errMess=errorMsg;
	}
	public String getErrMess() {
		return errMess;
	}
	public void setErrMess(String errMess) {
		this.errMess = errMess;
	}

}
