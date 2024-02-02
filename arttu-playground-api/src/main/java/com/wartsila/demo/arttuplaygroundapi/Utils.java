package com.wartsila.demo.arttuplaygroundapi;

public final class Utils {
    private Utils() {
    }

    public static final String SCHEMA = "public";
    public static final String TBL_CAR = "t_car";
    public static final String TBL_CAR_SEQ = SCHEMA + "." + TBL_CAR + "_id_seq";
    public static final String TBL_OWNER = "t_owner";
    public static final String TBL_OWNER_SEQ = SCHEMA + "." + TBL_CAR + "_id_seq";
    public static final String TBL_GARAGE = "t_garage";
    public static final String TBL_GARAGE_SEQ = SCHEMA + "." + TBL_GARAGE + "_id_seq";

}
