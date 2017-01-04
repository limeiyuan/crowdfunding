package com.crowdfunding.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 * Created by LiMeiyuan on 2016/6/15 9:49.
 * description:
 */
public class HibernateAwareObjectMapper extends ObjectMapper {
    public HibernateAwareObjectMapper() {
        Hibernate4Module module = new Hibernate4Module();
        module.configure(Hibernate4Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, true);
        module.configure(Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION, false);
        registerModule(module);
    }
}
