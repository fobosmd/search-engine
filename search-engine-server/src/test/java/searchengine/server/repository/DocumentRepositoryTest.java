package searchengine.server.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import searchengine.server.domain.Document;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DocumentRepositoryTest {
    private final Document doc = new Document("1", "1,2,3");


    @Autowired
    private DocumentRepository documentRepository;

    @Test
    public void getDocument_exist(){
        documentRepository.saveDocument(doc);
        Assert.assertEquals(Optional.of(doc), documentRepository.getDocument(doc.getKey()));
    }

    @Test
    public void getDocument_notExist(){
        documentRepository.saveDocument(doc);
        Assert.assertEquals(Optional.empty(), documentRepository.getDocument("2"));
    }
}
