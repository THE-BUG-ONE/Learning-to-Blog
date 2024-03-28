import com.blog.BackendBlogApplication;
import com.framework.entity.Category;
import com.framework.mapper.ArticleMapper;
import com.framework.service.ArticleService;
import com.framework.service.CategoryService;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = BackendBlogApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringTest {

    @Resource
    ArticleService articleService;

    @Resource
    CategoryService categoryService;

    @Resource
    ArticleMapper articleMapper;

    @Test
    public void test() {
        System.out.println(categoryService.lambdaQuery().eq(Category::getId, 50).one());
    }
}
