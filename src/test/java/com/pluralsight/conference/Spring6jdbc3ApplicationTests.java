package com.pluralsight.conference;

import com.pluralsight.conference.model.Speaker;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class Spring6jdbc3ApplicationTests {

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testCreateSpeaker() {
        Speaker speaker = new Speaker();
        speaker.setName("John Henry");

        speaker = restTemplate.postForObject("http://localhost:8080/speaker", speaker, Speaker.class);

        System.out.println(speaker.getName());
    }

    @Test
    void testGetSpeakers() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Speaker>> speakersResponse = restTemplate.exchange(
                "http://localhost:8080/speaker", HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                });

        assertNotNull(speakersResponse.getBody(), "Body is null");

        List<Speaker> speakers = speakersResponse.getBody();

        for (Speaker speaker : speakers) {
            System.out.println("Speaker name: " + speaker.getName() + " " + speaker.getSkill());
        }
    }

    @Test
    void testUpdateSpeaker() {
        Speaker speaker = restTemplate.getForObject("http://localhost:8080/speaker/{id}", Speaker.class, 17);
        speaker.setName(String.format("%s Sr.",speaker.getName()));
        restTemplate.put("http://localhost:8080/speaker", speaker);
        System.out.println(speaker.getName());
    }

    @Test
    void testBatchUpdate(){
        restTemplate.getForObject("http://localhost:8080/speaker/batch", Object.class);
    }

    @Test
    void testDeleteSpeaker(){
        restTemplate.delete("http://localhost:8080/speaker/delete/{id}",17);
    }

    @Test
    void testException() {
        restTemplate.getForObject("http://localhost:8080/speaker/test", Speaker.class);
    }
}
