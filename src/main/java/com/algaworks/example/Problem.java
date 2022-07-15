package com.algaworks.example;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Problem")
@JsonInclude(Include.NON_NULL)
public class Problem {

    @Schema(example = "400")
    private Integer status;

    @Schema(example = "https://books-api.com/invalid-data")
    private String type;

    @Schema(example = "Invalid data")
    private String title;

    @Schema(example = "One or more fields are invalid. Please fill in correctly and try again.")
    private String detail;

    @Schema(example = "One or more fields are invalid. Please fill in correctly and try again.")
    private String userMessage;

    @Schema(example = "2022-02-07T01:28:57.902245498Z")
    private OffsetDateTime timestamp;

    @Schema(description = "List of objects or fields that generated the error. (optional)")
    private List<Object> objects;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<Object> getObjects() {
        return objects;
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }

    @Schema(name = "ProblemObject")
    public static class Object {

        @Schema(example = "author")
        private String name;

        @Schema(example = "Author is required.")
        private String userMessage;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserMessage() {
            return userMessage;
        }

        public void setUserMessage(String userMessage) {
            this.userMessage = userMessage;
        }
    }

}