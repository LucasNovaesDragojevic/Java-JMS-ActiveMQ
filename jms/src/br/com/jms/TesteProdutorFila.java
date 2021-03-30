package br.com.jms;

import java.util.Iterator;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class TesteProdutorFila {

	public static void main(String[] args) throws Exception {
		InitialContext initialContext = new InitialContext();
		
		ConnectionFactory factory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) initialContext.lookup("financeiro");
		
		MessageProducer producer = session.createProducer(fila);		
		
		for (int i = 0; i < 100; i++) {
			Message message = session.createTextMessage("<id>" + i + "</id><dado>DAORA</dado>");
			producer.send(message);			
		}
		
		//new Scanner(System.in).nextLine();
		
		producer.close();
		session.close();
		connection.close();
		initialContext.close();
		
	}

}
