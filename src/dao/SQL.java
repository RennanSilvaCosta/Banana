package dao;

import model.Launch;
import model.Installment;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static util.Helper.convertStringToLocalDate;

public class SQL {

    public static void createTables() throws SQLException {
        Statement statement = DAOFactory.getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS tb_recurrence (id_recorrencia INTEGER PRIMARY KEY AUTOINCREMENT, recorrencia VARCHAR)");
        statement.execute("CREATE TABLE IF NOT EXISTS tb_user (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, email VARCHAR)");
        statement.execute("CREATE TABLE IF NOT EXISTS tb_category (id_category INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR)");
        statement.execute("CREATE TABLE IF NOT EXISTS tb_launch_type (id INTEGER PRIMARY KEY AUTOINCREMENT, type VARCHAR)");
        statement.execute("CREATE TABLE IF NOT EXISTS tb_payment_status (id INTEGER PRIMARY KEY AUTOINCREMENT, status VARCHAR)");

        statement.execute("CREATE TABLE IF NOT EXISTS tb_installment (id_prestacao    INTEGER PRIMARY KEY AUTOINCREMENT, date_prestacao  DATE, " +
                "value_prestacao DOUBLE, paid_prestacao  INTEGER REFERENCES tb_payment_status (id) NOT NULL," +
                " id_launch INTEGER, FOREIGN KEY (id_launch) REFERENCES tb_launch (id_lancamento));");

        statement.execute("CREATE TABLE IF NOT EXISTS tb_launch (id_lancamento INTEGER PRIMARY KEY AUTOINCREMENT, type INTEGER REFERENCES tb_launch_type (id) NOT NULL," +
                " title VARCHAR, category INTEGER REFERENCES tb_category (id_category) NOT NULL, date DATE, value DOUBLE, " +
                "recurrence INTEGER REFERENCES tb_recurrence (id_recorrencia) NOT NULL, fixed BOOLEAN, " +
                "paid INTEGER REFERENCES tb_payment_status (id) NOT NULL, parcel BOOLEAN, id_user INTEGER REFERENCES tb_user (id) NOT NULL);");
        statement.close();
    }

    public static void popularTables() throws SQLException {
        Statement statement = DAOFactory.getConnection().createStatement();
        statement.execute("INSERT INTO tb_category (name) VALUES ('Salario'), ('Investimentos'), ('Emprestimos')," +
                "('Outras receitas'), ('Alimentação'), ('Bares e restaurantes'), ('Casa'), ('Compras'), ('Cuidados pessoais'), ('Dívidas e empréstimos')," +
                "('Educação'), ('Família e filhos'), ('Impostos e Taxas'), ('Assinaturas e serviços'), ('Lazer e hobbies'), ('Mercado'), ('Outros'), ('Pets')," +
                "('Presentes ou doações'), ('Roupas'), ('Saúde'), ('Trabalho'), ('Transporte'), ('Viagem')");
        statement.execute("INSERT INTO tb_launch_type (type) VALUES ('receita'), ('despesa')");
        statement.execute("INSERT INTO tb_payment_status (status) VALUES ('pago'), ('não pago'), ('recebido'), ('não recebido')");
        statement.execute("INSERT INTO tb_recurrence (recorrencia) VALUES ('SEM_RECORRENCIA'), ('MENSAL'), ('ANUAL')");
        statement.close();
    }

    public static void saveLaunch(Launch lancamento) throws SQLException {
        Statement statement = DAOFactory.getConnection().createStatement();
        java.sql.Date date32 = java.sql.Date.valueOf(lancamento.getDate());

        statement.execute("INSERT INTO tb_launch (type, title, category, date, value, recurrence, fixed, paid, parcel) " +
                "VALUES ('" + lancamento.getType() + "','" + lancamento.getTitle() + "', " + "'" + lancamento.getCategory() + "', " + " '" + date32 + "', " + "'" + lancamento.getValue() + "', '" + lancamento.getRecurrence() + "', " + lancamento.isFixed() + " , " + lancamento.getPaid() + ", " + lancamento.isParcel() + ");");
        statement.close();
    }

