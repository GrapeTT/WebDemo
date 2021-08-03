package com.demo.web.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * @Name：DateConverter
 * @Package：com.demo.web.converter
 * @Descripition：json日期格式转换
 * @Author：涛
 * @Date：2018/4/1 15:09
 * @Version：1.0
 */
@JsonComponent
public class DateConverter extends JsonDeserializer<Date> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateConverter.class);
    
    private String[] patterns = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd"};
    
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String dateString = jsonParser.getText();
        Date date = null;
        try {
            date = DateUtils.parseDate(dateString, patterns);
        } catch (ParseException e) {
            LOGGER.error("转换日期失败，date=" + dateString, e);
            //转换失败返回空
            return null;
        }
        return date;
    }
}