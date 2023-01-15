package wethinkcode.rdbms;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * DO NOT MODIFY THIS CODE
 *
 *
 */
public interface TableValidator {
    boolean validate(DatabaseMetaData metaData) throws SQLException;
}
