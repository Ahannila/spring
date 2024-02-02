package com.arttuplayground.demolibrary.util;

import com.ninja_squad.dbsetup.bind.Binder;
import org.postgresql.util.PGobject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomTypeBinder implements Binder {

    private final Pattern objectTypePattern = Pattern.compile("(.*?)::(.*?)");

    @Override
    public void bind(PreparedStatement statement, int paramIndex, Object value) throws SQLException {
        if (Objects.isNull(value)) {

            statement.setObject(paramIndex, value);
            return;
        }

        Matcher m = objectTypePattern.matcher(value.toString());

        if (!m.matches()) {
            if (value instanceof UUID) {
                statement.setObject(paramIndex, value);
            } else {
                throw new SQLException(
                    "Unable to determine custom type - expected value::objectType syntax, value was: " +
                        value);
            }
        } else {
            String bareValue = m.group(1);
            String type = m.group(2);

            PGobject obj = new PGobject();
            obj.setType(type);
            obj.setValue(bareValue);

            statement.setObject(paramIndex, obj);

        }
    }
}
