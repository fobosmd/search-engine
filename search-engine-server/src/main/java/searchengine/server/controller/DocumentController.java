package searchengine.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import searchengine.server.domain.Document;
import searchengine.server.service.DocumentService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1/docs", consumes = MediaType.APPLICATION_JSON_VALUE)
public class DocumentController {

    private DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @RequestMapping(value = "/{key}", method= RequestMethod.GET)
    public Document getDocument(@PathVariable Optional<String> key){
        final String k = key.orElse("");
        return documentService.getDocument(k).orElseThrow(()->new DocumentNotFoundException("No documents found with key: " + k));
    }

    @RequestMapping(method= RequestMethod.POST)
    public void saveDocument(@RequestBody Optional<Document> doc){
        Document d = doc.orElseThrow(()->new IllegalArgumentException("Document can not be null"));
        documentService.saveDocument(d);
    }

    @RequestMapping(method= RequestMethod.GET)
    public Collection<String> search(@RequestParam("tokens") Optional<String> tokens){
        String t = tokens.orElse("");
        return documentService.search(Document.toTokens(t));
    }

    @ExceptionHandler({DocumentNotFoundException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleException(Exception e){
        return e.getMessage();
    }
}

