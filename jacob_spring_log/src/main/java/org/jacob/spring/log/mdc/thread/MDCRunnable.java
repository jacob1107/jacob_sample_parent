package org.jacob.spring.log.mdc.thread;
import org.slf4j.MDC;  
  
import java.util.Map;  
  
/** 
 * 短期、一次性任务执行，支持MDC透传 
 * 
 * @author liuguanqing 
 * created 2019/3/27 2:50 PM 
 * @since 2.1.1 
 **/  
public class MDCRunnable implements Runnable {  
  
    private final Runnable runnable;  
  
    private transient final Map<String, String> _cm = MDC.getCopyOfContextMap();  
  
    public MDCRunnable(Runnable runnable) {  
        this.runnable = runnable;  
    }  
  
    @Override  
    public void run() {  
        if (_cm != null) {  
            MDC.setContextMap(_cm);  
        }  
        try {  
            runnable.run();  
        } finally {  
            MDC.clear();  
        }  
    }  
  
}  