package br.com.jms;

import java.io.StringWriter;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.xml.bind.JAXB;

import br.com.caelum.modelo.Pedido;
import br.com.caelum.modelo.PedidoFactory;

public class TesteProdutorTopico {

	public static void main(String[] args) throws Exception {
		InitialContext initialContext = new InitialContext();
		
		ConnectionFactory factory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination topico = (Destination) initialContext.lookup("loja");
		
		MessageProducer producer = session.createProducer(topico);
		
		Pedido pedido = new PedidoFactory().geraPedidoComValores();
		
		Message message = session.createObjectMessage(pedido);
		//message.setBooleanProperty("ebook", true);
		producer.send(message);			
				
		//new Scanner(System.in).nextLine();
		
		producer.close();
		session.close();
		connection.close();
		initialContext.close();
		
	}

}
