package com.louddt.tag.utils;

import org.springframework.util.StringUtils;

public class ResourceUtils {

    /**
     * 获取文件的扩展名：kaka.jpeg -> jpeg
     */
    public static final String suffix(String name) {
        if (StringUtils.isEmpty(name) || name.indexOf(SymbolConstants.DOT) < 0) {
            return SymbolConstants.EMPTY;
        }
        return name.substring(name.lastIndexOf(SymbolConstants.DOT) + 1).toLowerCase();
    }
}
