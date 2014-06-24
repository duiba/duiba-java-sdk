package cn.com.duiba.credits.sdk;

public class CreditQueryResult {

	private boolean success;
	private int credits;
	private String phone="";//手机号
	private String alipay="";//支付宝账号
	private String errorMessage="";
	private String message="";

	public CreditQueryResult(boolean success){
		this.success=success;
	}
	
	public CreditQueryResult(boolean success,int credits){
		this.success=success;
		this.credits=credits;
	}
	
	public String toString(){
		if(success){
			return "{'status':'ok','message':'"+message+"','data':{'credits':'"+credits+"','phone':'"+phone+"','alipay':'"+alipay+"'}}";
		}else{
			return "{'status':'fail','message':'"+message+"','errorMessage':'"+errorMessage+"'}";
		}
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAlipay() {
		return alipay;
	}

	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
