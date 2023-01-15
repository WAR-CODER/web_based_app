package wethinkcode.rdbms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * DO NOT MODIFY THIS CODE
 *
 *
 */
public class AllBooksValidator implements Predicate<ResultSet> {
    @Override
    public boolean test(ResultSet resultSet) {

        Map<String, String> rows = new HashMap<>();
        try {
            while (resultSet.next()) {
               rows.put(resultSet.getString("title"), resultSet.getString("description"));
            }
            return checkRows(rows);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkRows(Map<String, String> rows) {
        Map<String, String> expected = Map.of(
                "Test Driven Development", "Programming",
                "Programming in Haskell", "Programming",
                "Scatterlings of Africa", "Biography");
        if (rows.size() != expected.size()) return false;
        return expected.entrySet().stream()
                .allMatch(e -> e.getValue().equals(rows.get(e.getKey())));
    }
}
