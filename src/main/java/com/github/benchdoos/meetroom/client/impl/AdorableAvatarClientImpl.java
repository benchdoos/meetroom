package com.github.benchdoos.meetroom.client.impl;

import com.github.benchdoos.meetroom.client.AvatarClient;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.Size;
import java.io.InputStream;
import java.util.Arrays;

@RequiredArgsConstructor
@Service
public class AdorableAvatarClientImpl implements AvatarClient {
    private static final String API_URL = "https://api.adorable.io/avatars/{size}/{key}.png";

    private final RestTemplate restTemplate;

    @Override
    public byte[] getAvatarByKey(String key, @Size(min = 40, max = 256) int size) {
        final HttpEntity<String> entity = new HttpEntity<>(new HttpHeaders());

        final RequestCallback requestCallback = request -> request.getHeaders()
                .setAccept(Arrays.asList(
                        MediaType.APPLICATION_OCTET_STREAM,
                        MediaType.IMAGE_PNG,
                        MediaType.IMAGE_JPEG)
                );

        return restTemplate.execute(
                API_URL,
                HttpMethod.GET,
                requestCallback,
                response -> {
                    final InputStream body = response.getBody();
                    return IOUtils.toByteArray(body);
                },
                size,
                key);
    }
}
