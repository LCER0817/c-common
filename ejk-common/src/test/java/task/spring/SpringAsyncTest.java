package task.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TreadPoolConfig.class)
public class SpringAsyncTest {


    @Resource
    private FacadeService service;

    /**
     * ServiceA、ServiceB为原生的业务类
     * Async为代理的异步类
     * Facade为组合异步调用的类
     */
    @Test
    public void test_service() {
        Map<String, Object> map = this.service.service();
        System.out.println(map);
    }


}
