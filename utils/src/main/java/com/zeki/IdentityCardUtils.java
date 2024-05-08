package com.zeki;

import java.time.LocalDateTime;

import static java.lang.Integer.parseInt;

/**
 * @description: 身份证工具类
 * @date 2018/11/12  16:53
 */
public class IdentityCardUtils {

    /**
     * 获取年
     * @param idNumber
     * @return
     */
    public static int getYear(String idNumber) {
      return parseInt(idNumber.substring(6, 10));
    }

    /**
     * 获取月
     * @param idNumber
     * @return
     */
    public static int getMonth(String idNumber) {
      return parseInt(idNumber.substring(10, 12));
    }

    /**
     * 获取日
     * @param idNumber
     * @return
     */
    public static int getDay(String idNumber) {
        return parseInt(idNumber.substring(12,14));
    }

    /**
     * 获取出生年月日
     * @param idNumber
     * @return
     */
    public static int getYearMonthDay(String idNumber) {
        return parseInt(idNumber.substring(6, 14));
    }

    /**
     * 获取指定格式化之后的出生年月日
     * @param idNumber
     * @return
     */
    public static String getFormatYMD(String idNumber, String format) {
        int year = IdentityCardUtils.getYear(idNumber);
        int month = IdentityCardUtils.getMonth(idNumber);
        int day = IdentityCardUtils.getDay(idNumber);
        return new StringBuffer(year).append(format).append(month).append(format).append(day).toString();
    }

    /**
     * 获取性别
     * @param idNumber 1：男 0：女
     * @return
     */
    public static int getSex(String idNumber) {
        String val = null;
        if (idNumber.length() == 15) {
            val = idNumber.substring(14, 15);
        } else if (idNumber.length() == 18) {
            val = idNumber.substring(16, 17);
        }
        if (Integer.parseInt(val) % 2 == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * 获取年龄
     * @param idNumber
     * @return
     */
    public static int getAge(String idNumber) {
        int currentYear = LocalDateTime.now().getYear();
        int age = currentYear - getYear(idNumber);
        int month = getMonth(idNumber);
        int currentMonth = LocalDateTime.now().getMonthValue();
        if (month > currentMonth) {
            -- age;
        }
        return age;
    }

    //身份证正则
    //String regexp = "^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|31)|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}([0-9]|x|X)$"

}
