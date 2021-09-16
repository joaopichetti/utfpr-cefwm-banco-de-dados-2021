import model.Departamento;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        // Obtém a fábrica
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("rh-pu");
        // Obtém uma instância de EntityManager
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        testInsert(entityManager);

        // Liberar recursos
        entityManager.close();
        entityManagerFactory.close();
    }

//    private static void testFind(EntityManager entityManager) {
//        // Obtém o registro com o código 1
//        Departamento departamento = entityManager.find(Departamento.class, 1);
//        System.out.println("Código: " + departamento.getCodigo() + " | Nome: " + departamento.getNome());
//    }
//
//    private static void testCriteriaBuilder(EntityManager entityManager) {
//        // Obtém todos os registros, usando CriteriaBuilder
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Departamento> criteriaQuery = criteriaBuilder.createQuery(Departamento.class);
//        Root<Departamento> rootEntry = criteriaQuery.from(Departamento.class);
//
//    }
//
//    private static void testJpql(EntityManager entityManager) {
//        String query = "SELECT d FROM Departamento d";
//        // Obtém todos os registros usando JPQL
//        List<Departamento> departamentos = entityManager.createQuery(query, Departamento.class).getResultList();
//        if (departamentos != null) {
//            for (Departamento departamento : departamentos) {
//                System.out.println("Código: " + departamento.getCodigo() + " | Nome: " + departamento.getNome());
//            }
//        }
//    }

    private static void testInsert(EntityManager entityManager) {
        Departamento departamento = new Departamento();
//        departamento.setCodigo(3);
        departamento.setNome("Novo Departamento");
        departamento.setLocal("Departamento inserido usando JPA");

        // Inicia transação
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(departamento);
            // Confirma alterações
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            // Desfaz alterações, caso um erro ocorra
            entityManager.getTransaction().rollback();
        }
    }
}
