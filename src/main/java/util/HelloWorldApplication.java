package util;

import com.martijndashorst.quickstarts.resteasy.HelloWorld;
import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class HelloWorldApplication extends Application {
    HashSet<Object> singletons = new HashSet<Object>();

    public HelloWorldApplication() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.2");
        beanConfig.setDescription("This is a sample hello world server.");
        beanConfig.setTitle("Swagger Hello World");
        beanConfig.setContact("shouyanguo@gmail.com");
        beanConfig.setSchemes(new String[]{"http","https"});
        beanConfig.setHost("localhost:4080");
        beanConfig.setBasePath("/");
        //beanConfig.setFilterClass("io.swagger.sample.util.ApiAuthorizationFilterImpl");
        beanConfig.setResourcePackage("com.martijndashorst.quickstarts.resteasy");
        beanConfig.setScan(true);
    }

    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> set = new HashSet<Class<?>>();
        set.add(HelloWorld.class);

        set.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        set.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        return set;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
