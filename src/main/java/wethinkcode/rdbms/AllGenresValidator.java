package wethinkcode.rdbms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * DO NOT MODIFY THIS CODE
 *
 *
 */
public class AllGenresValidator implements Predicate<ResultSet> {
    @Override
    public boolean test(ResultSet resultSet) {

        Map<String, String> rows = new HashMap<>();
        try {
            while (resultSet.next()) {
               rows.put(resultSet.getString("code"), resultSet.getString("description"));
            }
            return checkRows(rows);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkRows(Map<String, String> rows) {
        List<String> codes = Arrays.asList("PROG", "BIO", "SCIFI");
        if (rows.size() != codes.size()) return false;
        return rows.keySet().containsAll(codes);
    }
}
