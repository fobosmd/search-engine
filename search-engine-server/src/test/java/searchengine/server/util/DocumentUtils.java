package searchengine.server.util;

import searchengine.server.domain.Document;

import java.util.Objects;

public class DocumentUtils {
    public static String toJson(Document doc){
        Objects.requireNonNull(doc);
        return String.format("{\"key\":\"%s\",\"content\":\"%s\"}", doc.getKey(), doc.getContent());
    }
}
