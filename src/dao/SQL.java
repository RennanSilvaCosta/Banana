package dao;

import model.Launch;
import model.Prestacao;
import model.enums.LauchRecurrence;
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
                " category VARCHAR, date DATE, value DOUBLE, recurrence VARCHAR, fixed BOOLEAN, paid BOOLEAN, parcel BOOLEAN)");

        statement.execute("CREATE TABLE IF NOT EXISTS tb_prestacao( id_prestacao INTEGER PRIMARY KEY AUTOINCREMENT, date_prestacao DATE, value_prestacao DOUBLE, paid_prestacao BOOLEAN, id_launch INTEGER, FOREIGN KEY (id_launch) REFERENCES tb_lancamento(id_lancamento))");
        statement.close();
    }

    public static void saveLaunch(Launch lancamento) throws SQLException {
        Connection connection = DAOFactory.getConnection();
        java.sql.Date date32 = java.sql.Date.valueOf(lancamento.getDate());
        Statement statement = connection.createStatement();

        statement.execute("INSERT INTO tb_lancamento (type, title, category, date, value, recurrence, fixed, paid, parcel) " +
                "VALUES ('" + lancamento.getType() + "','" + lancamento.getTitle() + "', " + "'" + lancamento.getCategory() + "', " + " '" + date32 + "', " + "'" + lancamento.getValue() + "', '" + lancamento.getRecurrence() + "', " + lancamento.isFixed() + " , " + lancamento.isPaid() + ", " + lancamento.isParcel() + ");");
        statement.close();
        connection.close();
    }

    public static void updateLaunch(Launch lancamento) throws SQLException {
        Connection connection = DAOFactory.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("UPDATE tb_lancamento SET title = '" + lancamento.getTitle() + "', category = '" + lancamento.getCategory() + "', date = '" + lancamento.getDate() + "', value = '" + lancamento.getValue()
                + "', recurrence = '" + lancamento.getRecurrence() + "', fixed = " + lancamento.isFixed()
                + ", paid = " + lancamento.isPaid() + ", " + lancamento.isParcel() + " WHERE id_lancamento = " + lancamento.getId());

        statement.close();
        connection.close();
    }

    public static List<Launch> getLaunchByMonth(LocalDate localDateReferencia) throws SQLException {

        LocalDate firstDayOfMonth = localDateReferencia.minusDays(localDateReferencia.getDayOfMonth() - 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.plusDays(localDateReferencia.getMonth().length(false) - 1);

        Connection connection = DAOFactory.getConnection();
        PreparedStatement statement;
        ResultSet resultSet;

        List<Launch> lancamentos = new ArrayList<>();
        statement = connection.prepareStatement("SELECT * FROM tb_lancamento WHERE date BETWEEN '" + firstDayOfMonth + "' AND '" + lastDayOfMonth + "' ORDER BY type DESC");
        resultSet = statement.executeQuery();

        while (resultSet.next()) {

            Launch lancamento = new Launch();

            lancamento.setId(resultSet.getInt("id_lancamento"));
            lancamento.setTitle(resultSet.getString("title"));
            lancamento.setType(LaunchType.valueOf(resultSet.getString("type")));
            lancamento.setRecurrence(LauchRecurrence.valueOf(resultSet.getString("recurrence")));
            lancamento.setCategory(resultSet.getString("category"));
            lancamento.setDate(convertStringToLocalDate(resultSet.getString("date")));
            lancamento.setValue(resultSet.getDouble("value"));
            lancamento.setFixed(resultSet.getBoolean("fixed"));
            lancamento.setPaid(resultSet.getBoolean("paid"));
            lancamento.setParcel(resultSet.getBoolean("parcel"));
            lancamentos.add(lancamento);
        }

        resultSet.close();
        statement.close();
        return lancamentos;
    }

    public static int getIdLastLaunch() throws SQLException {

        Connection connection = DAOFactory.getConnection();
        PreparedStatement statement;
        ResultSet resultSet;

        statement = connection.prepareStatement("SELECT * FROM tb_lancamento ORDER BY id_lancamento DESC LIMIT 1");
        resultSet = statement.executeQuery();

        int id = 0;

        while (resultSet.next()) {
            id = resultSet.getInt("id_lancamento");
        }

        return id;
    }

    public static void saveParcel(Prestacao prestacao) throws SQLException {
        Connection connection = DAOFactory.getConnection();
        Statement statement = connection.createStatement();

        statement.execute("INSERT INTO tb_prestacao (date_prestacao, value_prestacao, paid_prestacao, id_launch) " +
                "VALUES ('" + prestacao.getDatePrestacao() + "','" + prestacao.getValuePrestacao() + "', " + prestacao.isPaidPrestacao() + ", " + prestacao.getIdLancamento() + " )");
        statement.close();
        connection.close();
    }

    public static Launch getLaunchParcelsByMonth(Launch lancamentos, LocalDate localDateReferencia) throws SQLException {

        LocalDate firstDayOfMonth = localDateReferencia.minusDays(localDateReferencia.getDayOfMonth() - 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.plusDays(localDateReferencia.getMonth().length(false) - 1);

        Connection connection = DAOFactory.getConnection();
        PreparedStatement statement;
        ResultSet resultSet;

        List<Prestacao> prestacaoList = new ArrayList<>();


        statement = connection.prepareStatement("SELECT * FROM tb_prestacao WHERE id_launch = " + lancamentos.getId() + " AND date_prestacao BETWEEN '" + firstDayOfMonth + "' AND '" + lastDayOfMonth + "'");
        resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Prestacao prestacao = new Prestacao();
            prestacao.setIdPrestacao(resultSet.getInt("id_prestacao"));
            prestacao.setValuePrestacao(resultSet.getDouble("value_prestacao"));
            prestacao.setDatePrestacao(convertStringToLocalDate(resultSet.getString("date_prestacao")));
            prestacao.setPaidPrestacao(resultSet.getBoolean("paid_prestacao"));
            prestacao.setIdLancamento(resultSet.getInt("id_launch"));
            prestacaoList.add(prestacao);
        }

        lancamentos.setPrestacaoes(prestacaoList);
        return lancamentos;


    }
}
