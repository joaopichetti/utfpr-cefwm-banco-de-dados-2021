import java.sql.*;

public class Main {

    public static void main(String[] args) {
        try (
            // Obtém a conexão existente, ou cria uma nova
            Connection connection = ConnectionFactory.getConnection()
        ) {
            // Testa uma consulta
            testSelect(connection);
            // Testa atualizações
            testUpdates(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void testSelect(Connection connection) throws SQLException {
        // Para definir os parâmetros, utiliza-se o caractere de interrogação (?)
        String sql = "SELECT cd_func, nm_func, salario FROM funcionario WHERE salario < ?";
        try (
            // Cria um PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            // Substitui o parâmetro na posição 1 pelo valor 2000. A primeira posição é 1, ao invés de 0
            preparedStatement.setDouble(1, 2000);
            try (
                // Executa uma query e retorna um ResultSet com o cursor posicionado na primeira linha
                ResultSet result = preparedStatement.executeQuery()
            ) {
                // Enquanto existirem registros, percorre o conjunto de dados
                while (result.next()) {
                    // Tenta obter um valor do tipo int de acordo com o nome da coluna
                    int codigo = result.getInt("cd_func");
                    // Tenta obter um valor do tipo String de acordo com o nome da coluna
                    String nome = result.getString("nm_func");
                    // Tenta obter um valor do tipo double de acordo com o nome da coluna
                    double salario = result.getDouble("salario");
                    System.out.println("Código: " + codigo + " | Nome: " + nome + " | Salário: " + salario);
                }
            }
        }
    }

    private static void testUpdates(Connection connection) throws SQLException {
        // Desativa o modo auto-commit
        connection.setAutoCommit(false);
        try {
            String sql = "INSERT INTO departamento (cd_dep, nm_dep) VALUES (?, ?), (?, ?), (?, ?)";
            int registrosAfetados = 0;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, 3);
                preparedStatement.setString(2, "TI");
                preparedStatement.setInt(3, 4);
                preparedStatement.setString(4, "Contabilidade");
                preparedStatement.setInt(5, 5);
                preparedStatement.setString(6, "RH");
                // Executa uma atualização e retorna o número de registros afetados
                registrosAfetados = preparedStatement.executeUpdate();
                System.out.println("Número de registros afetados pelo INSERT: " + registrosAfetados);
            }

            sql = "UPDATE departamento SET ds_local = ? WHERE cd_dep = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, "2º andar");
                preparedStatement.setInt(2, 3);
                registrosAfetados = preparedStatement.executeUpdate();
                System.out.println("Número de registros afetados pelo UPDATE: " + registrosAfetados);
            }

            sql = "DELETE FROM departamento WHERE cd_dep IN (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, 3);
                preparedStatement.setInt(2, 5);
                registrosAfetados = preparedStatement.executeUpdate();
                System.out.println("Número de registros afetados pelo DELETE: " + registrosAfetados);
            }

            // Confirma todas as alterações
            connection.commit();
        } catch (SQLException ex) {
            // Desfaz todas as alterações
            connection.rollback();
            throw ex;
        } finally {
            // Ativa o modo auto-commit
            connection.setAutoCommit(true);
        }
    }

}