package cn.com.duiba.credits.sdk;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class CreditConsumeParams {

	private String appKey;
	private Date timestamp;//时间戳
	private Integer credits;//消耗积分数
	private String orderNum;//兑吧订单号
	private String description;
	private String type="";//类型：QB,Phonebill,AlipayFast,Coupon
	private Integer facePrice=0;//面值，分为单位
	private Integer actualPrice=0;//实际扣款，分为单位
	private String uid;
	private boolean waitAudit=false;//是否等待审核， 如果返回true，表示此订单需要审核，审核通过后才会继续下去。 如果返回false表示此订单无须审核，会直接继续兑换流程
	public Integer getCredits() {
		return credits;
	}
	public void setCredits(Integer credits) {
		this.credits = credits;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public Map<String, String> toRequestMap(String appSecret){
		Map<String, String> map=new HashMap<String, String>();
		map.put("credits", credits+"");
		map.put("description", description);
		map.put("uid", uid);
		map.put("appKey", appKey);
		map.put("waitAudit", waitAudit+"");
		map.put("appSecret", appSecret);
		map.put("timestamp", timestamp.getTime()+"");
		map.put("orderNum", orderNum);
		map.put("type", type);
		map.put("facePrice", facePrice+"");
		map.put("actualPrice", actualPrice+"");
		
		String sign=SignTool.sign(map);
		
		map.remove("appSecret");
		map.put("sign", sign);
		return map;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public boolean isWaitAudit() {
		return waitAudit;
	}
	public void setWaitAudit(boolean waitAudit) {
		this.waitAudit = waitAudit;
	}
}
