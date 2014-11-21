package com.keeping.business.common.util;

public class ArrayUtil {
	/**
	 * 数组去掉最后一个项目
	 * @param strArr
	 * @return
	 */
	public static String[] removeLastItem(String[] strArr){
		if(strArr==null || strArr.length<2)
			return null;
		String[] result = new String[strArr.length-1];
		for (int i=0;i<strArr.length-1;i++){
			result[i]=strArr[i];
		}
		return result;
	}
	
	/**
	 * 数组去掉第一个项目
	 * @param strArr
	 * @return
	 */
	public static String[] removeFirstItem(String[] strArr){
		if(strArr==null || strArr.length<2)
			return null;
		String[] result = new String[strArr.length-1];
		for (int i=1;i<strArr.length;i++){
			result[i-1]=strArr[i];
		}
		return result;
	}
	
	/**
     * 字符串数组转字符串，列用平台定义的参数列分隔符
     * @param strArr
     * @return
     */
    public static String stringArr2String(String[] strArr, String delimiters){
    	StringBuffer sb = new StringBuffer();
    	for (int i=0;i<strArr.length;i++){
			sb.append(strArr[i] + delimiters);
		} 
    	return StringUtil.removeLastStr(sb.toString(), delimiters.length()) ;
    }

    /**
     * 长整形数组转字符串数组
     * @param longArr
     * @return
     */
    public static String[] longArr2StringArr(long[] longArr){
    	String[] res = new String[longArr.length];
    	for (int i=0;i<longArr.length;i++){
    		res[i]=String.valueOf(longArr[i]);
		} 
    	return res;
    }
    
    /**
     * 把第二个数组的内容加到第一个数组
     * @param longArr
     * @return
     */
    public static long[] longArr2Add(long[] longArr,long[] longArr2 ){
    	for (int i=0;i<longArr.length;i++){
			longArr[i]+=longArr2[i];
		} 
    	return longArr;
    }
  
    /**
     * 克隆数组
     * @param doubleArr
     * @return
     */
    public static double[][] clone(double[][] doubleArr){
    	int rowLen = doubleArr.length;
    	int colLen = doubleArr[0].length;
    	double[][] reArr = new double[rowLen][colLen];
    	for (int i=0;i<rowLen;i++){
    		for (int j=0;j<colLen;j++)
    			reArr[i][j]=doubleArr[i][j];
    	}
    	return reArr;
    }
    
    /**
     * 克隆数组
     * @param doubleArr
     * @return
     */
    public static double[] clone(double[] doubleArr){
    	int len = doubleArr.length;
    	double[] reArr = new double[len];
    	for (int i=0;i<len;i++){
    			reArr[i]=doubleArr[i];
    	}
    	return reArr;
    }
    
    /**
     * 克隆数组
     * @param stringArr
     * @return
     */
    public static String[] clone(String[] stringArr){
    	int len = stringArr.length;
    	String[] reArr = new String[len];
    	for (int i=0;i<len;i++){
    			reArr[i]=new String(stringArr[i]);
    	}
    	return reArr;
    }
    
    
    /**
     * 把数组arr2中的值写入到arr1中,数组arr1和arr2的长度可能不等
     * @param arr1
     * @param arr2
     */
    public static void copyArray(String[] arr1,String[] arr2){
    	int n1= arr1.length;
    	int n2= arr2.length;
    	int numb = n1;
    	if(n1>n2) numb = n2;
    	for(int i=0;i<numb;i++){
    		arr1[i] = arr2[i];
    	}
    }
}
