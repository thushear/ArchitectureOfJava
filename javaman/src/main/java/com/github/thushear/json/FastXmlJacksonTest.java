package com.github.thushear.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

/**
 * Created by kongming on 2017/4/13.
 */
public class FastXmlJacksonTest {

    static ObjectMapper MAPPER;

    static {

        MAPPER = new ObjectMapper();
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

        // SerializationFeature for changing how JSON is written

// to enable standard indentation ("pretty-printing"):
        MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
// to allow serialization of "empty" POJOs (no properties to serialize)
// (without this setting, an exception is thrown in those cases)
        MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
// to write java.util.Date, Calendar as number (timestamp):
        MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

// DeserializationFeature for changing how JSON is read as POJOs:

// to prevent exception when encountering unknown property:
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
// to allow coercion of JSON empty String ("") to null Object value:
        MAPPER.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

    }


    public static void main(String[] args) throws IOException {

        JsonDTO jsonDTO = new JsonDTO(1, "thushear");
        String[] childs = new String[]{"aa", "bb", "cc"};
        jsonDTO.setChilds(childs);
        JsonDTO.Wife wife = new JsonDTO.Wife();
        wife.setAge(21);
        wife.setName("thus");
        jsonDTO.setWife(wife);
        String jsonStr = MAPPER.writeValueAsString(jsonDTO);
        System.out.println("jsonStr = " + jsonStr);

        JsonDTO jsonDTO1 = MAPPER.readValue(jsonStr, JsonDTO.class);
        System.out.println("jsonDTO = " + jsonDTO1);

        //Tree Model
        JsonNode jsonNode = MAPPER.readValue(jsonStr, JsonNode.class);
        System.out.println(jsonNode.get("0") + ":" + jsonNode.get("1").asText());

        JsonNode jsonArray = jsonNode.get("childs");
        System.out.println("jsonarray:" + jsonArray.get(0));

        JsonNode wifeNode = jsonNode.get("wife");
        System.out.println(wifeNode.get("name").asText());


        // JsonParser
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser jsonParser = jsonFactory.createParser(jsonStr);

        while (!jsonParser.isClosed()) {
            JsonToken jsonToken = jsonParser.nextToken();
            System.out.println("jsonToken = " + jsonToken);
            if (JsonToken.FIELD_NAME.equals(jsonToken)) {
                String fieldName = jsonParser.getCurrentName();
                System.out.println("fieldName = " + fieldName);
                jsonToken = jsonParser.nextToken();
                System.out.println(jsonParser.getValueAsString());
            }

        }


    }


}

class JsonDTO {

    public JsonDTO() {
    }

    public JsonDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonProperty("0")
    private Integer id;

    @JsonProperty("1")
    private String name;

    private Wife wife;

    private String childs[];

    static class Wife {
        private String name;

        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

    public String[] getChilds() {
        return childs;
    }

    public void setChilds(String[] childs) {
        this.childs = childs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Wife getWife() {
        return wife;
    }

    public void setWife(Wife wife) {
        this.wife = wife;
    }

    @Override
    public String toString() {
        return "JsonDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}