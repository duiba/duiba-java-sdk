package cn.com.duiba.credits.sdk;

public class CreditConsumeResult {

	private boolean success;
	private String errorMessage="";
	private String message="";
	private String bizId="";
	private Long credits=-1L;//用户积分余额
	
	/**
	 * 目前只针对虚拟商品开放此字段的含义
	 * 
	 * ExchangeStatusSuccess(常量) : 直接成功
	 */
	private String exchangeStatus="";// 
	
	public static final String ExchangeStatusSuccess="exchange_success";
	

	public CreditConsumeResult(boolean success){
		this.success=success;
	}
	
	
	public String toString(){
		if(success){
			return "{'status':'ok','message':'"+message+"','errorMessage':'','data':{'bizId':'"+bizId+"','credits':'"+credits+"','exchangeStatus':'"+exchangeStatus+"'}}";
		}else{
			return "{'status':'fail','message':'"+message+"','errorMessage':'"+errorMessage+"','credits':'"+credits+"'}";
		}
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public String getBizId() {
		return bizId;
	}


	public void setBizId(String bizId) {
		this.bizId = bizId;
	}


	public Long getCredits() {
		return credits;
	}


	public void setCredits(Long credits) {
		this.credits = credits;
	}


	public String getExchangeStatus() {
		return exchangeStatus;
	}


	public void setExchangeStatus(String exchangeStatus) {
		this.exchangeStatus = exchangeStatus;
	}
}
