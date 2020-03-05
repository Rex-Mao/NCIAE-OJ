/* Vectory Online Judge - A cross-platform judge online system
 * Copyright (C) 2018 Haozhe Xie <cshzxie@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *                              _ooOoo_  
 *                             o8888888o  
 *                             88" . "88  
 *                             (| -_- |)  
 *                             O\  =  /O  
 *                          ____/`---'\____  
 *                        .'  \\|     |//  `.  
 *                       /  \\|||  :  |||//  \  
 *                      /  _||||| -:- |||||-  \  
 *                      |   | \\\  -  /// |   |  
 *                      | \_|  ''\---/''  |   |  
 *                      \  .-\__  `-`  ___/-. /  
 *                    ___`. .'  /--.--\  `. . __  
 *                 ."" '<  `.___\_<|>_/___.'  >'"".  
 *                | | :  `- \`.;`\ _ /`;.`/ - ` : | |  
 *                \  \ `-.   \_ __\ /__ _/   .-` /  /  
 *           ======`-.____`-.___\_____/___.-`____.-'======  
 *                              `=---=' 
 *
 *                          HERE BE BUDDHA
 *
 */
package cn.edu.nciae.judgecenter.voj.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * 生成随机字符串类.
 * @author Haozhe Xie
 */
public class DigestUtils {
	/**
	 * Utility classes should not have a public constructor. 
	 */
	private DigestUtils() { }
	
	/**
	 * 生成GUID.
	 * @return GUID
	 */
	public static String getGuid() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 获取MD5加密后的密码.
	 * @param password - 未经MD5加密的密码
	 * @return MD5加密后的密码
	 */
	public static String md5Hex(String password) {
		String md5 = "";
		if ( password == null || password.length() == 0 ) {
			return "";
		}
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			 byte[] byteData = md.digest();
	 
			StringBuilder sb = new StringBuilder();
			for (byte byteDatum : byteData) {
				sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
			}
			md5 = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md5;
	}
	
	/**
	 * 生成随机字符串.
	 * @param length - 字符串长度
	 * @param mode - 字符串生成模式
	 * @return 随机字符串
	 */
	public static String getRandomString(int length, Mode mode) {
		StringBuilder builder = new StringBuilder();
		String characters = "";

		switch(mode) {
			case ALPHA:
				characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
				break;
			case ALPHANUMERIC:
				characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
				break;
			case NUMERIC:
				characters = "1234567890";
				break;
			default: break;
		}
		
		int charactersLength = characters.length();
		for ( int i = 0; i < length; ++ i ) {
			double index = Math.random() * charactersLength;
			builder.append(characters.charAt((int) index));
		}
		return builder.toString();
	}
	
	/**
	 * 字符串生成模式枚举类.
	 */
	public static enum Mode { ALPHA, ALPHANUMERIC, NUMERIC }
}
