package com.tecnocampus.LS2.protube_back;

import com.tecnocampus.LS2.protube_back.domain.Video;

import java.util.List;

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
        html.append("<style>");
        html.append(".video-list {");
        html.append("  display: grid;");
        html.append("  grid-template-columns: repeat(3, 1fr);"); // 3 columns
        html.append("  gap: 10px;"); // Space between items
        html.append("}");
        html.append(".video-item {");
        html.append("  border: 1px solid #ccc;");
        html.append("  border-radius: 5px;");
        html.append("  overflow: hidden;");
        html.append("  text-align: center;");
        html.append("  transition: transform 0.2s;");
        html.append("}");
        html.append(".video-item:hover {");
        html.append("  transform: scale(1.05);"); // Slight scaling on hover
        html.append("}");
        html.append("button {");
        html.append("  background: none;");
        html.append("  border: none;");
        html.append("  cursor: pointer;");
        html.append("  padding: 0;");
        html.append("  width: 100%;"); // Button takes full width
        html.append("}");
        html.append("img {");
        html.append("  width: 100%;"); // Images fill the button width
        html.append("  height: auto;"); // Maintain aspect ratio
        html.append("}");
        html.append("</style>");
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
