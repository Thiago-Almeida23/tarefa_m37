import br.com.thiago.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

public class ProdutoTest {

    private EntityManagerFactory emf;
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("produtoPU");
        em = emf.createEntityManager();
    }

    @AfterEach
    public void tearDown() {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    public void testPersistirProduto() {
        Produto produto = new Produto();
        produto.setNome("Produto Teste");
        produto.setPreco(99.99);

        em.getTransaction().begin();
        em.persist(produto);
        em.getTransaction().commit();

        assertNotNull(produto.getId(), "O ID do produto não pode ser nulo após persistir");

        Produto produtoBuscado = em.find(Produto.class, produto.getId());

        assertNotNull(produtoBuscado, "Produto não encontrado no banco de dados");
        assertEquals("Produto Teste", produtoBuscado.getNome(), "O nome do produto não é o esperado");
        assertEquals(99.99, produtoBuscado.getPreco(), "O preço do produto não é o esperado");
    }
}

