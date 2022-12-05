package pl.gduraj.glencuboid.storage;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryBuilder {

    private final StringBuilder queryBuilder;
    private final List<Object> parameters;

    public QueryBuilder(StringBuilder queryBuilder, List<Object> parameters) {
        this.queryBuilder = queryBuilder;
        this.parameters = parameters;
    }

    public QueryBuilder() {
        this(new StringBuilder(), new ArrayList<>());
    }

    private void append(String value) {
        if (queryBuilder.length() != 0)
            queryBuilder.append(", ");
        queryBuilder.append(value);
    }

    public void add(String value, Object param) {
        append(value);
        parameters.add(param);
    }

    public void add(String value, Object... params) {
        append(value);
        this.parameters.addAll(Arrays.asList(params));
    }

    public String toQueryString() {
        return this.queryBuilder.toString();
    }

    public int setParameters(PreparedStatement ps, int offset) throws SQLException {
        for (int i = 0; i < parameters.size(); i++) {
            ps.setObject(offset + 1 + i, parameters.get(i));
        }
        return parameters.size();
    }

    static void setArguments(PreparedStatement prepStmt, Object... parameters) throws SQLException {
        for (int n = 0; n < parameters.length; n++) {
            prepStmt.setObject(n + 1, parameters[n]);
        }
    }
}
