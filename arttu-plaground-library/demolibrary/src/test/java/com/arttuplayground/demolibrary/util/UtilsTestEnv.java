package com.arttuplayground.demolibrary.util;

import static com.arttuplayground.demolibrary.Utils.SCHEMA;
import static com.arttuplayground.demolibrary.Utils.TBL_COMMON_ENTITY;

public final class UtilsTestEnv {
    private UtilsTestEnv() {
    }

    public static String tblCommonEntity() {
        return String.format("%s.%s", SCHEMA, TBL_COMMON_ENTITY);
    }
}
