package wethinkcode.rdbms;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DO NOT MODIFY THIS CODE
 *
 *
 */
public abstract class AbstractTableValidator implements TableValidator {
    protected final String tablename;

    public AbstractTableValidator(String tablename) {
        this.tablename = tablename;
    }

    public abstract boolean validateColumns(ResultSet rs) throws SQLException;
    public abstract boolean validatePrimaryKey(ResultSet rs) throws SQLException;
    public abstract boolean validateForeignKey(ResultSet rs) throws SQLException;

    public boolean validate(DatabaseMetaData metaData) throws SQLException {
        return verifyTableExists(metaData) &&
                verifyColumns(metaData) &&
                verifyPrimaryKeys(metaData) &&
                verifyForeignKeys(metaData);
    }

    private boolean verifyTableExists(DatabaseMetaData metaData) throws SQLException {
        try (final ResultSet rs = metaData.getTables(null, "sqlite_master", tablename, new String[]{"TABLE"})) {
            if (rs.next()) return true;
        }
        return false;
    }

    private boolean verifyColumns(DatabaseMetaData metaData) throws SQLException {
        try (final ResultSet rs = metaData.getColumns(null, "sqlite_master", tablename, null)) {
            if (validateColumns(rs)) return true;
        }
        return false;
    }

    private boolean verifyPrimaryKeys(DatabaseMetaData metaData) throws SQLException {
        try (final ResultSet rs = metaData.getPrimaryKeys(null, "sqlite_master", tablename)) {
            if (validatePrimaryKey(rs)) return true;
        }
        return false;
    }

    private boolean verifyForeignKeys(DatabaseMetaData metaData) throws SQLException {
        try (final ResultSet rs = metaData.getImportedKeys(null, "sqlite_master", tablename)) {
            if (validateForeignKey(rs)) return true;
        }
        return false;
    }
}
