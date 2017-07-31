package com.leeyom.utils;

import sun.misc.BASE64Decoder;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
* 字符串处理类
* @ClassName: StringUtil 
* @Description: TODO
* @author leeyom 
* @company: leaderment.com
* @date:  2017年4月6日 上午10:13:19
 */
public class StringUtils {

	/**
	 * 将String数组转换为String
	 * 
	 * @param strArray
	 * @return
	 */
	public static String array2String(String[] strArray) {

		StringBuffer strBuffer = new StringBuffer();

		for (int i = 0; i < strArray.length; i++) {
			strBuffer.append(strArray[i]);
		}
		return strBuffer.toString();
	}

	/**
	 * 以type为分界符分离str字符,返回分离后的数组
	 * 
	 * @param str
	 *            待处理字符串
	 * @param type
	 *            分界符
	 * @return array 处理后的字符串数数组
	 */
	public static String[] split(String str, String type) {

		int begin = 0;
		int pos = 0;
		String tempstr = "";
		String[] array = null;
		Vector vec = null;
		int len = str.trim().length();
		str = str + type;

		if (len > 0) {
			while (str.length() > 0) {

				if (vec == null) {
					vec = new Vector();
				}

				pos = str.indexOf(type, begin);
				tempstr = str.substring(begin, pos);
				str = str.substring(pos + type.length(), str.length());
				vec.add(tempstr);
			}
		}
		if (vec != null) {
			array = new String[vec.size()];

			for (int i = 0; i < vec.size(); i++) {
				array[i] = (String) vec.get(i);
			}
		} else {
			array = new String[0];
		}

		return array;
	}

	/**
	 * 按长度把字符串前�?0
	 * 
	 * @param s
	 *            �?要补位字符串对象
	 * @return len 该字符串的长�?
	 */
	public static String len(String s, int len) {

		if (!notNull(s))
			s = "";

		int length = len - s.length();
		for (int i = 0; i < length; i++) {
			s = "0" + s;
		}
		return s;
	}

	/**
	 * 判断字符串是否为�?
	 * 
	 * @param s
	 *            �?要判断字符串对象
	 * @return true 表示不为�? false 为空
	 * @throws Exception
	 */
	public static boolean notNull(String s) {

		if (s == null || s.trim().length() <= 0 || "".equals(s) || "null".equals(s))
			return false;
		else
			return true;
	}

	/**
	 * 返回�?个布尔�?�，表示两个字符串是否相�?
	 *
	 * @param objstr
	 *            字符串对�?
	 * @param bjstr
	 *            字符串对�?
	 * @return false 表示不相�? true 相等
	 * @throws Exception
	 */
	public static boolean equals(String objstr, String bjstr) {

		boolean lean = false;
		lean = (objstr != null) && objstr.equals(bjstr);

		return lean;
	}

	/**
	 * 替换source中的str1为str2
	 * 
	 * @param source
	 *            待替换的字符�?
	 * @param str1
	 *            待替换的字符
	 * @param str2
	 *            替换的字�?
	 * @return java.lang.String 替换后的字符�?
	 */
	public static String replace(String source, char str1, String str2) {
		return replace(source, String.valueOf(str1), str2);
	}

	/**
	 * 替换source中的str1为str2
	 * 
	 * @param source
	 *            待替换的字符�?
	 * @param str1
	 *            待替换的字符
	 * @param str2
	 *            替换的字�?
	 * @return java.lang.String 替换后的字符�?
	 */
	public static String replace(String source, String str1, String str2) {
		if (source == null)
			return "";
		String desc = "";
		int posstart = 0;
		int posend = source.indexOf(str1, 0);
		int len = source.length();
		while (posend >= 0 && posstart < len) {
			desc += source.substring(posstart, posend) + str2;
			posstart = posend + str1.length();
			posend = source.indexOf(str1, posstart);
		}
		if (posstart < len)
			desc += source.substring(posstart, len);
		return desc;
	}

	/**
	 * 替换source中的"\n"�?"换行�?"
	 * 
	 * @param source
	 *            待替换的字符�?
	 * @return java.lang.String 替换后的字符�?
	 */
	/**
	 * public static String replace(String content) { String[] tempValue = null;
	 * if (content != null) { if (content.indexOf("\n") != -1) { tempValue =
	 * StringUtil.split(content, "\n"); } if (tempValue != null &&
	 * tempValue.length > 0) { content = ""; for (int i = 0; i <
	 * tempValue.length; i++) { content += tempValue[i] + "<br>"; } content =
	 * content.substring(0, content.length() - 4); } } return content; }
	 */

	/**
	 * 替换source中的"\n"�?"换行�?"
	 * 
	 * @param source
	 *            待替换的字符�?
	 * @return java.lang.String 替换后的字符�?
	 */
	public static String replace2(String content) {
		String[] tempValue = null;
		StringBuffer sb = new StringBuffer();
		if (content != null) {
			if (content.indexOf("\r\n") != -1) {
				tempValue = StringUtils.split(content, "\r\n");
			}
			if (tempValue != null && tempValue.length > 0) {
				content = "";
				for (int i = 0; i < tempValue.length; i++) {
					sb.append(tempValue[i]);
				}
			} else {
				sb.append(content);
			}
		}
		return sb.toString();
	}

