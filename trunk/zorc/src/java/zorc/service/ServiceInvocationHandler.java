package zorc.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import zorc.exception.DataException;
import zorc.service.impl.Support;

public class ServiceInvocationHandler implements InvocationHandler {

    private Support service;

    public ServiceInvocationHandler(GenericService service) {
        this.service = (Support) service;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
        try {
            Object result = method.invoke(service, arguments);
            service.getManager().transactionCommit();
            return result;
        } catch (DataException e) {
            rollback(method);
            throw e;
        } catch (InvocationTargetException e) {
            rollback(method);
            throw e.getCause();
        }
    }

    private void rollback(Method method) {
        if (method.getDeclaringClass() != GenericService.class) {
            try {
                service.getManager().transactionRollback();
            } catch (DataException e) {
            }
        }
    }
}