package wethinkcode.rdbms;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DO NOT MODIFY THIS CODE
 *
 *
 */
public class GenresTableValidator extends AbstractTableValidator {

    public GenresTableValidator(String tablename) {
        super(tablename);
    }

    @Override
    public boolean validateColumns(ResultSet rs) throws SQLException {
        while (rs.next()) {
            if (!isColumnCorrect(rs.getString("COLUMN_NAME"), rs)) return false;
        }
        return true;
    }

    @Override
    public boolean validatePrimaryKey(ResultSet rs) throws SQLException {
        if (!rs.next()) return false;
        return rs.getString("COLUMN_NAME").equals("code");
    }

    @Override
    public boolean validateForeignKey(ResultSet rs) {
        return true;
    }

    private boolean isColumnCorrect(String column, ResultSet rs) throws SQLException {
        return switch (column) {
            case "code" -> rs.getString("TYPE_NAME").equals("TEXT") &&
                    rs.getString("IS_NULLABLE").equals("NO");
            case "description" -> rs.getString("TYPE_NAME").equals("TEXT") &&
                    rs.getString("IS_NULLABLE").equals("NO");
            default -> false;
        };
    }
}
