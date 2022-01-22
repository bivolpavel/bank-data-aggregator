package com.paj.psd2.aggregator.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * Utility class for loading a JSON object from file.
 */
public final class FileReader {
    private static final ObjectMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(FileReader.class);

    static {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        //mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
// or jackson 2.0
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Reads a JSON object from file.
     * Using {@link TypeReference} for type resolution.
     *
     * @param filePath            the location of the file containing the serialized object
     * @param outputTypeReference the {@link TypeReference} to use during deserialization
     * @param <T>                 the output object type
     * @return the deserialized JSON object of the provided type
     */
    public static <T> T readFromFile(String filePath, TypeReference<T> outputTypeReference) {
        T response = null;
        try {
            response = mapper.readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath), outputTypeReference);
        } catch (IOException e) {
            logger.info("Failed to read content from file " + filePath, e);
        }
        return response;
    }

    /**
     * Reads a JSON object from file.
     * Using Java {@link Class} for type resolution.
     *
     * @param filePath    the location of the file containing the serialized object
     * @param outputClass the {@link Class} to use during deserialization
     * @param <T>         the output object type
     * @return the deserialized JSON object of the provided type
     */
    public static <T> T readFromFile(String filePath, Class<T> outputClass) {
        T response = null;
        try {
            response = mapper.readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath), outputClass);
        } catch (IOException e) {
            logger.info("Failed to read content from file " + filePath, e);
        }
        return response;
    }

    /**
     * Reads a file into a String object.
     *
     * @param filePath the location of the file
     * @return the file content
     */
    public static String readFromFile(String filePath) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.lines().collect(Collectors.joining("\n"));
    }

}