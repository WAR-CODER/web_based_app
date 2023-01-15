package wethinkcode.rdbms;

import java.sql.ResultSet;

/**
 * DO NOT MODIFY THIS CODE
 *
 *
 */
public class SampleTableValidator extends AbstractTableValidator {
    public SampleTableValidator(String tablename) {
        super(tablename);
    }

    @Override
    public boolean validateColumns(ResultSet rs) {
        return true;
    }

    @Override
    public boolean validatePrimaryKey(ResultSet rs) {
        return true;
    }

    @Override
    public boolean validateForeignKey(ResultSet rs) {
        return true;
    }
}
