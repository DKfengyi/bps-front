package com.bsb.bps.front.util;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.smy.framework.core.util.DateUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {

	// 判断电话
    public static boolean isTelephone(String phonenumber) {
        String phone = "1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}";
        Pattern p = Pattern.compile(phone);
        Matcher m = p.matcher(phonenumber);
        return m.matches();
    }

    // 判断手机号
    public static boolean isMobileNO(String mobiles) {
        //Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Pattern p = Pattern.compile("1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    // 判断邮箱
    public static boolean isEmail(String email) {
        String str = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
    //是否为登录密码
    public static boolean isLoginPwd(String pwd){
      String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$";
      return pwd.matches(regex);
    }
    //是否为正确的图形验证码
    public static boolean isIdentifyCode(String code){
      return code.matches("^[0-9A-Za-z]{4}$");
    }
    //验证支付密码
    public static boolean isPayPwd(String code){
      return code.matches("^[0-9]{6}$");
    }
    //身份证校验
	public static boolean idCheck(String idNo){

		if(StringUtils.isNotEmpty(idNo)){
			if(!DateUtil.checkDateFormat(idNo.substring(6,10) + idNo.substring(10,12) + idNo.substring(12,14))){
				 return false;
		    }
		}else{
			 return false;
		}
	    int idNoYear = Integer.parseInt(idNo.substring(6,10));
	    int startYear = 1912;
	    int currYear = Integer.parseInt(String.valueOf(DateUtil.getCurStringDate().substring(0,4)));
	    int getidNoYear = currYear - 16;//16岁可办身份证
	    if(idNoYear < startYear || idNoYear > getidNoYear){
	    	 return false;
	    }

	    // 龙帝规则校验
	    int length = idNo.length();
	    int count = 0;
	    for (int i = 0; i < length - 1; i++) {
	        if(i == 0){
	            count = count + Integer.parseInt(idNo.substring(i, i+1)) * 7;
	        }else if(i == 1){
	            count = count + Integer.parseInt(idNo.substring(i, i+1)) * 9;
	        }else if(i == 2){
	            count = count + Integer.parseInt(idNo.substring(i, i+1)) * 10;
	        }else if(i == 3){
	            count = count + Integer.parseInt(idNo.substring(i, i+1)) * 5;
	        }else if(i == 4){
	            count = count + Integer.parseInt(idNo.substring(i, i+1)) * 8;
	        }else if(i == 5){
	            count = count + Integer.parseInt(idNo.substring(i, i+1)) * 4;
	        }else if(i == 6){
	            count = count + Integer.parseInt(idNo.substring(i, i+1)) * 2;
	        }else if(i == 7){
	            count = count + Integer.parseInt(idNo.substring(i, i+1)) * 1;
	        }else if(i == 8){
	            count = count + Integer.parseInt(idNo.substring(i, i+1)) * 6;
	        }else if(i == 9){
	            count = count + Integer.parseInt(idNo.substring(i, i+1)) * 3;
	        }else if(i == 10){
	            count = count + Integer.parseInt(idNo.substring(i, i+1)) * 7;
	        }else if(i == 11){
	            count = count + Integer.parseInt(idNo.substring(i, i+1)) * 9;
	        }else if(i == 12){
	            count = count + Integer.parseInt(idNo.substring(i, i+1)) * 10;
	        }else if(i == 13){
	            count = count + Integer.parseInt(idNo.substring(i, i+1)) * 5;
	        }else if(i == 14){
	            count = count + Integer.parseInt(idNo.substring(i, i+1)) * 8;
	        }else if(i == 15){
	            count = count + Integer.parseInt(idNo.substring(i, i+1)) * 4;
	        }else if(i == 16){
	            count = count + Integer.parseInt(idNo.substring(i, i+1)) * 2;
	        }
	    }
	    String lastIdNoValue = "";
	    int modCount = count % 11;
	    if(modCount == 0){
	        lastIdNoValue = "1";
	    }else if(modCount == 1){
	        lastIdNoValue = "0";
	    }else if(modCount == 2){
	        lastIdNoValue = "X";
	    }else if(modCount == 3){
	        lastIdNoValue = "9";
	    }else if(modCount == 4){
	        lastIdNoValue = "8";
	    }else if(modCount == 5){
	        lastIdNoValue = "7";
	    }else if(modCount == 6){
	        lastIdNoValue = "6";
	    }else if(modCount == 7){
	        lastIdNoValue = "5";
	    }else if(modCount == 8){
	        lastIdNoValue = "4";
	    }else if(modCount == 9){
	        lastIdNoValue = "3";
	    }else if(modCount == 10){
	        lastIdNoValue = "2";
	    }

	    if(!idNo.endsWith(lastIdNoValue)){
	    	 return false;
	    }

	    return true;
    }

	/**
     * 手机号验证
     *
     * @param  str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
    /**
     * 是否为纯数字动态验证码
     * @param code
     * @return
     */
    public static boolean isDynamicCode(String code){
      return code.matches("^[0-9]{5}$");
    }

}
