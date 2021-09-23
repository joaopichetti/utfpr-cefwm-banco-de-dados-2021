import model.Departamento;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Obtém a fábrica
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("rh-pu");
        // Obtém uma instância de EntityManager
        EntityManager entityManager = entityManagerFactory.createEntityManager();

//        testeFind(entityManager);
//        testePersist(entityManager);
//        testeRemove(entityManager);
//        testeUpdate(entityManager);
//        testeMerge(entityManager);
        testeJPQL(entityManager);

        // Liberar recursos
        entityManager.close();
        entityManagerFactory.close();
    }

    private static void testFind(EntityManager entityManager) {
        // Obtém o departamento com o código 1
        Departamento departamento = entityManager.find(Departamento.class, 1);
        System.out.println("Código: " + departamento.getCodigo() + " | Nome: " + departamento.getNome());
    }

    private static void testePersist(EntityManager entityManager) {
        Departamento departamento = new Departamento();
//        departamento.setCodigo(3);
        departamento.setNome("Novo Departamento");
        departamento.setLocal("Departamento inserido usando JPA");

        // Inicia transação
        entityManager.getTransaction().begin();
        try {
            // Insere o registro
            entityManager.persist(departamento);
            // Confirma alterações
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            // Desfaz alterações, caso um erro ocorra
            entityManager.getTransaction().rollback();
        }
    }

    private static void testeRemove(EntityManager entityManager) {
        // Busca o registro que será removido
        Departamento departamento = entityManager.find(Departamento.class, 3);

        // Inicia transação
        entityManager.getTransaction().begin();
        try {
            // Remove o registro
            entityManager.remove(departamento);
            // Confirma alterações
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            // Desfaz alterações, caso um erro ocorra
            entityManager.getTransaction().rollback();
        }
    }

    private static void testeUpdate(EntityManager entityManager) {
        // Busca o registro que será atualizado
        Departamento departamento = entityManager.find(Departamento.class, 1);

        // Inicia transação
        entityManager.getTransaction().begin();
        try {
            // Altera o valor da propriedade "nome"
            departamento.setNome("Escolinha JPA");
            // Confirma alterações
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            // Desfaz alterações, caso um erro ocorra
            entityManager.getTransaction().rollback();
        }
    }

    private static void testeMerge(EntityManager entityManager) {
        // Cria nova instância de Departamento
        Departamento departamento = new Departamento();
        departamento.setCodigo(1);
        departamento.setNome("Escolinha");
        departamento.setLocal("Em algum lugar");

        // Inicia transação
        entityManager.getTransaction().begin();
        try {
            // Cria uma entidade gerenciada a partir de uma não gerenciada
            entityManager.merge(departamento);
            // Confirma alterações
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            // Desfaz alterações, caso um erro ocorra
            entityManager.getTransaction().rollback();
        }
    }

    private static void testeJPQL(EntityManager entityManager) {
        // Consulta usando sintaxe da JPQL
        String consulta = "SELECT d FROM Departamento d ORDER BY d.codigo";
        // Cria uma instância de TypedQuery
        TypedQuery<Departamento> query = entityManager.createQuery(consulta, Departamento.class);
        // Executa a consulta e obtém uma lista de departamentos
        List<Departamento> departamentos = query.getResultList();
        for (Departamento d : departamentos) {
            System.out.println("Código: " + d.getCodigo() + " | Nome: " + d.getNome());
        }
        // Consulta com parâmetro
        consulta = "SELECT d FROM Departamento d WHERE d.nome = :nome ORDER BY d.codigo";
        query = entityManager.createQuery(consulta, Departamento.class);
        // Substitui o parâmetro "nome" pelo valor "Escolinha"
        query.setParameter("nome", "Escolinha");
        departamentos = query.getResultList();
        for (Departamento d : departamentos) {
            System.out.println("Código: " + d.getCodigo() + " | Nome: " + d.getNome());
        }
    }

}
