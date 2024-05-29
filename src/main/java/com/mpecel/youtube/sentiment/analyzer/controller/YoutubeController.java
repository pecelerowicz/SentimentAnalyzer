package com.mpecel.youtube.sentiment.analyzer.controller;

import com.mpecel.youtube.sentiment.analyzer.dto.youtube.YoutubeRequest;
import com.mpecel.youtube.sentiment.analyzer.dto.youtube.YoutubeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/yt")
public class YoutubeController {

    @GetMapping("/videos")
    public YoutubeResponse videos(@RequestBody YoutubeRequest youtubeRequest) {
        System.out.println(youtubeRequest);
        return null;
    }

}
