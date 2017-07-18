package cn.com.duiba.credits.sdk;


public class Urltest {

	public static void main(String[] args) {
		
		//测试生成免登陆地址的方法
		
		String uid = "test";
		Long credits = 1000L;
		String url = BuildUrl.buildAutoLoginRequest(uid, credits, null);
		System.out.println(url);

	}

}
