package br.com.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class TesteConsumidorFilaDLQ {

	public static void main(String[] args) throws Exception {
		InitialContext initialContext = new InitialContext();
		
		ConnectionFactory factory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) initialContext.lookup("DLQ");
		
		MessageConsumer messageConsumer = session.createConsumer(fila);
		
		messageConsumer.setMessageListener(System.out::println);

		new Scanner(System.in).nextLine();
		
		messageConsumer.close();
		session.close();
		connection.close();
		initialContext.close();
		
	}

}
