package searchengine.server.repository;

import org.springframework.stereotype.Component;
import searchengine.server.domain.Document;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class DocumentRepositoryImpl implements DocumentRepository {
    private ConcurrentMap<String, Document> docMap = new ConcurrentHashMap<>();

    @Override
    public Optional<Document> getDocument(String key) {
        Objects.requireNonNull(key);
        return Optional.ofNullable(docMap.get(key));
    }

    @Override
    public void saveDocument(Document doc) {
        Objects.requireNonNull(doc);
        docMap.put(doc.getKey(), doc);
    }
}
