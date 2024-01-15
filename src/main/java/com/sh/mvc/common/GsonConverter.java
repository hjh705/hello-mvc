package com.sh.mvc.common;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * <pre>
 * java.time.LocalDate, java.timeLocalTime에 대한 json변환을 지원합니다.
 * - Serializer는 java객체에서 json으로 변환을 지원합니다.
 * - Deserializer는 json데이터에서 java객체로 변환을 지원합니다.
 *
 * 날짜/시간 포맷을 지정해서 일관되게 관리합니다.
 * - LOCAL_DATE_PATTERN
 * - LOCAL_DATE_TIME_PATTERN
 * </pre>
 *
 * @author shqkel
 */
public class GsonConverter {

    public static final LocalDateSerializer LOCAL_DATE_SERIALIZER = new LocalDateSerializer();
    public static final LocalDateDeserializer LOCAL_DATE_DESERIALIZER = new LocalDateDeserializer();
    public static final LocalDateTimeSerializer LOCAL_DATE_TIME_SERIALIZER = new LocalDateTimeSerializer();
    public static final LocalDateTimeDeserializer LOCAL_DATE_TIME_DESERIALIZER = new LocalDateTimeDeserializer();

    private static final String LOCAL_DATE_PATTERN = "yyyy-MM-dd";
    private static final String LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";


    static class LocalDateSerializer implements JsonSerializer<LocalDate> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOCAL_DATE_PATTERN);

        @Override
        public JsonElement serialize(LocalDate localDate, Type srcType, JsonSerializationContext context) {
            return new JsonPrimitive(formatter.format(localDate));
        }
    }

    static class LocalDateDeserializer implements JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return LocalDate.parse(json.getAsString(),
                    DateTimeFormatter.ofPattern(LOCAL_DATE_PATTERN).withLocale(Locale.KOREAN));
        }
    }

    static class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN);

        @Override
        public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
            return new JsonPrimitive(formatter.format(localDateTime));
        }
    }

    static class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return LocalDateTime.parse(json.getAsString(),
                    DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN).withLocale(Locale.KOREAN));
        }
    }
}