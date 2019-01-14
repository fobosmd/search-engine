package searchengine.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.server.domain.Document;
import searchengine.server.repository.DocumentRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
public class DocumentServiceImpl implements DocumentService {
    private DocumentRepository documentRepository;
    private ConcurrentMap<String, Set<String>> tokenToKeysMap = new ConcurrentHashMap<>();

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public Optional<Document> getDocument(String key) {
        Objects.requireNonNull(key);
        return documentRepository.getDocument(key);
    }

    @Override
    public void saveDocument(Document doc) {
        Objects.requireNonNull(doc);
        documentRepository.saveDocument(doc);

        for (String t : doc.getTokens()){
            tokenToKeysMap.compute(t, (token, keys) -> {
                Set<String> resKeys = keys == null ? new CopyOnWriteArraySet<>() : keys;
                resKeys.add(doc.getKey());
                return resKeys;
            });
        }
    }

    @Override
    public Set<String> search(Iterable<String> tokens) {
        Set<String> resKeys = new HashSet<>();
        boolean isInited = false;

        for (String t : tokens){
            String trimmedToken = t.trim();
            Set<String> keys = tokenToKeysMap.get(trimmedToken);

            if(keys != null){
                if(isInited){
                    resKeys.retainAll(keys);
                } else {
                    resKeys.addAll(keys);
                    isInited = true;
                }
            } else {
                return Collections.emptySet();
            }
        }
        return resKeys;
    }
}
