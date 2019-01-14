package searchengine.server.service;

import searchengine.server.domain.Document;

import java.util.Collection;
import java.util.Optional;

public interface DocumentService {
    Optional<Document> getDocument(String key);
    void saveDocument(Document doc);
    Collection<String> search(Iterable<String> contentTokens);
}
