package com.example.screenmatch.service;

public interface IDataParser {
    <T> T getData(String json, Class<T> tClass);
}
