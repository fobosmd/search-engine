package searchengine.server.repository;

import searchengine.server.domain.Document;

import java.util.Optional;

public interface DocumentRepository {
    Optional<Document> getDocument(String key);
    void saveDocument(Document doc);
}
