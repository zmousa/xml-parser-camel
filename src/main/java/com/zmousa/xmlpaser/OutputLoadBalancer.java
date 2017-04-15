package com.zmousa.xmlpaser;

import java.util.Random;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class OutputLoadBalancer implements Processor {
	private String filePrefix;
	private int balancersNumber;
	
	public OutputLoadBalancer(String filePrefix, int balancersNumber) {
		this.filePrefix = filePrefix;
		this.balancersNumber = balancersNumber;
	}

	public String getFilePrefix() {
		return filePrefix;
	}

	public void setFilePrefix(String filePrefix) {
		this.filePrefix = filePrefix;
	}

	public int getBalancersNumber() {
		return balancersNumber;
	}

	public void setBalancersNumber(int balancersNumber) {
		this.balancersNumber = balancersNumber;
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		String posts = exchange.getIn().getBody(String.class);
		exchange.getOut().setHeader(Exchange.FILE_NAME, filePrefix + (new Random()).nextInt(balancersNumber));
		exchange.getOut().setBody(posts);
	}
}
