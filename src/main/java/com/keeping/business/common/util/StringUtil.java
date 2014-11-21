package com.keeping.business.common.util;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	private static char[] specialChar = new char[]{' ','(',')','+','-','*','/'};
	/**
	 * 特殊字符的正则表达式
	 */
	public final static String regExpStr = "[\\s|\\#|\\%|\\$|\\&|\\'|\\@|\\+|\\*|\\?|\\(|\\)|\\[|\\]|<|>|/|^|!|~]";
	public final static String regExpStr2 = "[\\s|\\#|\\%|\\$|\\&|\\'|\\@|\\+|\\*|\\?|\\(|\\)|\\[|\\]|<|>|/|^|!|~|.|,|;]";

	public static  boolean isSpecialChar(char c){
		for (int i=0;i<specialChar.length;i++){
			if (specialChar[i] == c)
				return true;
		}
		return false;
	}
	/**
	 * 在str中查找所有符合正则表达式(regEx)并用str2替换
	 * @param str 传入的主字符串
	 * @param regEx:可以为空或空字符串,如果为空，则用工具类StringUtil中的regExpStr常量的值
	 * @param str2　替的字符串
	 * @return
	 */
	  public static String delSpecialStr(String str, String regEx,String str2) {
	        String newstr = "";
			try{
				if(str!=null && !str.equals("")){
					if(regEx==null || regEx.equals("")) regEx = regExpStr;
			        Pattern p = Pattern.compile(regEx);
			        Matcher m = p.matcher(str);
			        newstr = m.replaceAll(str2);
				}
			}catch(Exception e){
			}
	        return newstr;
	  }	
	
	/**
	 * 返回替换后的字符串
	 * @param mainString 主字符串
	 * @param oldString 被替换的字符串
	 * @param newString 新的字符串
	 * @return
	 */	
    public  static String replace(String mainString, String oldString, String newString) {
        if (mainString == null) {
            return null;
        }
        if (oldString == null || oldString.length() == 0) {
            return mainString;
        }
        if (newString == null) {
            newString = "";
        }

        int i = mainString.lastIndexOf(oldString);

        if (i < 0) return mainString;

        StringBuffer mainSb = new StringBuffer(mainString);

        while (i >= 0) {
            mainSb.replace(i, i + oldString.length(), newString);
            i = mainString.lastIndexOf(oldString, i - 1);
        }
        return mainSb.toString();
    }
    
    /**
     * 返回替换后的字符串从左边第一个开始替换
     * @param mainString 主字符串
     * @param oldString 被替换的字符串
     * @param newString 新的字符串
     * @param numb 要替换的字符的个数
     * @return
     */	
    public  static String replace(String mainString, String oldString, String newString,int numb) {
        if (mainString == null) {
            return null;
        }
        if (oldString == null || oldString.length() == 0) {
            return mainString;
        }
        if (newString == null) {
            newString = "";
        }

        StringBuffer mainSb = new StringBuffer(mainString);
        int count = 0;
        while (mainSb.indexOf(oldString)>=0 && count < numb) {
        	int i = mainSb.indexOf(oldString);
            mainSb.replace(i, i + oldString.length(), newString);
            count++;
        }
        return mainSb.toString();
    }
        
    /**
     * @param str 原字符串
     * @param fillStr 填充的字符串
     * @param length 需要的长度
     * @return 按需要的长度,把填充的字符串填充到原字符串的左边
     */
    public static String LFillStr(String str, String fillStr, int length) {
    	int i = length - str.length();
    	String mStr = "";
    	for (int j=0; j<i; j++)
    		mStr = mStr+fillStr;
    	mStr = mStr +str;
    	return mStr;
    }
    /**
     * @param str 原字符串
     * @param fillStr 填充的字符串
     * @param length 需要的长度
     * @return 按需要的长度,把填充的字符串填充到原字符串的右边
     */
    public static String RFillStr(String str, String fillStr, int length){
    	int i = length - str.length();
    	String mStr = "";
    	for (int j=0; j<i; j++)
    		mStr = mStr+fillStr;
    	mStr = str + mStr;
    	return mStr;
    }

    /**
     * 对安全字符串进行解码，返回原始字符串
     * @param stringTmp
     * @return
     */
    public static String decode(String stringTmp)
    {
        if(stringTmp == null)
            return "";
        String stringname = "";
        for(int i = 0; i < stringTmp.length(); i++)
        {
            char charint = stringTmp.charAt(i);
            switch(charint)
            {
            case 126: // '~'
                String stringtmp = stringTmp.substring(i + 1, i + 3);
                stringname = stringname + (char)Integer.parseInt(stringtmp, 16);
                i += 2;
                break;

            case 94: // '^'
                String stringtmp2 = stringTmp.substring(i + 1, i + 5);
                stringname = stringname + (char)Integer.parseInt(stringtmp2, 16);
                i += 4;
                break;

            default:
                stringname = stringname + charint;
                break;
            }
        }

        return stringname;
    }

    /**
     * 对原始字符串进行编码，返回安全字符串
     * @param stringname
     * @return
     */
    public static String encode(String stringname)
    {
        if(stringname == null)
            return "";
        String stringTmp = "";
        for(int i = 0; i < stringname.length(); i++)
        {
            int charat = stringname.charAt(i);
            if(charat > 255)
            {
                String tmp = Integer.toString(charat, 16);
                for(int j = tmp.length(); j < 4; j++)
				tmp = "0" + tmp;

                stringTmp = stringTmp + "^" + tmp;
            } else
            {
                if(charat < 48 || charat > 57 && charat < 65 || charat > 90 && charat < 97 || charat > 122)
                {
                    String stringat = Integer.toString(charat, 16);
                    for(int j = stringat.length(); j < 2; j++)
					stringat = "0" + stringat;

                    stringTmp = stringTmp + "~" + stringat;
                } else
                {
                    stringTmp = stringTmp + stringname.charAt(i);
                }
            }
        }

        return stringTmp;
    }

    /**
     * 对安全字符串进行解码，返回原始字符串
     * @param stringTmp
     * @return
     */
    public static byte[] decode2(String stringTmp)
    {
        if(stringTmp == null)
            return null;
        int pos = 0;
        byte[] dectmp = new byte[stringTmp.length()];
        for(int i = 0; i < stringTmp.length(); i++)
        {
            char charint = stringTmp.charAt(i);
            switch(charint)
            {
            case 126: // '~'
                String stringtmp = stringTmp.substring(i + 1, i + 3);
                dectmp[pos] = (byte)(char)Integer.parseInt(stringtmp, 16);
                i += 2;
                break;

            case 94: // '^'
                String stringtmp2 = stringTmp.substring(i + 1, i + 5);
                dectmp[pos] = (byte)(char)Integer.parseInt(stringtmp2, 16);
                i += 4;
                break;

            default:
            	dectmp[pos] = (byte)charint;
                break;
            }
            pos++;
        }
        byte[] decArr = new byte[pos];
        for (int i = 0; i < pos; i++){
        	decArr[i] = dectmp[i];
        }
        return decArr;
    }

    /**
     * 对原始字符串进行编码，返回安全字符串
     * @param stringname
     * @return
     */
    public static String encode2(byte[] data)
    {
        if(data == null)
            return "";
        String stringTmp = "";
        for(int i = 0; i < data.length; i++)
        {
            int charat = (char)data[i];
            if(charat > 255)
            {
                String tmp = Integer.toString(charat, 16);
                for(int j = tmp.length(); j < 4; j++)
				tmp = "0" + tmp;

                stringTmp = stringTmp + "^" + tmp;
            } else
            {
                if(charat < 48 || charat > 57 && charat < 65 || charat > 90 && charat < 97 || charat > 122)
                {
                    String stringat = Integer.toString(charat, 16);
                    for(int j = stringat.length(); j < 2; j++)
					stringat = "0" + stringat;

                    stringTmp = stringTmp + "~" + stringat;
                } else
                {
                    stringTmp = stringTmp + ((char)data[i]);
                }
            }
        }

        return stringTmp;
    }
      
    /**
     * 判断 list是否为空返回boolean型true或false
     * @param str 字符串
     * @return
     */    
    public static boolean isNull(String str)
    {
    	if (str==null || str.equals("null") || str.trim().length()==0)
    		return true;
    	else
    		return false;
    }
    
    
    /**
     * 判断 map是否为空返回boolean型true或false
     * @param map 集合
     * @return
     */   
    public static boolean isNull(Map<?,?> map) {
       	if (map==null || map.size()==0 )
       		return true;
       	else
       		return false;
    }
 
    /**
     * 判断 List是否为空返回boolean型true或false
     * @param List 集合
     * @return
     */   
    public static boolean isNull(List<?> list) {
       	if (list==null || list.size()==0 )
       		return true;
       	else
       		return false;
    }

	/**
	 * 把字段串按分隔符转换成数组，若空值，数组中也会留空值
	 * 
	 * @param str 原字段串
	 * @param delimiters 分隔符
	 * @return
	 */
	public static String[] tokenizeToStringArray(String str, String delimiters) {
		if (StringUtil.isNull(str))
			return null;
		String aa = new String(str + delimiters + "1");
		String[] result = aa.split(delimiters);
		return ArrayUtil.removeLastItem(result);
	}

	/**
	 * 字符串去掉最后几个字符。
	 * 
	 * @param str 原字段串
	 * @param lastLen 去掉最后几个字符
	 * @return 去掉最后几个字符的字符串
	 */
	public static String removeLastStr(String str, int lastLen) {
		if (isNull(str) || str.length() <= lastLen)
			return "";
		else
			return str.substring(0, str.length() - lastLen);
	}

	/**
	 * 字符串转换为ASCII码
	 * @param str
	 * @return
	 */
	 public static String strToAscii(String str){//
		  char[]chars=str.toCharArray(); //把字符中转换为字符数组 
		  String result = "";
		  for(int i=0;i<chars.length;i++){//输出结果
			  result += chars[i]; 
		  }
		  return result;
	}

	 /**
	  * 把ascii字符串转成对应的字符串
	  * @param ascii以空格分开
	 * @return
	  */
	 public static String asciiToStr(String ascii){//ASCII转换为字符串
		 String[]chars=ascii.split(" ");
		 StringBuffer sb = new StringBuffer();
		 for(int i=0;i<chars.length;i++){ 
        	sb.append(""+(char)Integer.parseInt(chars[i]));
		 } 
	     return sb.toString();   
	 }
}
