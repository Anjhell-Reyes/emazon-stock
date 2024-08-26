package bootcamp.emazon.stock.infrastructure.configuracion;
import bootcamp.emazon.stock.domain.api.ICategoriaServicePort;
import bootcamp.emazon.stock.domain.spi.ICategoriaPersistencePort;
import bootcamp.emazon.stock.domain.usecase.CategoriaUseCase;
import bootcamp.emazon.stock.infrastructure.configuration.BeanConfiguration;
import bootcamp.emazon.stock.infrastructure.out.adapter.CategoriaJpaAdapter;
import bootcamp.emazon.stock.infrastructure.out.mapper.CategoriaEntityMapper;
import bootcamp.emazon.stock.infrastructure.out.repository.ICategoriaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BeanConfigurationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testCategoriaPersistencePortBean() {
        ICategoriaPersistencePort categoriaPersistencePort =
                applicationContext.getBean(ICategoriaPersistencePort.class);
        assertNotNull(categoriaPersistencePort);
        assertTrue(categoriaPersistencePort instanceof CategoriaJpaAdapter);
    }

    @Test
    public void testCategoriaServicePortBean() {
        ICategoriaServicePort categoriaServicePort =
                applicationContext.getBean(ICategoriaServicePort.class);
        assertNotNull(categoriaServicePort);
        assertTrue(categoriaServicePort instanceof CategoriaUseCase);
    }

    @Configuration
    @Import(BeanConfiguration.class)
    static class TestConfig {

        @Bean
        public ICategoriaRepository categoriaRepository() {
            return Mockito.mock(ICategoriaRepository.class); // Mock de ICategoriaRepository
        }

        @Bean
        public CategoriaEntityMapper categoriaEntityMapper() {
            return Mockito.mock(CategoriaEntityMapper.class); // Mock de CategoriaEntityMapper
        }
    }
}
