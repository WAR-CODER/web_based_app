package wethinkcode.rdbms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Predicate;

/**
 * DO NOT MODIFY THIS CODE
 *
 *
 */
public class HistoryBooksValidator implements Predicate<ResultSet> {
    @Override
    public boolean test(ResultSet resultSet) {

        try {
            if (!resultSet.next()) return false;
            return resultSet.getInt(1) == 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
