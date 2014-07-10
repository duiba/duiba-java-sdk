package cn.com.duiba.credits.sdk;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class CreditTool {

	private String appKey;
	private String appSecret;
	public CreditTool(String appKey,String appSecret){
		this.appKey=appKey;
		this.appSecret=appSecret;
	}
	/**
	 * 构建在兑吧商城自动登录的url地址
	 * @param uid 用户id
	 * @param credits 用户积分余额
	 * @return 自动登录的url地址
	 */
	public String buildCreditAutoLoginRequest(String uid,int credits){
		String url="http://www.duiba.com.cn/autoLogin/autologin?";
		Map<String, String> params=new HashMap<String, String>();
		Long timestamp=new Date().getTime();
		params.put("uid", uid);
		params.put("credits", credits+"");
		params.put("appSecret", appSecret);
		params.put("appKey", appKey);
		params.put("timestamp", timestamp+"");
		String sign=SignTool.sign(params);
		url+="uid="+uid+"&credits="+credits+"&appKey="+appKey+"&sign="+sign+"&timestamp="+timestamp;
		return url;
	}
	/**
	 * 构建向兑吧查询兑换订单状态的url地址
	 * @param orderNum 兑吧的订单号
	 * @return
	 */
	public String buildCreditOrderStatusRequest(String orderNum){
		String url="http://www.duiba.com.cn/status/orderStatus?";
		Map<String, String> params=new HashMap<String, String>();
		Long timestamp=new Date().getTime();
		params.put("orderNum", orderNum);
		params.put("appKey", appKey);
		params.put("appSecret", appSecret);
		params.put("timestamp", timestamp+"");
		String sign=SignTool.sign(params);
		url+="orderNum="+orderNum+"&appKey="+appKey+"&sign="+sign+"&timestamp="+timestamp;
		return url;
	}
	/**
	 * 构建开发者审核结果的的方法
	 * @param params
	 * @return 发起请求的url
	 */
	public String buildCreditAuditRequest(CreditAuditParams params){
		String url="http://www.duiba.com.cn/audit/apiAudit?";
		Map<String, String> signParams=new HashMap<String, String>();
		Long timestamp=new Date().getTime();
		signParams.put("appKey", appKey);
		signParams.put("appSecret", appSecret);
		signParams.put("timestamp", timestamp+"");
		if(params.getPassOrderNums().size()>0){
			String s=null;
			for(String o:params.getPassOrderNums()){
				if(s==null){
					s=o;
				}else{
					s+=","+o;
				}
			}
			signParams.put("passOrderNums", s);
		}
		if(params.getRejectOrderNums().size()>0){
			String s=null;
			for(String o:params.getRejectOrderNums()){
				if(s==null){
					s=o;
				}else{
					s+=","+o;
				}
			}
			signParams.put("rejectOrderNums", s);
		}
		String sign=SignTool.sign(signParams);
		
		url+="appKey="+appKey+"&passOrderNums="+signParams.get("passOrderNums")+"&rejectOrderNums="+signParams.get("rejectOrderNums")+"&sign="+sign
				+"&timestamp="+timestamp;
		return url;
	}
	
	
	
	/**
	 * 积分余额查询的解析方法
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public CreditQueryParams parseCreditQuery(HttpServletRequest request) throws Exception{
		if(!appKey.equals(request.getParameter("appKey"))){
			throw new Exception("appKey不匹配");
		}
		if(request.getParameter("timestamp")==null){
			throw new Exception("请求中没有带时间戳");
		}
		boolean verify=SignTool.signVerify(appSecret, request);
		if(!verify){
			throw new Exception("签名验证失败");
		}
		CreditQueryParams params=new CreditQueryParams();
		params.setUid(request.getParameter("uid"));
		params.setAppKey(appKey);
		params.setTimestamp(new Date(Long.valueOf(request.getParameter("timestamp"))));
		return params;
	}
	/**
	 * 积分消耗请求的解析方法
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public CreditConsumeParams parseCreditConsume(HttpServletRequest request) throws Exception{
		if(!appKey.equals(request.getParameter("appKey"))){
			throw new Exception("appKey不匹配");
		}
		if(request.getParameter("timestamp")==null){
			throw new Exception("请求中没有带时间戳");
		}
		boolean verify=SignTool.signVerify(appSecret, request);
		if(!verify){
			throw new Exception("签名验证失败");
		}
		CreditConsumeParams params=new CreditConsumeParams();
		params.setAppKey(appKey);
		params.setUid(request.getParameter("uid"));
		params.setCredits(Integer.valueOf(request.getParameter("credits")));
		params.setTimestamp(new Date(Long.valueOf(request.getParameter("timestamp"))));
		params.setDescription(request.getParameter("description"));
		params.setOrderNum(request.getParameter("orderNum"));
		params.setType(request.getParameter("type"));
		if(request.getParameter("facePrice")!=null){
			params.setFacePrice(Integer.valueOf(request.getParameter("facePrice")));
		}
		if(request.getParameter("actualPrice")!=null){
			params.setActualPrice(Integer.valueOf(request.getParameter("actualPrice")));
		}
		params.setAlipay(request.getParameter("alipay"));
		params.setPhone(request.getParameter("phone"));
		params.setQq(request.getParameter("qq"));
		if(request.getParameter("waitAudit")!=null){
			params.setWaitAudit(Boolean.valueOf(request.getParameter("waitAudit")));
		}
		return params;
	}
	/**
	 * 积分消耗结果通知请求  的解析方法
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public CreditNotifyParams parseCreditNotify(HttpServletRequest request) throws Exception{
		if(!appKey.equals(request.getParameter("appKey"))){
			throw new Exception("appKey不匹配");
		}
		if(request.getParameter("timestamp")==null){
			throw new Exception("请求中没有带时间戳");
		}
		boolean verify=SignTool.signVerify(appSecret, request);
		if(!verify){
			throw new Exception("签名验证失败");
		}
		
		CreditNotifyParams params=new CreditNotifyParams();
		params.setSuccess(Boolean.valueOf(request.getParameter("success")));
		params.setErrorMessage(request.getParameter("errorMessage"));
		params.setBizId(request.getParameter("bizId"));
		params.setUid(request.getParameter("uid"));
		params.setAppKey(request.getParameter("appKey"));
		params.setTimestamp(new Date(Long.valueOf(request.getParameter("timestamp"))));
		return params;
	}
	/**
	 * 需要审核的兑换 的解析方法
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public CreditNeedAuditParams parseCreditAudit(HttpServletRequest request) throws Exception{
		if(!appKey.equals(request.getParameter("appKey"))){
			throw new Exception("appKey不匹配");
		}
		if(request.getParameter("timestamp")==null){
			throw new Exception("请求中没有带时间戳");
		}
		boolean verify=SignTool.signVerify(appSecret, request);
		if(!verify){
			throw new Exception("签名验证失败");
		}
		
		CreditNeedAuditParams params=new CreditNeedAuditParams();
		params.setAppKey(appKey);
		params.setTimestamp(new Date(Long.valueOf(request.getParameter("timestamp"))));
		params.setBizId(request.getParameter("bizId"));
		
		return params;
	}
	
	
	
}
