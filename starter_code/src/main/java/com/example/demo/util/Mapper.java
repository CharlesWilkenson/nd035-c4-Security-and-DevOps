package com.example.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mapper {
    static Logger logger = LogManager.getLogger(Mapper.class);
    public static String mapToJasonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
