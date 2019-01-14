package searchengine.cli;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Component
public class CliImpl implements Cli {
    private final String url;
    private RestTemplate restTemplate;
    private HttpHeaders headers;

    public CliImpl(@Value("${search-engine.cli.server.url}") String url) {
        Objects.requireNonNull(url);
        this.url = url + "/v1/docs";
        this.headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        this.restTemplate = new RestTemplate();
    }

    @Override
    public String getDocument(String key) {
        Objects.requireNonNull(key);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url + "/" + key, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    @Override
    public void saveDocument(String key, String content) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(content);

        HttpEntity<String> entity = new HttpEntity<>(CliUtils.toJson(key, content), headers);
        restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    @Override
    public String search(String tokens) {
        Objects.requireNonNull(tokens);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("tokens", tokens);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
        return response.getBody();
    }
}