    public static void updateLaunch(Launch lancamento) throws SQLException {
        Connection connection = DAOFactory.getConnection();
        Statement statement = connection.createStatement();
        /*statement.execute("UPDATE tb_launch SET title = '" + lancamento.getTitle() + "', category = '" + lancamento.getCategory() + "', date = '" + lancamento.getDate() + "', value = '" + lancamento.getValue()
                + "', recurrence = '" + lancamento.getRecurrence() + "', fixed = " + lancamento.isFixed()
                + ", paid = " + lancamento.isPaid() + ", " + lancamento.isParcel() + " WHERE id_lancamento = " + lancamento.getId());*/

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
        statement = connection.prepareStatement("SELECT * FROM tb_launch WHERE date BETWEEN '" + firstDayOfMonth + "' AND '" + lastDayOfMonth + "' ORDER BY type DESC");
        resultSet = statement.executeQuery();

        while (resultSet.next()) {

            Launch lancamento = new Launch();

            lancamento.setId(resultSet.getInt("id_lancamento"));
            lancamento.setTitle(resultSet.getString("title"));
            //lancamento.setType(LaunchType.valueOf(resultSet.getString("type")));
            //lancamento.setRecurrence(LauchRecurrence.valueOf(resultSet.getString("recurrence")));
           // lancamento.setCategory(resultSet.getString("category"));
            lancamento.setDate(convertStringToLocalDate(resultSet.getString("date")));
            lancamento.setValue(resultSet.getDouble("value"));
            lancamento.setFixed(resultSet.getBoolean("fixed"));
            //lancamento.setPaid(resultSet.getBoolean("paid"));
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

        statement = connection.prepareStatement("SELECT * FROM tb_launch ORDER BY id_lancamento DESC LIMIT 1");
        resultSet = statement.executeQuery();

        int id = 0;

        while (resultSet.next()) {
            id = resultSet.getInt("id_lancamento");
        }

        return id;
    }

    public static void saveParcel(Installment installment) throws SQLException {
        Connection connection = DAOFactory.getConnection();
        Statement statement = connection.createStatement();

       /* statement.execute("INSERT INTO tb_installment (date_prestacao, value_prestacao, paid_prestacao, id_launch) " +
                "VALUES ('" + prestacao.getDatePrestacao() + "','" + prestacao.getValuePrestacao() + "', " + prestacao.isPaidPrestacao() + ", " + prestacao.getIdLancamento() + " )");*/
        statement.close();
        connection.close();
    }

    public static Launch getLaunchParcelsByMonth(Launch lancamentos, LocalDate localDateReferencia) throws SQLException {

        LocalDate firstDayOfMonth = localDateReferencia.minusDays(localDateReferencia.getDayOfMonth() - 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.plusDays(localDateReferencia.getMonth().length(false) - 1);

        Connection connection = DAOFactory.getConnection();
        PreparedStatement statement;
        ResultSet resultSet;

        List<Installment> installmentList = new ArrayList<>();


        statement = connection.prepareStatement("SELECT * FROM tb_installment WHERE id_launch = " + lancamentos.getId() + " AND date_prestacao BETWEEN '" + firstDayOfMonth + "' AND '" + lastDayOfMonth + "'");
        resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Installment installment = new Installment();
            installment.setId(resultSet.getInt("id_prestacao"));
            installment.setValue(resultSet.getDouble("value_prestacao"));
            installment.setDate(convertStringToLocalDate(resultSet.getString("date_prestacao")));
            //prestacao.setPaidPrestacao(resultSet.getBoolean("paid_prestacao"));
            installment.setIdLancamento(resultSet.getInt("id_launch"));
            installmentList.add(installment);
        }

        lancamentos.setPrestacaoes(installmentList);
        return lancamentos;


    }
}
