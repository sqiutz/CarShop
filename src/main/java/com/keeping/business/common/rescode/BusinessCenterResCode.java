package com.keeping.business.common.rescode;

public enum BusinessCenterResCode {
	
	SYS_SUCCESS("000000", "成功"),
	
	SYS_ERROR("000001","系统繁忙，请稍候再试！"),
	SYS_REQ_ERROR("000002","请求信息不正确！"),
	SYS_NOT_EXIST("000003","相关信息不存在！"),
	SYS_INVILID_REQ("000004","无效的请求信息,请重新登录！"),
	SYS_NO_PRIVILEGE("000005","无效的操作请求！"),
	SYS_NO_AUTHORITY("000006","用户无此权限！"),
	SYS_NO_ADMIN("000007","用户无此权限！"),
	
	/**注册错误提示信息  */
	REGISTRY_EMAIL_EXISTED("010000", "邮箱地址已经被注册！"),
	REGISTRY_VCODE_ERROR("010001", "验证码错误！"),
	REGISTRY_VCODE_EXPIRED("010002", "验证码已经过期！"),
	REGISTRY_CONFIRM_FAIL("010003", "邮箱验证失败！"),
	
	/**登录错误提示信息  */
	LOGIN_USER_NOT_EXIST("010100", "用户不存在！"),
	LOGIN_USER_NOT_ALIVE("010101", "用户未激活！"),
    LOGIN_PASSWORD_NOT_RIGHT("010102", "密码不正确！"),
	
    /**修改密码错误信息 */
    MOD_PASSWORD_NOT_RIGHT("010105", "原始密码不正确！"),
	
	/**找回密码错误信息 */
	RETR_PASSWORD_NOT_RIGTH("010107", "无效注册邮箱或邮箱未激活！"),
	/**发送邮件出错  */
	RETR_PASSWORD_SEND_ERROR("010108", "发送至邮箱失败，请再点击找回密码！"),
	RETR_PASSWORD_CONFIRM_ERROR("010109", "找回密码验证过期！"),

	/**错误信息 */
	PRODUCT_NOT_EXIST("010200", "产品不存在！"),
	ENGNAME_ILLEGAL("010201", "32字符以内，英文、数字、下划线、中划线组成!"),
	PROJECT_NOT_EXIST("010300", "体验项目不存在！"),
	PROJECT_EXIST("010301", "已存在体验产品！"),
	NAME_EXIST("010301", "该名称已存在！"),
	TYPE_EXIST("010302", "该版本号已存在！");
    
	private String code;
	private String msg;

    private BusinessCenterResCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

    public static BusinessCenterResCode getByCode(String code) {
        if (code == null)
            return null;
        code = code.trim();
        for (BusinessCenterResCode value : values()) {
            if (value.getCode().equals(code))
                return value;
        }
        return null;
    }
}
