package com.arttuplayground.demolibrary.util;

import com.ninja_squad.dbsetup.operation.Operation;

import static com.arttuplayground.demolibrary.Utils.TBL_COMMON_ENTITY;
import static com.arttuplayground.demolibrary.Utils.TBL_COMMON_ENTITY_SEQ;
import static com.arttuplayground.demolibrary.util.UtilsTestEnv.tblCommonEntity;
import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static com.ninja_squad.dbsetup.Operations.sql;

public class DBOperations {
    public DBOperations() {
    }

    public static final Operation CREATE_SCHEMA = sequenceOf(
            sql("CREATE SCHEMA IF NOT EXISTS public;"),
            sql("CREATE TABLE IF NOT EXISTS public.".concat(TBL_COMMON_ENTITY).concat(" (id BIGSERIAL PRIMARY KEY, generic_value VARCHAR(255))"))
    );
    public static final Operation CLEAN_DB = sequenceOf(
            sql("ALTER sequence ".concat(TBL_COMMON_ENTITY_SEQ).concat(" RESTART WITH 100")),
            deleteAllFrom(
                tblCommonEntity()
            ),
            insertInto(TBL_COMMON_ENTITY)
                .columns("generic_value")
                .values("shelby@cobra.gt").build()
    );
}
