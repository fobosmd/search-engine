package searchengine.server.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import searchengine.server.domain.Document;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DocumentServiceTest {

    @Autowired
    private DocumentService documentService;

    @Test
    public void getDocument_exist(){
        Document doc = new Document("1", "1,2,3");
        documentService.saveDocument(doc);
        Assert.assertEquals(Optional.of(doc), documentService.getDocument(doc.getKey()));
    }

    @Test
    public void getDocument_notExist(){
        Document doc = new Document("1", "1,2,3");
        documentService.saveDocument(doc);
        Assert.assertEquals(Optional.empty(), documentService.getDocument("2"));
    }

    @Test
    public void search_tokensNotMatch(){
        Document doc = new Document("1", "1,2,3");
        documentService.saveDocument(doc);
        Collection<String> actual = documentService.search(Arrays.asList("4", "5", "6"));
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void search_tokensMatch(){
        Document doc = new Document("1", "1,2,3");
        documentService.saveDocument(doc);
        Collection<String> actual = documentService.search(Arrays.asList("1", "3"));
        Assert.assertTrue(actual.size() == 1);
        Assert.assertTrue(actual.contains("1"));
    }

    @Test
    public void search_tokensMatchNotTrimmed(){
        Document doc = new Document("1", "1,2,3");
        documentService.saveDocument(doc);
        Collection<String> actual = documentService.search(Arrays.asList("  1   ", "  3   "));
        Assert.assertTrue(actual.size() == 1);
        Assert.assertTrue(actual.contains("1"));
    }

    @Test
    public void search_tokensMatch2Docs(){
        Document doc1 = new Document("1", "1,2,3");
        Document doc2 = new Document("2", "1,2,4");

        documentService.saveDocument(doc1);
        documentService.saveDocument(doc2);
        Collection<String> actual = documentService.search(Arrays.asList("1", "2"));
        Assert.assertTrue(actual.size() == 2);
        Assert.assertTrue(actual.contains("1"));
        Assert.assertTrue(actual.contains("2"));
    }

    @Test
    public void search_partlyMatch(){
        Document doc = new Document("1", "1,2,3");
        documentService.saveDocument(doc);
        Collection<String> actual = documentService.search(Arrays.asList("1", "3", "5"));
        Assert.assertTrue(actual.isEmpty());
    }



}
