package org.astashonok.onlinestore.util.inject;

import org.astashonok.onlinestore.util.ClassName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Field;
import java.util.List;

public class DICommand {
    private static final String APP_CTX_PATH = "/context-dao.xml";
    private static final Logger logger = LoggerFactory.getLogger(ClassName.getCurrentClassName());

    public DICommand() {
        try {
            // load AppContext
            ApplicationContext appCtx = new ClassPathXmlApplicationContext(APP_CTX_PATH);
            // then we inject from ApplicationContext all the fields marked by @Inject
            List<Field> allFields = FieldReflector.collectUpTo(this.getClass(), DICommand.class);
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
                    throw new Exception("There is no bean with name '" + beanName + "'");
                }
                field.set(this, bean);
            }
        } catch (Exception e) {
            // "Can't inject from " + APP_CTX_PATH, e
            e.printStackTrace();
        }
    }
}
