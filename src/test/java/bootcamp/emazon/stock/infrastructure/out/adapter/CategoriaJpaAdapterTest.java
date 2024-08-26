package bootcamp.emazon.stock.infrastructure.out.adapter;

import bootcamp.emazon.stock.domain.model.Categoria;
import bootcamp.emazon.stock.infrastructure.exception.CategoriaAlreadyExistsExeption;
import bootcamp.emazon.stock.infrastructure.exception.CategoriaNotFoundException;
import bootcamp.emazon.stock.infrastructure.exception.NoDataFoundException;
import bootcamp.emazon.stock.infrastructure.out.entity.CategoriaEntity;
import bootcamp.emazon.stock.infrastructure.out.mapper.CategoriaEntityMapper;
import bootcamp.emazon.stock.infrastructure.out.repository.ICategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoriaJpaAdapterTest {

    @InjectMocks
    private CategoriaJpaAdapter categoriaJpaAdapter;

    @Mock
    private ICategoriaRepository categoriaRepository;

    @Mock
    private CategoriaEntityMapper categoriaEntityMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCategoria_WhenCategoriaDoesNotExist() {
        Categoria categoria = new Categoria(1L, "testName", "descriptionTest");
        CategoriaEntity categoriaEntity = new CategoriaEntity();

        when(categoriaRepository.findByNombre(categoria.getNombre())).thenReturn(Optional.empty());
        when(categoriaEntityMapper.toEntity(categoria)).thenReturn(categoriaEntity);
        when(categoriaRepository.save(categoriaEntity)).thenReturn(categoriaEntity);

        Categoria result = categoriaJpaAdapter.saveCategoria(categoria);

        assertNotNull(result);
        assertEquals(categoria, result);
        verify(categoriaRepository).save(categoriaEntity);
    }

    @Test
    void testSaveCategoria_WhenCategoriaAlreadyExists() {
        Categoria categoria = new Categoria(1L, "testName", "descriptionTest");

        when(categoriaRepository.findByNombre(categoria.getNombre())).thenReturn(Optional.of(new CategoriaEntity()));

        assertThrows(CategoriaAlreadyExistsExeption.class, () -> categoriaJpaAdapter.saveCategoria(categoria));
    }

    @Test
    void testGetCategoria_WhenCategoriaExists() {
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        Categoria categoria = new Categoria(1L, "testName", "descriptionTest");

        when(categoriaRepository.findByNombre("testName")).thenReturn(Optional.of(categoriaEntity));
        when(categoriaEntityMapper.toCategoria(categoriaEntity)).thenReturn(categoria);

        Categoria result = categoriaJpaAdapter.getCategoria("testName");

        assertEquals(categoria, result);
    }

    @Test
    void testGetCategoria_WhenCategoriaDoesNotExist() {
        when(categoriaRepository.findByNombre("testName")).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFoundException.class, () -> categoriaJpaAdapter.getCategoria("testName"));
    }

    @Test
    void testGetAllCategoriasWithData() {
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        Categoria categoria = new Categoria(1L, "testName", "descriptionTest");
        Pageable pageable = Pageable.unpaged();
        Page<CategoriaEntity> categoriaEntities = new PageImpl<>(Collections.singletonList(categoriaEntity));

        when(categoriaRepository.findAll(pageable)).thenReturn(categoriaEntities);
        when(categoriaEntityMapper.toCategoria(categoriaEntity)).thenReturn(categoria);

        Page<Categoria> result = categoriaJpaAdapter.getAllCategorias(pageable);  // Cambiado a categoriaJpaAdapter

        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void testGetAllCategoriasNoData() {
        Pageable pageable = Pageable.unpaged();

        when(categoriaRepository.findAll(pageable)).thenReturn(Page.empty());

        assertThrows(NoDataFoundException.class, () -> categoriaJpaAdapter.getAllCategorias(pageable));  // Cambiado a categoriaJpaAdapter
    }

    @Test
    void testUpdateCategoria() {
        Categoria categoria = new Categoria(1L, "testName", "descriptionTest");
        CategoriaEntity categoriaEntity = new CategoriaEntity();

        when(categoriaEntityMapper.toEntity(categoria)).thenReturn(categoriaEntity);

        categoriaJpaAdapter.updateCategoria(categoria);

        verify(categoriaRepository).save(categoriaEntity);
    }

    @Test
    void testDeleteCategoria() {
        categoriaJpaAdapter.deleteCategoria("testName");

        verify(categoriaRepository).deleteByNombre("testName");
    }
}
