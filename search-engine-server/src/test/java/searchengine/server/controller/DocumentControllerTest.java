package searchengine.server.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import searchengine.server.domain.Document;
import searchengine.server.util.DocumentUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DocumentControllerTest {
    private final String doc1 = DocumentUtils.toJson(new Document("1", "1,2,3"));
    private final String doc2 = DocumentUtils.toJson(new Document("2", "1,2,5"));

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getDocument_documentExists() throws Exception {
        mockMvc.perform(post("/v1/docs").content(doc1).contentType(MediaType.APPLICATION_JSON)).andDo(print());
        mockMvc.perform(get("/v1/docs/1").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(content().json(doc1));
    }

    @Test
    public void getDocument_documentNotExist() throws Exception {
        mockMvc.perform(get("/v1/docs/1").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void search_documentMatch() throws Exception {
        mockMvc.perform(post("/v1/docs").content(doc1).contentType(MediaType.APPLICATION_JSON)).andDo(print());
        mockMvc.perform(get("/v1/docs?tokens=1,2").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(content().json("[\"1\"]"));
    }

    @Test
    public void search_documentNotMatch() throws Exception {
        mockMvc.perform(post("/v1/docs").content(doc1).contentType(MediaType.APPLICATION_JSON)).andDo(print());
        mockMvc.perform(get("/v1/docs?tokens=1,2,5").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(content().json("[]"));
    }

    @Test
    public void search_documentPartlyMatch() throws Exception {
        mockMvc.perform(post("/v1/docs").content(doc1).contentType(MediaType.APPLICATION_JSON)).andDo(print());
        mockMvc.perform(post("/v1/docs").content(doc2).contentType(MediaType.APPLICATION_JSON)).andDo(print());
        mockMvc.perform(get("/v1/docs?tokens=1,2,9").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(content().json("[]"));
    }

    @Test
    public void search_document2Match() throws Exception {
        mockMvc.perform(post("/v1/docs").content(doc1).contentType(MediaType.APPLICATION_JSON)).andDo(print());
        mockMvc.perform(post("/v1/docs").content(doc2).contentType(MediaType.APPLICATION_JSON)).andDo(print());
        mockMvc.perform(get("/v1/docs?tokens=1,2").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(content().json("[\"1\",\"2\"]"));
    }


}
