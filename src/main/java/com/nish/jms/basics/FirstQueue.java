package com.nish.jms.basics;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstQueue {

	public static void main(String[] args) {

		//access jndi initial context
		
		InitialContext initialContext = null;
		Connection connection = null;
		try {
			initialContext = new InitialContext();
			//this will use the information inside jndi.properties
		
			
			ConnectionFactory cf = (ConnectionFactory)initialContext.lookup("ConnectionFactory");
			
			 connection = cf.createConnection();
			
			Session session = connection.createSession();
			
			Queue queue = (Queue)initialContext.lookup("queue/myQueueNish");
			
			MessageProducer producer = session.createProducer(queue);
		
			TextMessage msg = session.createTextMessage("nish is kingi am the creator of my destiny");
			
			producer.send(msg);
			
			MessageConsumer consumer= session.createConsumer(queue);
			
			connection.start();
			
			TextMessage msgReceived = (TextMessage)consumer.receive(5000);
			
			System.out.println("message received: " + msgReceived.getText());
			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}finally {
			if (initialContext != null)
			{
				try {
					initialContext.close();
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
			
			if (connection != null)
			{
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
