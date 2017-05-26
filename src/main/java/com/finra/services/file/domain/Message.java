package com.finra.services.file.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message {
    private String author;
    private String contents;

    public Message() {
    }

    public Message(String author, String contents) {
        this.author = author;
        this.contents = contents;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}