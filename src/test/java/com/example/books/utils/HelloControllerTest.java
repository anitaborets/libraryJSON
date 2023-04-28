package com.example.books.utils;

import com.example.books.book.BookController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class HelloControllerTest {


    @Autowired
    private WebTestClient webTestClient;

    @Test
    void simple_index_test() {
        HelloController controller = new HelloController();
        String result = controller.index();
        assertEquals("index", result);
    }

    @Test
    void test_index_with_webTestClient() {
        webTestClient.get().uri("/")
                .accept(MediaType.TEXT_HTML)
                .exchange()
                .expectStatus().isOk();
              //  .expectBody(String.class)
              //  .isEqualTo("index");
    }

}