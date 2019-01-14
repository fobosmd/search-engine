package searchengine.cli;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CliTest {

    @Autowired
    private Cli cli;

    @Test
    @Ignore
    public void search(){
        cli.saveDocument("1", "1,2,3");
        cli.saveDocument("2", "1,2,4");
        String actual = cli.search("1,2");
        String actualDoc = cli.getDocument("2");
    }
}
