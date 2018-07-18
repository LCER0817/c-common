package task.spring;

import org.springframework.stereotype.Service;

@Service
public class ServiceB {

    public String service(String str) {
        return "serviceB:" + str;
    }
}
