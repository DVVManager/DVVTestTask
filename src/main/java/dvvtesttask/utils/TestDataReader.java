package dvvtesttask.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;

public class TestDataReader {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JodaModule()).registerModule(new JavaTimeModule())
                .setFilterProvider(new SimpleFilterProvider().setFailOnUnknownId(false))
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }


    public static JsonNode readTestDataFile(String filePath) {

        try (InputStream inputStream = TestDataReader.class.getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IOException("File not found: " + filePath);
            }
            return objectMapper.readTree(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonNode readTestData(String filePath,String testDataSection) {
        return readTestDataFile(filePath).get(testDataSection);
    }
}
