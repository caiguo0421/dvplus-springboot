package cn.sf_soft.common.util;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;




import java.util.Base64;

public class DES {
	
	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DES.class);
	// 算法名称
	public static final String KEY_ALGORITHM = "DES";
	// 算法名称/加密模式/填充方式
	// DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
	public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";
		
	/**
	 * 生成密钥
	 * @return
	 */
	public static String generateKey(){
		return "yRVW$$A)^7828(4no!BiMms0)c+r4(0xw8fOT)DCk25$8Oh42+!5heMyTw6(";
	}
	
	/**
	 * 生成密钥
	 */
	public static String initkey() throws NoSuchAlgorithmException {
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM); // 实例化密钥生成器
		kg.init(56); // 初始化密钥生成器
		SecretKey secretKey = kg.generateKey(); // 生成密钥
		return Base64.getEncoder().encodeToString(secretKey.getEncoded()); // 获取二进制密钥编码形式
	}

	/**
	 * 转换密钥
	 */
	private static Key toKey(byte[] key) throws Exception {
		DESKeySpec dks = new DESKeySpec(key); // 实例化Des密钥
		SecretKeyFactory keyFactory = SecretKeyFactory
				.getInstance(KEY_ALGORITHM); // 实例化密钥工厂
		SecretKey secretKey = keyFactory.generateSecret(dks); // 生成密钥
		return secretKey;
	}

	/**
	 * 加密数据
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return 加密后的数据
	 */
	public static String encrypt(String data, String key) throws Exception {
		Key k = toKey(key.getBytes());// 还原密钥
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM); // 实例化Cipher对象，它用于完成实际的加密操作
		cipher.init(Cipher.ENCRYPT_MODE, k); // 初始化Cipher对象，设置为加密模式
		return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes())); // 执行加密操作。加密后的结果通常都会用Base64编码进行传输
	}

	/**
	 * 解密数据
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return 解密后的数据
	 */
	public static String decrypt(String data){
		try {
			Key k = toKey(generateKey().getBytes());
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, k); // 初始化Cipher对象，设置为解密模式
			return new String(cipher.doFinal(Base64.getDecoder().decode(data))); // 执行解密操作
		} catch (Exception e) {
			logger.error("解密数据出错", e);
//			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解密数据
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return 解密后的数据
	 */
	public static String decrypt(String data, String key){
		try {
			Key k = toKey(key.getBytes());
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, k); // 初始化Cipher对象，设置为解密模式
			return new String(cipher.doFinal(Base64.getDecoder().decode(data))); // 执行解密操作
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(generateKey());
	}
}
