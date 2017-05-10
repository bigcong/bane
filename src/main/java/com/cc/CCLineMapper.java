package com.cc;

import com.cc.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.LineMapper;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;


/**
 * Created by bigcong on 11/01/2017.
 */
public class CCLineMapper implements LineMapper<Person> {
    private static final Logger log = LoggerFactory.getLogger(CCLineMapper.class);

    @Override
    public Person mapLine(String line, int lineNumber) throws Exception {

        Person p = new Person();
        Field[] fields = p.getClass().getDeclaredFields();
        int i = 0;
        String[] lines = line.split(",");
        if (lines.length != 16) {
            throw new Exception("数据有误" + lines.length);
        }
        for (Field field : fields) {
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), p.getClass());
            log.info(field.getName());
            //获得set方法
            Method method = pd.getWriteMethod();
            String a = lines[i].trim();
            if (a.equals("")) {
                continue;

            }
            method.invoke(p, a);
            i++;
        }


        log.info("第" + lineNumber + "行：" + line + ",s数据为" + p.getGoods_name() + "," + p.getFund_status());


        return p;
    }
}
