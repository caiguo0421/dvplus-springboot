package cn.sf_soft.common.util;

import cn.sf_soft.common.ServiceException;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import java.util.Base64;


import java.nio.charset.Charset;
import java.security.MessageDigest;

public class EncryptionUtil {
	
	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(EncryptionUtil.class);

	/**
	 * 将密码转换成MD5加密后的字符串形式.
	 * 
	 * @author king
	 * @param password
	 *            原始密码
	 * @return 32位字符串
	 */
	public static String getMD5(String password) {
		byte[] source = password.getBytes();
		String s = null;
		char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
				'E', 'F' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
			// 用字节表示就是 16 个字节
			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
			// 所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
				// 转换成 16 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
				// >>> 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符串

		} catch (Exception e) {
			logger.error(password+" 获取MD5码出错", e);
//			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 16进制字符串转为字符串格式(数据库中的MD5值GBK乱码方式) 注：该方法已废弃，改为在服务端调用C#编写的COM组件加密
	 * 
	 * @param s
	 * @return
	 */
	public static String hexToString(String s) {
		Integer.parseInt(s, 16);
		try {
			byte[] tmp = new byte[s.length() / 2];
			for (int i = 0; i < tmp.length; i++) {
				tmp[i] = (byte) (0xff & Integer.parseInt(
						s.substring(i * 2, i * 2 + 2), 16));
			}
			s = new String(tmp, "GBK");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return s;
	}

	// public static void main(String[] args){
	//
	// String libPath = System.getProperty("java.library.path");
	// String[] paths = libPath.split(";");
	// for(String p:paths) {
	// System.out.println(p);
	// }
	// String pass = encode("1");
	// System.out.println(pass);
	// }

	/**
	 * 调用C#编写的COM组件加密密码，得出经过MD5加密后再GBK编码的字符串
	 * 
	 * @param password
	 * @return
	 */
	public static String encode(String password) {
		// String libPath = System.getProperty("java.library.path");
		// String[] paths = libPath.split(";");
		// for(String s:paths){
		// System.out.println(s);
		// }
		try {
			ActiveXComponent dotnetCom = new ActiveXComponent("A4S.Com.Global"); // 需要调用的C#代码中的命名空间名和类名。
			Variant var = Dispatch.call(dotnetCom, "MD5Hash", password); // 需要调用的方法名和参数值
			String r = var.getString();
			// fix 密码为‘1992’不能登录
			return r.replace(';', '!');
		}catch (Error e){
			e.printStackTrace();
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(password.getBytes());
				byte b[] = md.digest();
				String output = new String(b, "GBK");
				// 兼容上述替换
				output.replace(';', '!');
				return output;
			} catch (Exception e2) {
				e.printStackTrace();
				return "";
			}
		}
	}

	public static String encodeNew(String password) {
		if(null == password || "".equals(password)){
			return "";
		}
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes(Charset.forName("UTF-8")));
			byte b[] = md.digest();
			java.util.Base64.Encoder base64 = java.util.Base64.getEncoder();
			return base64.encodeToString(b);
		}catch (Exception ex){
			throw new ServiceException("加密错误("+password+")", ex);
		}
	}
	/**
	 * 解码SOAP头中的值
	 * 
	 * @param encodeds
	 * @return
	 */
	public static String decodeSoapHeader(String encodeds) {
		try {
			return new String(Base64.getDecoder().decode(encodeds));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
