package bootcamp.emazon.stock.infrastructure.documentation;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(properties = {
        "appdescription=Test Description",
        "appversion=1.0.0"
})
public class OpenApiConfigurationTest {

    @Value("${appdescription}")
    private String appDescription;

    @Value("${appversion}")
    private String appVersion;

    @Test
    public void testCustomOpenApi() {
        OpenApiConfiguration openApiConfiguration = new OpenApiConfiguration();
        OpenAPI openAPI = openApiConfiguration.customOpenApi(appDescription, appVersion);

        assertNotNull(openAPI);
        assertEquals("Hexagonal Monolithic Emazon API", openAPI.getInfo().getTitle());
        assertEquals(appVersion, openAPI.getInfo().getVersion());
        assertEquals(appDescription, openAPI.getInfo().getDescription());
        assertEquals("http://swagger.io/terms/", openAPI.getInfo().getTermsOfService());
        assertEquals("Apache 2.0", openAPI.getInfo().getLicense().getName());
        assertEquals("http://springdoc.org", openAPI.getInfo().getLicense().getUrl());
    }
}
