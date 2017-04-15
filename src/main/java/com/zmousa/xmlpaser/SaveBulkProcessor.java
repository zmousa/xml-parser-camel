package com.zmousa.xmlpaser;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class SaveBulkProcessor implements Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		@SuppressWarnings("unchecked")
		List<Post> posts = exchange.getIn().getBody(ArrayList.class);
		if (posts != null) {
			System.out.println("save:" + posts.size() + " first id: " + posts.get(0).getId());
			StringBuilder builder = new StringBuilder();
			for (Post post : posts)
				if (post.isQuestion())
					builder.append(post.getCSVRow());
			exchange.getOut().setBody(builder.toString());
		}
	}
}