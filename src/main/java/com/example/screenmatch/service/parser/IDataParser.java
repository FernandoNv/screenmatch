package com.example.screenmatch.service.parser;

public interface IDataParser {
    <T> T getData(String json, Class<T> tClass);
}
