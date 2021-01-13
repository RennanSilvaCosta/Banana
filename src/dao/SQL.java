package dao;

import model.Lancamento;
import model.enums.LaunchType;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SQL {

    public static Connection getConnect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:banana.db");
    }

    public static void createTables() throws SQLException {
        Statement statement = getConnect().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS tb_lancamento( id_lancamento INTEGER PRIMARY KEY AUTOINCREMENT, type VARCHAR, title VARCHAR," +
                " category VARCHAR, description VARCHAR, note VARCHAR, date LOCALDATE, value DOUBLE, recurrence VARCHAR, parcelas INTEGER)");
        statement.close();
    }

    public static void saveLauch(Lancamento lancamento) throws SQLException {
        Connection connection = getConnect();
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO tb_lancamento (type, title, category, description, note, date, value, recurrence, parcelas) " +
                "VALUES ('"+lancamento.getType()+"','"+ lancamento.getTitle()+"'," + "'" + lancamento.getCategory() + "', " + "'" + lancamento.getDescription() + "', '"+ lancamento.getNote()+"', '"+ lancamento.getDate()+"', '"+ lancamento.getValue()+"', '"+ lancamento.getRecurrence()+"', '"+ lancamento.getParcelas()+"');");
        statement.close();
    }

    public static List<Lancamento> getAllLancamentos() throws SQLException {

        Connection connection = getConnect();
        PreparedStatement statement;
        ResultSet resultSet;

        List<Lancamento> lancamentos = new ArrayList<>();

        statement = connection.prepareStatement("SELECT * FROM tb_lancamento");
        resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Lancamento lancamento = new Lancamento();

            lancamento.setId(resultSet.getInt("id_lancamento"));
            lancamento.setTitle(resultSet.getString("title"));
            lancamento.setType(LaunchType.valueOf(resultSet.getString("type")));
            lancamento.setCategory(resultSet.getString("category"));
            lancamento.setDescription(resultSet.getString("description"));
            lancamento.setNote(resultSet.getString("note"));
            lancamento.setDate(LocalDate.parse(resultSet.getString("date")));
            lancamento.setValue(resultSet.getDouble("value"));
            lancamento.setParcelas(resultSet.getInt("parcelas"));
            lancamentos.add(lancamento);
        }
        statement.close();
        return lancamentos;
    }

}
