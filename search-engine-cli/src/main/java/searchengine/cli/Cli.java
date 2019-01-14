package searchengine.cli;

public interface Cli {
    String getDocument(String key);
    void saveDocument(String key, String content);
    String search(String tokens);
}
