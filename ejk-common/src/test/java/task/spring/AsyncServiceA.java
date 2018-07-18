package task.spring;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.Future;

@Service
public class AsyncServiceA {

    @Resource
    private ServiceA serviceA;

    @Async
    public Future<String> service(String str) {
        return new AsyncResult(Thread.currentThread().getName() + serviceA.service(str));
    }
}
