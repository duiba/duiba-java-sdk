package cn.com.duiba.credits.sdk;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

public class SignTool {

	private static final Set<String> SIGN_KEYS = new HashSet<String>();
	private static final Set<String> IGNORE_KEYS = new HashSet<String>();
	static{
		SIGN_KEYS.add("uid");
		SIGN_KEYS.add("credits");
		SIGN_KEYS.add("appKey");
		SIGN_KEYS.add("timestamp");
		SIGN_KEYS.add("dcustom");
		SIGN_KEYS.add("transfer");
		SIGN_KEYS.add("vip");
		SIGN_KEYS.add("signKeys");
		SIGN_KEYS.add("alipay");
		SIGN_KEYS.add("realname");
		SIGN_KEYS.add("qq");
		SIGN_KEYS.add("phone");

		IGNORE_KEYS.add("sign");
		IGNORE_KEYS.add("appsecret");
	}
	
	public static boolean signVerify(String appSecret,HttpServletRequest request){
		Map<String, String[]> map=request.getParameterMap();
		Map<String, String> data=new HashMap<String, String>();
		for(String key:map.keySet()){
			data.put(key, map.get(key)[0]);
		}
		return signVerify(appSecret, data);
	}
	
	public static boolean signVerify(String appSecret,Map<String, String> params){
		Map<String, String> map=new HashMap<String, String>();

		Set<String> ignoreKeys = new HashSet<String>(IGNORE_KEYS);
		Set<String> keys = params.keySet();

		if(params.containsKey("signKeys")){
			String signKeys = params.get("signKeys");
			List<String> signKeysList = Arrays.asList(signKeys.split("|"));
			for(String key:keys){
				if (SIGN_KEYS.contains(key) || signKeysList.contains(key)){
					continue;
				}else{
					ignoreKeys.add(key.toLowerCase());
				}
			}
		}

		for(String key:keys){
			if(!ignoreKeys.contains(key.toLowerCase())){
				map.put(key, params.get(key));
			}
		}
		
		map.put("appSecret", appSecret);
		
		String sign=sign(map);
		if(sign.equals(params.get("sign"))){
			return true;
		}
		return false;
	}
	
	private static String toHexValue(byte[] messageDigest) {
		if (messageDigest == null)
			return "";
		StringBuilder hexValue = new StringBuilder();
		for (byte aMessageDigest : messageDigest) {
			int val = 0xFF & aMessageDigest;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
	/**
	 * 
	 * @param params
	 * @return
	 */
	public static String sign(Map<String,String> params){
		List<String> keys=new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String string="";
		for(String s:keys){
			string+=params.get(s);
		}
		String sign="";
		try {
			sign = toHexValue(encryptMD5(string.getBytes("utf-8")));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("md5 error");
		}
		return sign;
	}
	
	private static byte[] encryptMD5(byte[] data)throws Exception{
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(data);
		return md5.digest();
	}
	
	public static void main(String[] args) {
		String appKey="key";
		String appSecret="secret";
		
		Map<String, String> params=new HashMap<String, String>();
		params.put("appKey", appKey);
		params.put("appSecret", appSecret);
		params.put("signKeys","a|b");
		params.put("a", "222");
		params.put("b", "666");
		
		String sign=sign(params);

		params.put("c", "777");
		params.put("date", new Date().getTime()+"");
		params.put("sign", sign);


		System.out.println(signVerify(appSecret, params));
		
	}
}
