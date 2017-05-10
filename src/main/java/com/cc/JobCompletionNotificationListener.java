package com.cc;

import com.cc.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by bigcong on 10/01/2017.
 */
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("人物已经完成，现在是验证结果的时候：");
            List<Person> results = jdbcTemplate.query("SELECT transation_no FROM transaction_record", new RowMapper<Person>() {
                @Override
                public Person mapRow(ResultSet rs, int row) throws SQLException {
                    Person p = new Person();
                    p.setTransation_no(rs.getString(1));
                    return p;
                }
            });

            for (Person person : results) {
                log.info("发现 <" + person + "> 在数据库当中.");
            }

        }
    }


}
