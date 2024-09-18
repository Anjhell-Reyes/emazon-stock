package bootcamp.emazon.stock.domain.usecase;

import bootcamp.emazon.stock.domain.api.IArticleServicePort;
import bootcamp.emazon.stock.domain.exception.*;
import bootcamp.emazon.stock.domain.model.Article;
import bootcamp.emazon.stock.domain.model.Brand;
import bootcamp.emazon.stock.domain.model.Category;
import bootcamp.emazon.stock.domain.model.CustomPage;
import bootcamp.emazon.stock.domain.spi.IArticlePersistencePort;
import bootcamp.emazon.stock.domain.spi.IBrandPersistencePort;
import bootcamp.emazon.stock.domain.spi.ICategoryPersistencePort;
import bootcamp.emazon.stock.domain.utils.Constants;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ArticleUseCase implements IArticleServicePort {

    private final IArticlePersistencePort articlePersistencePort;
    private final IBrandPersistencePort brandPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;

    public ArticleUseCase(IArticlePersistencePort articlePersistencePort, IBrandPersistencePort brandPersistencePort, ICategoryPersistencePort categoryPersistencePort){
        this.articlePersistencePort = articlePersistencePort;
        this.brandPersistencePort = brandPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
    }

    public Article saveArticle(Article article) {
        // Validation Brands
        validateBrands(article);
        // Validation Categories
        validateCategories(article);
        return articlePersistencePort.saveArticle(article);
    }

    @Override
    public Article getArticle(String articleName) {
        return articlePersistencePort.getArticle(articleName);
    }

    @Override
    public CustomPage<Article> getAllArticles(int page, int size, String sortBy, boolean asc) {
        if (page < 0) {
            throw new InvalidPageIndexException();
        }
        int offset = (page - 1) * size;
        String sortByField = getSortByField(sortBy);
        return articlePersistencePort.getAllArticles(offset, size, sortByField, asc);
    }

    @Override
    public void updateArticle(Article article) {
        if (article.getName() == null) {
            throw new NamenotnullException();
        }
        if (article.getDescription() == null) {
            throw new DescriptionNotnullException();
        }
        articlePersistencePort.updateArticle(article);
    }

    @Override
    public void deleteArticle(String articleName) {
        articlePersistencePort.deleteArticle(articleName);
    }

    // Validation of whether the category is greater than three or if it is null
    public void validateCategories(Article article) {
        List<Category> categories = article.getCategories();

        if (categories == null) {
            throw new InvalidNumberOfCategoriesException();
        }
        if (categories.isEmpty()) {
            throw new InvalidNumberOfCategoriesException();
        }
        if (categories.size() > Constants.MAX_CATEGORIES) {
            throw new InvalidNumberOfCategoriesException();
        }

        Set<Category> categorySet = new HashSet<>(categories);
        if (categories.size() != categorySet.size()) {
            throw new DuplicateCategoriesException();
        }

        List<Category> categoryExists = categories.stream()
                .map(category -> {
                    Category existingCategory;
                    try {
                        existingCategory = categoryPersistencePort.getCategory(category.getName());
                    } catch (CategoryNotFoundException e) {
                        throw new CategoryNotFoundException();
                    }
                    return existingCategory;
                })
                .collect(Collectors.toList());

        article.setCategories(categoryExists);
    }

    public void validateBrands(Article article) {
        String brandName = article.getBrand().getName();
        Brand existingBrand;

        if (brandName.isEmpty()) {
            throw new AvailableBrandsException(getAvailableBrands());
        }

        try {
            existingBrand = brandPersistencePort.getBrand(brandName);
        } catch (BrandNotFoundException e) {
            throw new AvailableBrandsException(getAvailableBrands());
        }

        article.setBrand(existingBrand);
    }

    public List<String> getAvailableBrands() {
        return brandPersistencePort.getAll().stream()
                .map(Brand::getName)
                .collect(Collectors.toList());
    }

    public String getSortByField(String sortBy) {
        return switch (sortBy) {
            case "brand" -> Constants.SORT_BY_BRAND_NAME;
            case "categories" -> Constants.SORT_BY_CATEGORY_NAMES;
            default -> Constants.SORT_BY_DEFAULT;
        };
    }
}
