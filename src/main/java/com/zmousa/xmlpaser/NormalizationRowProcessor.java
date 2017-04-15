package com.zmousa.xmlpaser;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class NormalizationRowProcessor implements Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		Post post = exchange.getIn().getBody(Post.class);
		if (post != null && post.isQuestion())
			exchange.getOut().setBody(new PostNormalizer(post).normlize());
	}
}
