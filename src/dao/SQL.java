package dao;

import model.Lancamento;
import model.enums.LaunchType;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static util.Helper.convertStringToLocalDate;

public class SQL {

    public static void createTables() throws SQLException {
        Statement statement = DAOFactory.getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS tb_lancamento( id_lancamento INTEGER PRIMARY KEY AUTOINCREMENT, type VARCHAR, title VARCHAR," +
                " category VARCHAR, date DATE, value DOUBLE, recurrence VARCHAR, parcelas INTEGER, totalParcelas INTEGER, fixed BOOLEAN, paid BOOLEAN)");
    }

    public static void saveLauch(Lancamento lancamento) throws SQLException {
        Connection connection = DAOFactory.getConnection();
        java.sql.Date date32 = java.sql.Date.valueOf(lancamento.getDate());
        Statement statement = connection.createStatement();

        System.out.println(date32);

        statement.execute("INSERT INTO tb_lancamento (type, title, category, date, value, recurrence, parcelas, totalParcelas, fixed, paid) " +
                "VALUES ('"+lancamento.getType()+"','"+ lancamento.getTitle()+"', " + "'" + lancamento.getCategory() + "', " + " '"+ date32 +"', " + "'" + lancamento.getValue()+"', '"+ lancamento.getRecurrence()+"', '" +lancamento.getParcelas() + "', '"+ lancamento.getTotalParcelas()+"', "+ lancamento.isFixed() + " , " + lancamento.isPaid() + ");");
        statement.close();
        connection.close();
    }

    public static List<Lancamento> getLaunchByMonth(LocalDate localDateReferencia) throws SQLException {

        LocalDate firstDayOfMonth = localDateReferencia.minusDays(localDateReferencia.getDayOfMonth() - 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.plusDays(localDateReferencia.getMonth().length(false) - 1);

        Connection connection = DAOFactory.getConnection();
        PreparedStatement statement;
        ResultSet resultSet;

        List<Lancamento> lancamentos = new ArrayList<>();

        statement = connection.prepareStatement("SELECT * FROM tb_lancamento WHERE date BETWEEN '"+ firstDayOfMonth  +"' and '" + lastDayOfMonth +"'");

        resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Lancamento lancamento = new Lancamento();

            lancamento.setId(resultSet.getInt("id_lancamento"));
            lancamento.setTitle(resultSet.getString("title"));
            lancamento.setType(LaunchType.valueOf(resultSet.getString("type")));
            lancamento.setCategory(resultSet.getString("category"));
            lancamento.setDate(convertStringToLocalDate(resultSet.getString("date")));
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
