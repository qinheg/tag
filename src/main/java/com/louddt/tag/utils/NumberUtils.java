package com.louddt.tag.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class NumberUtils {

    /**
     * 数据库数字类型的默认值为0
     *
     * @return true 为空
     */
    public static boolean numberBlank(final Number num) {
        if (num == null) {
            return true;
        }
        if (num instanceof Integer) {
            if (num.intValue() == 0) {
                return true;
            }
        }
        if (num instanceof Long) {
            if (num.longValue() == 0L) {
                return true;
            }
        }
        if (num instanceof Byte) {
            if (num.byteValue() == 0) {
                return true;
            }
        }
        if (num instanceof Short) {
            if (num.shortValue() == 0) {
                return true;
            }
        }
        return false;
    }

    public static Number isNullNumberDefault(final Number num) {
        return numberBlank(num) ? 0 : num;
    }

    public static BigDecimal isNullBigDecimalDefault(BigDecimal decimal) {
        return decimal == null ? BigDecimal.ZERO : decimal;
    }


    public static final DecimalFormat DF_2_SCALE = new DecimalFormat(("#.00"));

    /***
     * 转换BigDecimal为2位小数
     */
    public static final BigDecimal bigDecimal2Scale(BigDecimal bigDecimal) {
        return new BigDecimal(DF_2_SCALE.format(bigDecimal));
    }
}
