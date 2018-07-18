package task.spring;

import com.google.common.collect.Maps;
import com.ns.common.util.exception.sys.SystemInternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.Future;

@Service
public class FacadeService {

    private Logger logger = LoggerFactory.getLogger(FacadeService.class);

    @Resource
    private AsyncServiceA asyncServiceA;

    @Resource
    private AsyncServiceB asyncServiceB;


    public Map<String, Object> service() {
        Future<String> serviceA = asyncServiceA.service("paramA");
        Future<String> serviceB = asyncServiceB.service("paramB");

        Map<String, Object> result = Maps.newHashMap();

        try {
            result.put("serviceA", serviceA.get());
            result.put("serviceB", serviceB.get());
        } catch (Exception e) {
            logger.error("", e.getMessage());
            throw new SystemInternalException();
        }
        return result;
    }
}
