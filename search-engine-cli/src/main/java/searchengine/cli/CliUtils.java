package searchengine.cli;

public class CliUtils {
    public static String toJson(String key, String content){
        return String.format("{\"key\":\"%s\",\"content\":\"%s\"}", key, content);
    }
}
