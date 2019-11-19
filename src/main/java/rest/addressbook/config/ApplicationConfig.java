package rest.addressbook.config;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import rest.addressbook.domain.AddressBook;
import rest.addressbook.web.AddressBookController;

public class ApplicationConfig extends ResourceConfig {

  /**
   * Default constructor
   */
  public ApplicationConfig() {
    this(new AddressBook());
  }


  /**
   * Main constructor
   *
   * @param addressBook a provided address book
   */
  public ApplicationConfig(final AddressBook addressBook) {
    register(AddressBookController.class);
    register(MOXyJsonProvider.class);
    register(new AbstractBinder() {

      @Override
      protected void configure() {
        bind(addressBook).to(AddressBook.class);
      }
    });
    OpenAPI oas = new OpenAPI();
    Info info = new Info()
          .title("Address book API")
          .description("Gestión de una coleccion de contactos")
          .termsOfService("https://github.com/UNIZAR-30246-WebEngineering/lab3-restful-ws/blob/master/README.md")
          .contact(new Contact()
                .email("721615@unizar.es"));

    oas.info(info);

    OpenApiResource openApiResource = new OpenApiResource();

    SwaggerConfiguration oasConfig = new SwaggerConfiguration()
            .openAPI(oas)
            .prettyPrint(true)
            .resourcePackages(Stream.of("io.swagger.sample.resource").collect(Collectors.toSet()));

    openApiResource.setOpenApiConfiguration(oasConfig);
    register(openApiResource);
  }

}
