package util; /**
 * Created by guo on 28/06/2017.
 */


import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.models.*;
import io.swagger.models.auth.OAuth2Definition;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class Bootstrap extends HttpServlet {
  /*
  @Override
  public void init(ServletConfig config) throws ServletException {
      super.init(config);

      BeanConfig beanConfig = new BeanConfig();
      beanConfig.setVersion("1.0.2");
      beanConfig.setSchemes(new String[]{"http"});
      beanConfig.setHost("localhost:8080");
      beanConfig.setBasePath("/api");
      beanConfig.setResourcePackage("com.martijndashorst.quickstarts.resteasy");
      beanConfig.setScan(true);
  }
  */
  @Override
  public void init(ServletConfig config) throws ServletException {
    Info info = new Info()
            .title("Swagger Hello World")
            .description("This is a sample hello world server.")
            .termsOfService("http://swagger.io/terms/")
            .contact(new Contact()
                    .email("shouyanguo@gmail.com"))
            .license(new License()
                    .name("Apache 2.0")
                    .url("http://www.apache.org/licenses/LICENSE-2.0.html"));

    ServletContext context = config.getServletContext();
    Swagger swagger = new Swagger();
    swagger.info(info);
    swagger.securityDefinition("petstore_auth",
            new OAuth2Definition()
                    .implicit("http://localhost:8002/oauth/dialog")
                    .scope("email", "Access to your email address")
                    .scope("pets", "Access to your pets"));
    swagger.tag(new Tag()
            .name("Hello")
            .description("Hello World Service")
            .externalDocs(new ExternalDocs("Find out more", "http://swagger.io")));
    context.setAttribute("swagger", swagger);

  }

}