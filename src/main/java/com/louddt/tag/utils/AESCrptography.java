package com.louddt.tag.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Slf4j
public class AESCrptography {
	private final static String encoding = "UTF-8";
	public static final String KEY="dfjgbpoiwcknsyut";  
	public static final String IV="abcdefghijklmnop";
	
	public static void main(String[] args) {  
		  
    	String content = "A234567 123";
    	
    	/**  加密 */
    	String jia = getUnRealWord(content);
    	log.info("加密：" + jia);
    	
    	/**  解密 */
    	String pass =getRealWord(jia);
		log.info("解密：" + pass);
    	
    	
    } 
	
	/**
	 * 
	 * @Author DSN
	 * @Date 2018年3月14日
	 * @Desc 加密
	 */
	public static String getUnRealWord(String password){
		byte[] jiamihou = AES_CBC_Encrypt(password);
    	String jiamihoustr = parseByte2HexStr(jiamihou);
    	return jiamihoustr;
	}
	
	/**
	 * 
	 * @Author DSN
	 * @Date 2018年3月14日
	 * @Desc 解密
	 */
	public static String getRealWord(String password){
		if(StringUtils.isEmpty(password)) {
			return "";
		}
		String res="";
		byte[] tmp1 = parseHexStr2Byte(password);
    	byte[] tmp2 = AES_CBC_Decrypt(tmp1);
        res = new String (tmp2);
		return res;
	}
	
	/**
	 * AES加密
	 * 
	 * @param content
	 * @param password
	 * @return
	 */
	public static byte[] encrypt(String content, String password) {  
    	return AES_CBC_Encrypt(content);
	}

	/**
	 * AES解密
	 * 
	 * @param content
	 * @param password
	 * @return
	 */
	public static byte[] decrypt(byte[] content, String password) {
		return AES_CBC_Decrypt(content);
	}

	/**
	 * 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @return
	 */
	public static byte[] AES_CBC_Encrypt(String content) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			// 防止linux下 随机生成key
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(KEY.getBytes());
			keyGenerator.init(128, secureRandom);
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			//windows下 
//			keyGenerator.init(128, new SecureRandom(KEY.getBytes()));  
//            SecretKey key=keyGenerator.generateKey();
			
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 创建密码器
			byte[] byteContent = content.getBytes(encoding);
			cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes()));// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}catch (Exception e) {  
			e.printStackTrace(); 
        }
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @return
	 */
	public static byte[] AES_CBC_Decrypt(byte[] content) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			// 防止linux下 随机生成key
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(KEY.getBytes());
			keyGenerator.init(128, secureRandom);
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			//windows下
//			keyGenerator.init(128, new SecureRandom(KEY.getBytes()));//key长可设为128，192，256位，这里只能设为128  
//            SecretKey key=keyGenerator.generateKey();
			
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes()));// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}catch (Exception e) {  
            e.printStackTrace();  
        } 
		return null;
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
}
