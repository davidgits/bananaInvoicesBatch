/*
 * This code is sample code, provided as-is, and we make NO
 * warranties as to its correctness or suitability for any purpose.
 *
 * We hope that it's useful to you. Enjoy.
 * Copyright LearningPatterns Inc.
 */

package es.netmind.banana_invoices.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.netmind.banana_invoices.batch.processor.ReciboPagadoProcessor;
import es.netmind.banana_invoices.batch.processor.ReciboValidoProcessor;
import es.netmind.banana_invoices.batch.processor.SimpleProcessor;
import es.netmind.banana_invoices.batch.reader.S3ReaderConfig;
import es.netmind.banana_invoices.batch.reader.SimpleReader;
import es.netmind.banana_invoices.batch.writer.ReciboJPAWriter;
import es.netmind.banana_invoices.batch.writer.ReciboSimpleWriter;
import es.netmind.banana_invoices.batch.writer.SimpleWriter;
import es.netmind.banana_invoices.models.Recibo;

@Configuration
@EnableBatchProcessing
@SuppressWarnings({"rawtypes", "unchecked"})
public class AppMainConfig {
    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;
    
    //RECIBOS
    @Autowired
    private S3ReaderConfig readerConfig;
    
    @Bean
    ReciboValidoProcessor reciboProcessor() {
    	return new ReciboValidoProcessor();
    }
    
    @Bean
    ReciboPagadoProcessor reciboPagadoProcessor() {
    	return new ReciboPagadoProcessor();
    }
    
    @Bean
    ReciboSimpleWriter reciboSimpleWriter() {
    	return new ReciboSimpleWriter();
    }
    
    // persiste en base de datos
    @Bean
    ReciboJPAWriter reciboJPAWriter() {
    	return new ReciboJPAWriter();
    }

//    @Bean
//    ItemReader<String> simpleRead() {
//        return new SimpleReader();
//    }
//
//    @Bean
//    ItemWriter<String> simpleWrite() {
//        return new SimpleWriter();
//    }
//
//    @Bean
//    ItemProcessor<String, String> simpleProccesor() {
//        return new SimpleProcessor();
//    }

//    @Bean
//    public Step step1() {
//        return steps.get("step1")
//                .allowStartIfComplete(true)
//                .<String, String>chunk(2)
//                .reader(simpleRead())
//                .processor(simpleProccesor())
//                .writer(simpleWrite())
//                .build();
//    }
//
//    @Bean("mySimpleJob")
//    public Job procesadorItems() {
//        return jobs.get("job1")
//                .start(step1())
//                .build();
//    }

    // TODO: IMPLEMENT STEPS AND JOB FOR RECIBO
    @Bean
    public Step step1() {
    	return steps.get("step1")
    			.allowStartIfComplete(true)
    			.<Recibo, Object>chunk(20)
    			.reader(readerConfig.s3RecibosDataReader())
    			.processor(reciboPagadoProcessor())
    			.writer(reciboJPAWriter())
    			.build();
    	
    }
    
    @Bean("reciboJob")
    public Job procesadorRecibos() {
        return jobs.get("job1")
                .start(step1())
                .build();
    }

}