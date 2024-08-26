package bootcamp.emazon.stock.infrastructure.configuracion;
import bootcamp.emazon.stock.domain.api.ICategoriaServicePort;
import bootcamp.emazon.stock.domain.spi.ICategoriaPersistencePort;
import bootcamp.emazon.stock.domain.usecase.CategoriaUseCase;
import bootcamp.emazon.stock.infrastructure.out.adapter.CategoriaJpaAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BeanConfigurationIntegrationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testBeansConfiguration() {
        // Verificar que ICategoriaPersistencePort es un bean
        ICategoriaPersistencePort categoriaPersistencePort = applicationContext.getBean(ICategoriaPersistencePort.class);
        assertThat(categoriaPersistencePort).isNotNull();
        assertThat(categoriaPersistencePort).isInstanceOf(CategoriaJpaAdapter.class);

        // Verificar que ICategoriaServicePort es un bean
        ICategoriaServicePort categoriaServicePort = applicationContext.getBean(ICategoriaServicePort.class);
        assertThat(categoriaServicePort).isNotNull();
        assertThat(categoriaServicePort).isInstanceOf(CategoriaUseCase.class);

        // Verificar que las dependencias están inyectadas correctamente
        CategoriaJpaAdapter categoriaJpaAdapter = (CategoriaJpaAdapter) categoriaPersistencePort;
        assertThat(categoriaJpaAdapter.getCategoriaRepository()).isNotNull();
        assertThat(categoriaJpaAdapter.getCategoriaEntityMapper()).isNotNull();
    }
}


