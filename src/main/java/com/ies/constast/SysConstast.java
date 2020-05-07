package com.ies.constast;

/**
 * 常量接口
 */
public interface SysConstast {

    Integer CODE_SUCCESS = 1;
    Integer CODE_ERROR = -1;

    /**
     * 可用状态
     */
    Integer AVAILABLE_TRUE = 1;
    Integer AVAILABLE_FALSE = 0;

    /**
     * 用户类型
     */
    Integer USER_TYPE_SUPER = 1;
    Integer USER_TYPE_NORMAL = 2;

    /**
     * 是否展开
     */
    Integer SPREAD_TRUE = 1;
    Integer SPREAD_FALSE = 0;

    Integer STOCK_RETURN = -1;
    Integer STOCK_INIT = 0;
    Integer STOCK_USED = 1;

    /**
     * 公用常量
     */
    Integer CODE_ZERO = 0;
    Integer CODE_ONE = 1;
    Integer CODE_TWO = 2;
    Integer CODE_THREE = 3;

    /**
     * 用户默认密码
     */
    String USER_DEFAULT_PWD="123456";

    /**
     * 临时文件标记
     */
    String FILE_UPLOAD_TEMP = "_temp";

    /**
     * 默认图片地址
     */
    Object DEFAULT_CAR_IMG = "images/defaultcountryimage.jpg";




}
