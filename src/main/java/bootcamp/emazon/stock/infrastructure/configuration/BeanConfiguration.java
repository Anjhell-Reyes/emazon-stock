package bootcamp.emazon.stock.infrastructure.configuration;

import bootcamp.emazon.stock.domain.api.ICategoriaServicePort;
import bootcamp.emazon.stock.domain.spi.ICategoriaPersistencePort;
import bootcamp.emazon.stock.domain.usecase.CategoriaUseCase;
import bootcamp.emazon.stock.infrastructure.out.adapter.CategoriaJpaAdapter;
import bootcamp.emazon.stock.infrastructure.out.mapper.CategoriaEntityMapper;
import bootcamp.emazon.stock.infrastructure.out.repository.ICategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final ICategoriaRepository categoriaRepository;
    private final CategoriaEntityMapper categoriaEntityMapper;

    @Bean
    public ICategoriaPersistencePort categoriaPersistencePort(){
        return new CategoriaJpaAdapter(categoriaRepository, categoriaEntityMapper);
    }

    @Bean
    public ICategoriaServicePort categoriaServicePort() {
        return new CategoriaUseCase(categoriaPersistencePort());
    }
}
