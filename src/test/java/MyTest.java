import com.blog.BlogApplication;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = BlogApplication.class)
@RunWith(SpringRunner.class)
public class MyTest {

    @Resource
    private PasswordEncoder encoder;

    @Test
    public void password_encode() {
        System.out.println(encoder.encode("123456"));
    }
}
