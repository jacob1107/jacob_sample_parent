package org.jacob.spring.log.mdc.thread;
import org.slf4j.MDC;  
  
import java.util.Map;  
import java.util.concurrent.Callable;  
  
/** 
 * 短期、一次性任务执行，支持MDC透传 
 * 
 * @author liuguanqing 
 * created 2019/3/27 2:50 PM 
 * @since 2.1.1 
 **/  
public class MDCCallable<V> implements Callable<V> {  
  
    private final Callable<V> callable;  
  
    private transient final Map<String, String> _cm = MDC.getCopyOfContextMap();  
  
    public MDCCallable(Callable<V> callable) {  
        this.callable = callable;  
    }  
  
    @Override  
    public V call() throws Exception {  
        if (_cm != null) {  
            MDC.setContextMap(_cm);  
        }  
        try {  
            return callable.call();  
        } finally {  
            MDC.clear();  
        }  
    }  
  
}  