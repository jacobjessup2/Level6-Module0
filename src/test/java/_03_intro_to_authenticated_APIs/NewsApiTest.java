package _03_intro_to_authenticated_APIs;

import _03_intro_to_authenticated_APIs.data_transfer_objects.ApiExampleWrapper;
import _03_intro_to_authenticated_APIs.data_transfer_objects.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.util.UriBuilder;

import _01_intro_to_APIs.data_transfer_objects.Result;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class NewsApiTest {

    NewsApi newsApi;

    @Mock
    WebClient webClientMock;

    @Mock
    WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpecMock;

    @Mock
    WebClient.ResponseSpec responseSpecMock;

    @Mock
    Mono<ApiExampleWrapper> resultMonoMock;
    
    @Mock
    Mono<Article[]> articleMonoMock;
    
    @BeforeEach
    void setUp() {
    	MockitoAnnotations.openMocks(this);
    	
    	newsApi = new NewsApi();
    	newsApi.setWebClient(webClientMock);
    }

    @Test
    void itShouldGetNewsStoryByTopic() {
        //given
    	String topic = "pizza";

    	ApiExampleWrapper result = new ApiExampleWrapper();
    	ApiExampleWrapper expectedResults = result;

        when(webClientMock.get())
                .thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri((Function<UriBuilder, URI>) any()))
                .thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve())
                .thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(ApiExampleWrapper.class))
                .thenReturn(resultMonoMock);
        when(resultMonoMock.block())
                .thenReturn(expectedResults);
        
        //when
        ApiExampleWrapper actualResults = newsApi.getNewsStoryByTopic(topic);
       
        //then
        verify(webClientMock, times(1)).get();
        assertEquals(expectedResults, actualResults);
    }

    @Test
    void itShouldFindStory(){
        //given
    	String topic = "Cows";
    	String articleContent = "Cow Stuff";
        String articleTitle = "Cow Article";
        String articleURL = "www.cowArticleURL.com";

        ApiExampleWrapper wrapper = new ApiExampleWrapper();
        	
        Article result = new Article();
        result.setTitle(articleTitle);
        result.setUrl(articleURL);
        result.setContent(articleContent);
        
        List<Article> expectedResults = new ArrayList<Article>();
        expectedResults.add(result);
        wrapper.setArticles(expectedResults);
        when(webClientMock.get())
                .thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri((Function<UriBuilder, URI>) any()))
                .thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve())
                .thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(ApiExampleWrapper.class))
                .thenReturn(resultMonoMock);
        when(resultMonoMock.block())
                .thenReturn(wrapper);

        String expectedArticle =
        		articleTitle + " -\n"
                        + articleContent
                        + "\nFull article: " + articleURL;
        //when
        String actualBook = newsApi.findStory(topic);
        
        //then
        verify(webClientMock, times(1)).get();
        assertEquals(expectedArticle, actualBook);
    }


}