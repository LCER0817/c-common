package task;

import org.junit.Test;

import java.util.Map;

public class RunnerTest {

    ServiceA sa = new ServiceA();
    ServiceB sb = new ServiceB();

    @Test
    public void test_01() throws Exception {
        ParallelTaskRunner runner = new ParallelTaskRunner();
        runner.addTask("serviceA", () -> sa.op("paramA"));
        runner.addTask("serviceB", () -> sb.op("paramB"));
        runner.addTask("serviceAA", () -> sa.op("paramAA"));

        Map map = runner.run();
        System.out.println(map);
    }

}

class ServiceA {
    public Object op(String param) {
        return param;
    }
}

class ServiceB {
    public Object op(String param) {
        return param;
    }
}