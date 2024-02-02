package com.wartsila.demo.arttuplaygroundapi.util;

import com.ninja_squad.dbsetup.operation.Operation;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static com.ninja_squad.dbsetup.Operations.sql;
import static com.wartsila.demo.arttuplaygroundapi.Utils.*;
import static com.wartsila.demo.arttuplaygroundapi.util.UtilsTestEnv.*;


public class DBOperations {
    public DBOperations() {
    }


    public static final Operation CLEAN_DB = sequenceOf(
            deleteAllFrom(
                tblCar(),
                tblOwner(),
                tblGarage()
            ),
            insertInto(TBL_OWNER)
                .columns("id", "firstname", "lastname")
                    .values(0, "Giulio", "Rokka")
                    .values(1,"test","testtt")
                    .values(2,"tester","type").build(),
            insertInto(TBL_CAR)
                .columns("id", "brand", "year", "owner")
                .values(0, "Ferrari", 2023, 0)
                .values(1, "Lamborghini", 2022, 1)
                .values(2, "Mercedes", 2022, 2).build(),
            insertInto(TBL_GARAGE)
                .columns("id", "name", "location", "car")
                .values(0, "Private", "Ylästöntie 3", 1).build(),
            sql("ALTER sequence ".concat(TBL_CAR_SEQ).concat(" RESTART WITH 100")),
            sql("ALTER sequence ".concat(TBL_GARAGE_SEQ).concat(" RESTART WITH 100"))
    );
}
