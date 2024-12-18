package com.dwbh.backend.component;

import com.dwbh.backend.dto.chat.ChatSuggestRequest;
import com.dwbh.backend.dto.chat.ChatSuggestResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/v1beta/models/")
public interface GeminiHttpClient {
    @PostExchange("{model}:generateContent")
    ChatSuggestResponse httpRequest(@PathVariable("model") String model, @RequestBody ChatSuggestRequest req);
}
