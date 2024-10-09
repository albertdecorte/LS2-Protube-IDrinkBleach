package com.tecnocampus.LS2.protube_back;

import com.tecnocampus.LS2.protube_back.domain.Video;

import java.util.List;

public class HtmlGenerator {

    public String generateHtml(List<Video> videos) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html lang=\"en\">");
        html.append("<head>");
        html.append("<meta charset=\"UTF-8\">");
        html.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        html.append("<title>Video List</title>");
        html.append("</head>");
        html.append("<body>");
        html.append("<h1>Video List</h1>");
        html.append("<div class=\"video-list\">");

        for (Video video : videos) {
            html.append("<div class=\"video-item\">");
            html.append("<button onclick=\"location.href='file:///").append(video.getVideoPath()).append("'\">");
            html.append("<img src=\"file:///").append(video.getImagePath()).append("\" alt=\"").append(video.getTitle()).append("\">");
            html.append("<div>").append(video.getTitle()).append("</div>");
            html.append("</button>");
            html.append("</div>");
        }

        html.append("</div>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }
}
