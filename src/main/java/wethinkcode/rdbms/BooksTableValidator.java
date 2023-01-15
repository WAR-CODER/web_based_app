package wethinkcode.rdbms;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DO NOT MODIFY THIS CODE
 *
 *
 */
public class BooksTableValidator extends AbstractTableValidator {

    public BooksTableValidator(String tablename) {
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
        return rs.getString("COLUMN_NAME").equals("id");
    }

    @Override
    public boolean validateForeignKey(ResultSet rs) throws SQLException {
        if (!rs.next()) return false;
        return rs.getString("PKTABLE_NAME").equals("Genres") &&
               rs.getString("PKCOLUMN_NAME").equals("code") &&
               rs.getString("FKTABLE_NAME").equals("Books") &&
               rs.getString("FKCOLUMN_NAME").equals("genre_code");
    }

    private boolean isColumnCorrect(String column, ResultSet rs) throws SQLException {
        return switch (column) {
            case "id" -> rs.getString("TYPE_NAME").equals("INTEGER") &&
                    rs.getString("IS_NULLABLE").equals("NO") &&
                    rs.getString("IS_AUTOINCREMENT").equals("YES");
            case "title" -> rs.getString("TYPE_NAME").equals("TEXT") &&
                    rs.getString("IS_NULLABLE").equals("NO");
            case "genre_code" -> rs.getString("TYPE_NAME").equals("TEXT") &&
                    rs.getString("IS_NULLABLE").equals("NO");
            default -> false;
        };
    }
}
