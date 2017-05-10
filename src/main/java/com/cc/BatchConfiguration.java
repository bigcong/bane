package com.cc;


import com.cc.model.Person;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

/**
 * Created by bigcong on 10/01/2017.
 */
@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    // tag::readerwriterprocessor[]
    @Bean
    public FlatFileItemReader<Person> reader() {
        FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
        Resource r = new FileSystemResource("/Users/bigcong/Downloads/alipay_record_20170217_1704.txt");

        //reader.setResource(new ClassPathResource("sample-data.csv"));
        reader.setResource(r);

        reader.setLineMapper(new CCLineMapper());

     /*   reader.setLineMapper(new DefaultLineMapper<Person>() {{


            setLineTokenizer(new DelimitedLineTokenizer() {{

                setNames(new String[]{"firstName", "lastName"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                setTargetType(Person.class);
            }});

        }});*/
        return reader;
    }

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    /**
     * `transation_no` varchar(255) DEFAULT NULL COMMENT '交易号',
     * `business_order_number` varchar(255) DEFAULT NULL COMMENT '商户订单号',
     * `transation_create_time` datetime DEFAULT NULL COMMENT '交易创建时间',
     * `paid_time` datetime DEFAULT NULL COMMENT '支付时间',
     * `last_modified_time` datetime DEFAULT NULL COMMENT '最近修改时间',
     * `transation_source` varchar(255) DEFAULT NULL COMMENT '交易来源',
     * `transation_type` varchar(255) DEFAULT NULL COMMENT '交易类型',
     * `counterparty` varchar(255) DEFAULT NULL COMMENT '交易对方',
     * `goods_name` varchar(255) DEFAULT NULL COMMENT '商品名字',
     * `amount` double DEFAULT NULL COMMENT '金额',
     * `payment_type` varchar(255) DEFAULT NULL COMMENT '收支',
     * `transation_status` varchar(255) DEFAULT NULL COMMENT '交易状态',
     * `service_charge` double DEFAULT NULL COMMENT '服务费',
     * `success_refund_amount` double DEFAULT NULL COMMENT '成功退款',
     * `remark` varchar(255) DEFAULT NULL COMMENT '备注',
     * `fund_status` varchar(255) DEFAULT NULL COMMENT '资金状态'
     *
     * @return
     */
    @Bean
    public JdbcBatchItemWriter<Person> delteWriter() {
        JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<Person>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
        writer.setSql("delete from transaction_record where transation_no=:transation_no");
        writer.setAssertUpdates(false);

        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public JdbcBatchItemWriter<Person> insertWriter() {
        JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<Person>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());

        /**
         * if exists (select 1 from tb where name = 'aa')
         begin
         update tb
         set name = 'bb'
         where name = 'aa'
         end
         else
         begin
         insert into tb(name) select 'aa'
         end
         */

        writer.setSql(
                " INSERT INTO transaction_record (transation_no, business_order_number,transation_create_time,paid_time,last_modified_time,transation_source,transation_type,counterparty,goods_name,amount,payment_type,transation_status,service_charge,success_refund_amount,remark,fund_status) VALUES " +
                        "(:transation_no,:business_order_number,:transation_create_time,:paid_time,:last_modified_time,:transation_source,:transation_type,:counterparty,:goods_name,:amount,:payment_type,:transation_status,:service_charge,:success_refund_amount,:remark,:fund_status)");

        writer.setDataSource(dataSource);
        return writer;
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[]
    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener).start(deleteStep()).next(insertStep())
                .build();
    }

    @Bean
    public Step insertStep() {
        return stepBuilderFactory.get("insert")
                .<Person, Person>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(insertWriter())
                .build();
    }


    @Bean
    public Step deleteStep() {
        return stepBuilderFactory.get("delete")
                .<Person, Person>chunk(10)
                .reader(reader())
                .writer(delteWriter())
                .build();
    }
    // end::jobstep[]
}
