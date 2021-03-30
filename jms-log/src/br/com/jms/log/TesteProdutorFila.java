package br.com.jms.log;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TesteProdutorFila {

	public static void main(String[] args) throws Exception {
		InitialContext initialContext = new InitialContext();
		
		ConnectionFactory factory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) initialContext.lookup("LOG");
		
		MessageProducer producer = session.createProducer(fila);		
		
		Message message = session.createTextMessage("INFO | isReady");
		producer.send(message, DeliveryMode.NON_PERSISTENT, 1, 5000);
		
		message = session.createTextMessage("DEBUG | transactionFinished");
		producer.send(message, DeliveryMode.NON_PERSISTENT, 0, 2000);
			
		message = session.createTextMessage("ERROR | ConnectionBrokeException");
		producer.send(message, DeliveryMode.PERSISTENT, 9, 10000);
		
		//new Scanner(System.in).nextLine();
		
		producer.close();
		session.close();
		connection.close();
		initialContext.close();
		
	}

}