	public static Object[] list2Values(List list) {
		Object[] strs = new Object[list.size()];
		for (int i = 0; i < strs.length; i++) {
			strs[i] = list.get(i);
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" + strs[i]);
		}
		System.out.println("返回的值得数组" + strs);
		return strs;
	}

	/**
	 * 将字符串列表转换成字符串数组
	 * 
	 * @param list
	 *            List 字符串列�?
	 * @return String[]
	 */
	public static String[] list2Array(List list) {
		String[] strs = new String[list.size()];
		for (int i = 0; i < strs.length; i++) {
			strs[i] = (String) list.get(i);
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" + strs[i]);
		}
		System.out.println("返回的值得数组" + strs);
		return strs;
	}

	/**
	 * 将字符串数组转换成字符串列表
	 * 
	 * @param array
	 *            String[] array 字符串数�?
	 * @return List 字符串列�?
	 */
	public static List array2List(String[] array) {
		List list = new ArrayList();
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				list.add(array[i]);
			}
		}
		return list;
	}

	/**
	 * 过滤Html的特殊字�?
	 */
	public static String htmlEscape(String in) {
		StringBuffer out = new StringBuffer();
		for (int i = 0; i < in.length(); i++) {
			char c = in.charAt(i);
			switch (c) {
			case '"':
				out.append("&quot;");
				break;
			case '&':
				out.append("&amp;");
				break;
			case '<':
				out.append("&lt;");
				break;
			case '>':
				out.append("&gt;");
				break;
			default:
				out.append(c);
				break;
			}
		}
		return out.toString();
	}

	/**
	 * 将字符串中的大写字母转化�?"_"加小写字�?
	 */
	public static String capTurn(String sentence) {
		char[] retChars = sentence.toCharArray();
		String retString = "";
		for (int i = 0; i < retChars.length; i++) {
			char curchar = retChars[i];

			if (Character.isUpperCase(curchar)) {
				retString = retString + "_" + Character.toLowerCase(curchar);
			} else {
				retString = retString + curchar;
			}
		}
		return retString;
	}

	public static Object dealData(Object obj) {
		Object object = null;
		if (obj instanceof Integer || obj instanceof Long || obj instanceof Double) {
			object = obj == null ? "0" : obj;
		}
		if (obj instanceof String) {
			// 用来处理‘附加信息’文本框内容换行报错的问题
			// obj = ((String) obj).replace("\n","<br/>");
			object = obj == null ? "" : ((String) obj).replaceAll("\\s*", "").replaceAll("\"", "").replace("\n", "");
		}
		if (obj instanceof Date) {
			object = (obj == null) ? "" : DateUtils.getString((Date) obj);
		}
		if (object == null) {
			object = "";
		}
		return object;
	}

	/**
	 * 把文本编码为Html代码
	 * 
	 * @param target
	 * @return 编码后的字符�?
	 */
	public static String htmEncode(String target) {
		StringBuffer stringbuffer = new StringBuffer();
		int j = target.length();
		for (int i = 0; i < j; i++) {
			char c = target.charAt(i);
			switch (c) {
			case 60:
				stringbuffer.append("&lt;");
				break;
			case 62:
				stringbuffer.append("&gt;");
				break;
			case 38:
				stringbuffer.append("&amp;");
				break;
			case 34:
				stringbuffer.append("&quot;");
				break;
			case 169:
				stringbuffer.append("&copy;");
				break;
			case 174:
				stringbuffer.append("&reg;");
				break;
			case 165:
				stringbuffer.append("&yen;");
				break;
			case 8364:
				stringbuffer.append("&euro;");
				break;
			case 8482:
				stringbuffer.append("&#153;");
				break;
			case 13:
				if (i < j - 1 && target.charAt(i + 1) == 10) {
					stringbuffer.append("<br>");
					i++;
				}
				break;
			case 32:
				if (i < j - 1 && target.charAt(i + 1) == ' ') {
					stringbuffer.append(" &nbsp;");
					i++;
					break;
				}
			default:
				stringbuffer.append(c);
				break;
			}
		}
		return new String(stringbuffer.toString());
	}

	/**
	 * base64解码
	 * 
	 * @param context
	 * @return
	 */
	public static String decodeString(String context) {
		BASE64Decoder decoder = new BASE64Decoder();
		try {

			byte[] fileNameStr = decoder.decodeBuffer(context);
			context = new String(fileNameStr, "UTF-8");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return context;
	}

	/**
	 * base64编码
	 * 
	 * @author: leeyom
	 * @company: leaderment.com
	 * @date: 2017年3月21日 下午12:02:46
	 * @param @return
	 * @return String
	 */
	public static String encodeString(String str) {
		String base64Str = null;
		try {
			base64Str = new sun.misc.BASE64Encoder().encode(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return base64Str;
	}
}
