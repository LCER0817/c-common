import com.ns.common.bean.Ucloud;
import com.ns.common.util.ucloud.BucketAction;

public class UcloudTest {

//    @Test
//    public void createBucket(){
//        Ucloud ucloud = UCloudUtil.newBuilder("JBPXpUKpSSTwWHEGjdD7eiagVyp/MF3/kMVV7AQgJMarF0VGUQYuMg==", "728689afc953dfcfe76b9dcc38d06209d26e9bec").build();
//        String result = ucloud.bucket().createBucket("passport-local-123");
//        System.out.println(result);
//    }
//
//    @Test
//    public void postFile(){
//        Ucloud ucloud = UCloudUtil.newBuilder("JBPXpUKpSSTwWHEGjdD7eiagVyp/MF3/kMVV7AQgJMarF0VGUQYuMg==", "728689afc953dfcfe76b9dcc38d06209d26e9bec").build();
//        File file = new File("E://img.png");
//        String result = ucloud.file().postFile("passport-local-123", "test4", file, "image/png");
//        System.out.println(result);
//    }
//
//    @Test
//    public void putFile(){
//        Ucloud ucloud = UCloudUtil.newBuilder("JBPXpUKpSSTwWHEGjdD7eiagVyp/MF3/kMVV7AQgJMarF0VGUQYuMg==", "728689afc953dfcfe76b9dcc38d06209d26e9bec").build();
//        File file = new File("E://img.png");
//        String result = ucloud.file().putFile("passport-local-123", "test2", file, "image/png");
//        System.out.println(result);
//    }

    public void describeBucket() {
        Ucloud ucloud = new Ucloud("JBPXpUKpSSTwWHEGjdD7eiagVyp/MF3/kMVV7AQgJMarF0VGUQYuMg==", "728689afc953dfcfe76b9dcc38d06209d26e9bec", null);
        String result = BucketAction.getBucketInfo(ucloud, "passport-local-123");
        System.out.println(result);
    }
}
