package cn.com.duiba.credits.sdk;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreditNotifyParams {

	private boolean success;
	private String bizId="";
	private String errorMessage;
	private Date timestamp=new Date();
	private String appKey;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	
	public Map<String, String> toRequestMap(String appSecret){
		Map<String, String> map=new HashMap<String, String>();
		map.put("success", success+"");
		map.put("errorMessage", errorMessage);
		map.put("bizId", bizId);
		map.put("appKey", appKey);
		map.put("appSecret", appSecret);
		map.put("timestamp", timestamp.getTime()+"");
		
		String sign=SignTool.sign(map);
		
		map.remove("appSecret");
		map.put("sign", sign);
		return map;
	}
	
}
