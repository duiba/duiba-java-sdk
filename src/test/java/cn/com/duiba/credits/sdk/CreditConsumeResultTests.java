package cn.com.duiba.credits.sdk;

import org.junit.Assert;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

public class CreditConsumeResultTests {

	public CreditConsumeResultTests() {
		
	}
	
	@Test
	public void CreditConsumeResult() throws Exception{
		
		CreditConsumeResult ccr = new CreditConsumeResult(true);
		try {
			JSONObject o = JSONObject.parseObject(ccr.toString());
			Assert.assertNotEquals(null, o.get("status"));
			Assert.assertNotEquals(null, o.get("credits"));
			Assert.assertNotEquals(null, o.get("bizId"));
			Assert.assertNotEquals(null, o.get("message"));
			Assert.assertNotEquals(null, o.get("errorMessage"));
		} catch (Exception e) {
			
		}
	}

}
