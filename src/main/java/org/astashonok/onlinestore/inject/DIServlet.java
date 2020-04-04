package org.astashonok.onlinestore.inject;

import org.astashonok.onlinestore.util.ClassName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.lang.reflect.Field;
import java.util.List;

public class DIServlet extends HttpServlet {
    private static final String APP_CTX_PATH = "contextConfigLocation";
    private static final Logger logger = LoggerFactory.getLogger(ClassName.getCurrentClassName());

    @Override
    public void init() throws ServletException {
        String appCtxPath = this.getServletContext().getInitParameter(APP_CTX_PATH);
        logger.debug("load {} -> ", appCtxPath);
        if (appCtxPath == null) {
            logger.error("I need init param {} ", APP_CTX_PATH);
            throw new ServletException(APP_CTX_PATH + " init param == null");
        }
        try {
            // load AppContext
            ApplicationContext appCtx = new ClassPathXmlApplicationContext(appCtxPath);
            // then we inject from ApplicationContext all the fields marked by @Inject
            List<Field> allFields = FieldReflector.collectUpTo(this.getClass());
            List<Field> injectFields = FieldReflector.filterInject(allFields);
            logger.debug("{} :: injectFields: {}", this.getClass().getSimpleName(), injectFields);
            for (Field field : injectFields) {
                // encapsulation hacking
                field.setAccessible(true);
                Inject annotation = field.getAnnotation(Inject.class);
                logger.debug("I found the field marked by @Inject: '{}'", field);
                String beanName = annotation.value();
                logger.debug("I must instantiate and inject '{}'", beanName);
                Object bean = appCtx.getBean(beanName);
                logger.debug("The instantiation is OK: '{}'", beanName);
                if (bean == null) {
                    throw new ServletException("There is no bean with name '" + beanName + "'");
                }
                field.set(this, bean);
            }
        } catch (Exception e) {
            throw new ServletException("Can't inject from " + APP_CTX_PATH, e);
        }
    }
}
