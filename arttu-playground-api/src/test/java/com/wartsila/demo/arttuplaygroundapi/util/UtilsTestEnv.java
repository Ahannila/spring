package com.wartsila.demo.arttuplaygroundapi.util;

import static com.wartsila.demo.arttuplaygroundapi.Utils.*;

public final class UtilsTestEnv {
    private UtilsTestEnv() {
    }

    public static String tblCar() {
        return String.format("%s.%s", SCHEMA, TBL_CAR);
    }

    public static String tblOwner() {return String.format("%s.%s", SCHEMA, TBL_OWNER); }

    public static String tblGarage() {return String.format("%s.%s", SCHEMA, TBL_GARAGE); }
}
