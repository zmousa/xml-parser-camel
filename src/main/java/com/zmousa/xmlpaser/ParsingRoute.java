package com.zmousa.xmlpaser;

import static org.apache.camel.component.stax.StAXBuilder.stax;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.language.ConstantExpression;
import org.springframework.beans.factory.annotation.Autowired;

public class ParsingRoute extends RouteBuilder {
	
	@Autowired
	private NormalizationRowProcessor normalizationRowProcessor;
	@Autowired
	private SaveBulkProcessor saveBulkProcessor;
	@Autowired
	private RowAggrigator aggregateStrategy;
	@Autowired
	private OutputLoadBalancer outputLoadBalancer;
	
	public static final String CSV_SEPERATOR = "|";
	public static ArrayList<String> stopWords;
	public static ExecutorService executorService;
	static String OUTPUT_FILE_NAME;
	
	private String STOPWORDS_FILE_NAME;
	
	public ParsingRoute() {
		STOPWORDS_FILE_NAME = ParsingRoute.class.getClassLoader().getResource("stopwords.txt").getPath();
		loadStopwords();
	}
	
	@Override
	public void configure() {
		from("file:/tmp/input/?noop=true")
		.split(stax(Post.class))
		.streaming()
		.process(normalizationRowProcessor)
		.to("seda:staging");
		
		from("seda:staging?concurrentConsumers=64")
		.aggregate(new ConstantExpression("false"), aggregateStrategy).ignoreInvalidCorrelationKeys().completionSize(500)
		.process(saveBulkProcessor)
		.process(outputLoadBalancer)
		.to("file:/tmp/output?fileExist=Append"); 
    }
	
	private void loadStopwords(){
		try {
			Scanner s = new Scanner(new File(STOPWORDS_FILE_NAME));
			stopWords = new ArrayList<String>();
			while (s.hasNext()){
				stopWords.add(s.next());
			}
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}