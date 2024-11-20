package com.poc.config;

import com.poc.listener.FirstJobListener;
import com.poc.listener.FirstStepListener;
import com.poc.model.StudentCsv;
import com.poc.model.StudentJson;
import com.poc.model.StudentXml;
import com.poc.processor.FirstItemProcessor;
import com.poc.reader.FirstItemReader;
import com.poc.service.SecondTasklet;
import com.poc.writer.FirstItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.io.File;

@Configuration
public class SampleJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private SecondTasklet secondTasklet;

    @Autowired
    private FirstJobListener firstJobListener;

    @Autowired
    private FirstStepListener firstStepListener;

    @Autowired
    private FirstItemReader firstItemReader;

    @Autowired
    private FirstItemProcessor firstItemProcessor;

    @Autowired
    private FirstItemWriter firstItemWriter;



    @Bean
    public Job firstJob() {
        return jobBuilderFactory.get("First Job")
                .incrementer(new RunIdIncrementer())
                .start(firstStep())
                .next(secondStep())
                .listener(firstJobListener)
                .build();
    }

    private Step firstStep() {
        return stepBuilderFactory.get("First Step")
                .tasklet(firstTask())
                .listener(firstStepListener)
                .build();
    }


    private Tasklet firstTask() {
        return new Tasklet() {

            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("This is first tasklet step");
                System.out.println(chunkContext.getStepContext().getJobExecutionContext());
                return RepeatStatus.FINISHED;
            }
        };
    }

    private Step secondStep() {
        return stepBuilderFactory.get("Second Step")
                .tasklet(secondTasklet)
                .build();
    }

    @Bean
    public Job secondJob(){
        return jobBuilderFactory.get("Second Job")
                .incrementer(new RunIdIncrementer())
                .start(firstChunckStep())
                .build();
    }

    private Step firstChunckStep() {
        return stepBuilderFactory.get("First Chunk Step")
                .<StudentXml, StudentXml>chunk(1)
                //.reader(flatFileItemReader())
                //.reader(jsonItemReader())
                .reader(studentXmlStaxEventItemReader())
                //.processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }

    public FlatFileItemReader<StudentCsv> flatFileItemReader(
    ){
        FlatFileItemReader<StudentCsv> flatFileItemReader = new FlatFileItemReader<StudentCsv>();

        flatFileItemReader.setResource(new FileSystemResource(
                new File("C:\\Users\\clavi\\Documents\\Udemy\\Batch processing with spring batch y spring boot\\demo1\\InputFiles\\students.csv")));

        flatFileItemReader.setLineMapper(new DefaultLineMapper<StudentCsv>(){{
            setLineTokenizer(new DelimitedLineTokenizer(){{
                setNames("ID"," First Name"," Last Name"," Email");
                setDelimiter("|");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<StudentCsv>(){{
                setTargetType(StudentCsv.class);
            }});
        }});

        flatFileItemReader.setLinesToSkip(1);

        return flatFileItemReader;
    }

    public StaxEventItemReader<StudentXml> studentXmlStaxEventItemReader(){
        StaxEventItemReader<StudentXml> staxEventItemReader =
                new StaxEventItemReader<>();

        staxEventItemReader.setResource(new FileSystemResource(
                new File("C:\\Users\\clavi\\Documents\\Udemy\\Batch processing with spring batch y spring boot\\demo1\\InputFiles\\students.xml")));

        staxEventItemReader.setFragmentRootElementName("student");
        staxEventItemReader.setUnmarshaller(new Jaxb2Marshaller(){
            {
            setClassesToBeBound(StudentXml.class);
        }
        });
        return staxEventItemReader;
    }

    public JsonItemReader<StudentJson> jsonItemReader(){
        JsonItemReader<StudentJson> jsonItemReader = new JsonItemReader<StudentJson>();

        jsonItemReader.setResource(new FileSystemResource(
                new File("C:\\Users\\clavi\\Documents\\Udemy\\Batch processing with spring batch y spring boot\\demo1\\InputFiles\\students.json")));

        jsonItemReader.setJsonObjectReader(new JacksonJsonObjectReader<>(StudentJson.class));
        return jsonItemReader;
    }

}
