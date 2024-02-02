package com.arttuplayground.demolibrary.util;

import com.ninja_squad.dbsetup.bind.Binder;
import com.ninja_squad.dbsetup.bind.Binders;
import com.ninja_squad.dbsetup.bind.DefaultBinderConfiguration;

import java.sql.ParameterMetaData;
import java.sql.SQLException;
import java.sql.Types;

public class PostgresBinderConfiguration extends DefaultBinderConfiguration {

    /**
     * Adds support for Postgres type placeholders when the column metadata
     * indicates that the {@link Types} is OTHER (e.g. a custom type or
     * jsonb) See also {@link CustomTypeBinder} for implementation details
     */
    @Override
    public Binder getBinder(ParameterMetaData metadata, int param) throws SQLException {

        if (metadata == null) {
            return Binders.defaultBinder();
        }

        if (metadata.getParameterType(param) == Types.OTHER) {
            return new CustomTypeBinder();
        }

        return super.getBinder(metadata, param);
    }
}
