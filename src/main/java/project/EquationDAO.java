package project;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.List;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EquationDAO {

    private HikariDataSource createDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/mathassistant");
        dataSource.setUsername("user1");
        dataSource.setPassword("password1");
        return dataSource;
    }

    private JdbcTemplate createJdbcTemplate() {
        return new JdbcTemplate(createDataSource());
    }

    public void closeDataSource() {
        createDataSource().close();
    }

    private String buildQueryForEquationAndCorrectRootsInsert(ArrayList<String> correctRoots, String equation) {
        String insertRootsValuesForQuery = correctRoots.getFirst();
        String insertRootsForQuery = "root1";
        for (int i = 1; i < correctRoots.toArray().length; i++) {
            insertRootsValuesForQuery = insertRootsValuesForQuery + ", " + correctRoots.get(i);
            insertRootsForQuery = insertRootsForQuery + ", " + "root" + (i + 1);
        }
        return "INSERT INTO equations (equation, " + insertRootsForQuery + ") VALUES (" + "'" + equation + "', " + insertRootsValuesForQuery + ");";
    }

    //not in use
    private String insertQueryForEquation(String equation) {
        return "INSERT INTO equations (equation) VALUES + (" + equation + ");";
    }
    //not in use
    public void insertEquationAndRootsIntoDB(ArrayList<String> correctRoots, String equation) {
        createJdbcTemplate().update(buildQueryForEquationAndCorrectRootsInsert(correctRoots, equation));
        closeDataSource();
        System.out.println("Your equation and it's correct roots are saved, ID for the record is: "
                + createJdbcTemplate().queryForObject("SELECT Max(id) FROM equations;", String.class));
    }
    //not in use
    public void selectEquationByID(String id) {
        try {
            System.out.println(createJdbcTemplate().queryForObject(
                    "SELECT equation FROM equations WHERE id=" + id + ";", String.class));
        }
        catch (EmptyResultDataAccessException e) {
            System.out.println("Sorry, but we couldn't find the equation with given ID");
        }
    }

    public List<Equation> getAllQuestions() {
        String sql = "SELECT * FROM equations";
        return createJdbcTemplate().query(sql, equationRowMapper);
    }

    public List<Equation> findEquationsByNonZeroRootsCount(int nonZeroRootsCount) {
        StringBuilder whereClause = new StringBuilder();
        for (int i = 1; i <= 10; i++) {
            if (i > 1) {
                whereClause.append(" + ");
            }
            whereClause.append("(root").append(i).append(" <> 0 AND root").append(i).append(" IS NOT NULL)");
        }
        String sql = "SELECT * FROM equations WHERE " + whereClause + " = ?";

        try {
            return createJdbcTemplate().query(sql, new Object[]{nonZeroRootsCount}, equationRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Equation> findEquationsByRootValue(double rootValue) {
        StringBuilder whereClause = new StringBuilder();
        for (int i = 1; i <= 10; i++) {
            if (i > 1) whereClause.append(" OR ");
            whereClause.append("root").append(i).append(" = ?");
        }
        String sql = "SELECT * FROM equations WHERE " + whereClause;

        return createJdbcTemplate().query(sql,
                new Object[]{rootValue, rootValue, rootValue, rootValue, rootValue,
                        rootValue, rootValue, rootValue, rootValue, rootValue}, equationRowMapper);
    }

    public int insertEquationAndGetId(String equation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO equations (equation) VALUES (?)";

        createJdbcTemplate().update(connection -> {
            java.sql.PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, equation);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public void updateEquationRoots(int id, ArrayList<String> roots) {
        try {
        StringBuilder sql = new StringBuilder("UPDATE equations SET ");
        for (int i = 0; i < roots.size(); i++) {
            sql.append("root").append(i + 1).append(" = ?, ");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 2));
        sql.append(" WHERE id = ?");

        Object[] params = new Object[roots.size() + 1];
        for (int i = 0; i < roots.size(); i++) {
            params[i] = roots.get(i);
        }
        params[roots.size()] = id;

        createJdbcTemplate().update(sql.toString(), params);
    } catch (BadSqlGrammarException e) {
            System.out.println("There are no correct roots to update");
        }
    }


    public Equation findEquationById(int id) {
        try {
            String sql = "SELECT * FROM equations WHERE id = ?";
            Equation equation = createJdbcTemplate().queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
                Equation eq = new Equation();
                eq.setId(rs.getInt("id"));
                eq.setEquation(rs.getString("equation"));
                double[] roots = new double[10];
                for (int i = 0; i < 10; i++) {
                    roots[i] = rs.getDouble("root" + (i + 1));
                }
                eq.setRoots(roots);
                return eq;
            });
            return equation;
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Sorry, but we couldn't find the equation with given ID");
        }
        return null;
    }

    private RowMapper<Equation> equationRowMapper = new RowMapper<Equation>() {
        public Equation mapRow(ResultSet rs, int rowNum) throws SQLException {
            Equation equation = new Equation();
            equation.setId(rs.getInt("id"));
            equation.setEquation(rs.getString("equation"));
            double[] roots = new double[10];
            for (int i = 0; i < 10; i++) {
                roots[i] = rs.getDouble("root" + (i + 1));
            }
            equation.setRoots(roots);
            return equation;
        }
    };




}




