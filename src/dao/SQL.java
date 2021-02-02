package dao;

import model.Lancamento;
import model.enums.LaunchType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQL {

    public static void createTables() throws SQLException {
        Statement statement = DAOFactory.getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS tb_lancamento( id_lancamento INTEGER PRIMARY KEY AUTOINCREMENT, type VARCHAR, title VARCHAR," +
                " category VARCHAR, month INTEGER, year INTEGER, value DOUBLE, recurrence VARCHAR, parcelas INTEGER, totalParcelas INTEGER, fixed BOOLEAN, paid BOOLEAN)");
    }

    public static void saveLauch(Lancamento lancamento) throws SQLException {
        Connection connection = DAOFactory.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO tb_lancamento (type, title, category, month, year, value, recurrence, parcelas, totalParcelas, fixed, paid) " +
                "VALUES ('"+lancamento.getType()+"','"+ lancamento.getTitle()+"', " + "'" + lancamento.getCategory() + "', " + " '"+ lancamento.getMonth()+"', '" +lancamento.getYear() +"', '"+ lancamento.getValue()+"', '"+ lancamento.getRecurrence()+"', '" +lancamento.getParcelas() + "', '"+ lancamento.getTotalParcelas()+"', "+ lancamento.isFixed() + " , " + lancamento.isPaid() + ");");
        statement.close();
        connection.close();
    }

    public static List<Lancamento> getLaunchByMonth(int month, int year) throws SQLException {

        Connection connection = DAOFactory.getConnection();
        PreparedStatement statement;
        ResultSet resultSet;

        List<Lancamento> lancamentos = new ArrayList<>();

        statement = connection.prepareStatement("SELECT * FROM tb_lancamento WHERE month=" + month + " and year=" + year + "  ORDER BY type DESC");
        resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Lancamento lancamento = new Lancamento();

            lancamento.setId(resultSet.getInt("id_lancamento"));
            lancamento.setTitle(resultSet.getString("title"));
            lancamento.setType(LaunchType.valueOf(resultSet.getString("type")));
            lancamento.setCategory(resultSet.getString("category"));
            lancamento.setMonth(resultSet.getInt("month"));
            lancamento.setYear(resultSet.getInt("year"));
            lancamento.setValue(resultSet.getDouble("value"));
            lancamento.setParcelas(resultSet.getInt("parcelas"));
            lancamento.setTotalParcelas(resultSet.getInt("totalParcelas"));
            lancamento.setFixed(resultSet.getBoolean("fixed"));
            lancamento.setPaid(resultSet.getBoolean("paid"));
            lancamentos.add(lancamento);
        }
        resultSet.close();
        statement.close();
        return lancamentos;
    }

}
