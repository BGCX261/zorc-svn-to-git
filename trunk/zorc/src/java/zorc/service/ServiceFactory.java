package zorc.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import zorc.entity.Entity;
import zorc.exception.DataException;
import zorc.persistence.TransactionManager;
import zorc.persistence.TransactionManagerImpl;
import zorc.service.impl.PlanChangeServiceImpl;
import zorc.service.impl.PlanServiceImpl;
import zorc.service.impl.ProductionServiceImpl;
import zorc.service.impl.UnitServiceImpl;

/**
 * @author Genocide Фабрика сервисов.
 */
public class ServiceFactory {

    private static Map<Class<? extends GenericService<? extends Entity>>, Class<? extends GenericService<? extends Entity>>> services = new ConcurrentHashMap<Class<? extends GenericService<? extends Entity>>, Class<? extends GenericService<? extends Entity>>>();

    static {
        services.put(UnitService.class, UnitServiceImpl.class);
        services.put(ProductionService.class, ProductionServiceImpl.class);
        services.put(PlanService.class, PlanServiceImpl.class);
        services.put(PlanChangeService.class, PlanChangeServiceImpl.class);
    }

    @SuppressWarnings("unchecked")
    public static <Type extends GenericService<? extends Entity>> Type getService(Class<Type> key) throws DataException {
        Class<? extends GenericService<? extends Entity>> value = services.get(key);
        if (value != null) {
            try {
                ClassLoader classLoader = value.getClassLoader();
                Class<?>[] interfaces = {key};
                GenericService<? extends Entity> service = value.
                        getConstructor(TransactionManager.class).
                        newInstance(new TransactionManagerImpl());
                InvocationHandler handler = new ServiceInvocationHandler(service);
                return (Type) Proxy.newProxyInstance(classLoader, interfaces, handler);
            } catch (Exception e) {
            }
        }
        return null;
    }
}
