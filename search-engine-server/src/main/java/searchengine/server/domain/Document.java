package searchengine.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode
@Getter
public class Document {
    @NonNull
    private final String key;
    @NonNull
    private final String content;
    @NonNull
    @JsonIgnore
    private final List<String> tokens;

    public Document(@NonNull String key, @NonNull String content) {
        this.key = key;
        this.content = content;
        this.tokens = Collections.unmodifiableList(toTokens(content));
    }

    public static List<String> toTokens(String tokens){
        return Arrays.stream(tokens.split(",")).map(String::trim).collect(Collectors.toList());
    }
}
