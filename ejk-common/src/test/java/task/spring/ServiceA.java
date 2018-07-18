package task.spring;

import org.springframework.stereotype.Service;

@Service
public class ServiceA {

    public String service(String str) {
        return "serviceA:" + str;
    }
}
