package eu.sensap.farEdge.dataRoutingClient.messageBus;


import org.apache.kafka.clients.producer.*;

import eu.sensap.farEdge.dataRoutingClient.models.ConfigurationEnv;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by glalas-dev on 9/21/2017.
 */
public class KafkaJavaProducer
{
	
	private ConfigurationEnv env;
	private static Producer<Long, String> producer;
		
	public KafkaJavaProducer (ConfigurationEnv env)
	{
		this.setEnv(env);
		producer = createProducer(env.getKafkaProps());
	}

    // creates a new static instance of Producer due to 'prop' properties
	private static Producer<Long, String> createProducer(Properties prop)
	{
    //TODO:  http://kafka.apache.org/documentation/#producerconfigs for more configurations
//        Properties prop = new Properties();
//        try {
//            //load a properties file from class path, inside static method
//            prop.load(Producer.class.getClassLoader().getResourceAsStream("producer.properties"));
//        } 
//        catch (IOException ex) {
//            ex.printStackTrace();
//        }
        return new KafkaProducer<>(prop);
    }
		
	public boolean runSyncProducer(String message, String topic) throws Exception
	{
		System.out.println("running 'runSyncProducer' in KafkaJavaProducer");
//		Thread.currentThread().setContextClassLoader(null);
		boolean status=false;

		long time = System.currentTimeMillis();
		
		try
		{
			long index = time;
			final ProducerRecord<Long, String> record = new ProducerRecord<>(topic, index, message);
			RecordMetadata metadata = producer.send(record).get();
			long elapsedTime = System.currentTimeMillis() - time;
			System.out.printf("sent record(key=%s value=%s) " + "meta(partition=%d, offset=%d) topic=%s time=%d\n", record.key(), record.value(), metadata.partition(), metadata.offset(), record.topic(), elapsedTime);
			status = true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();		
		}
		return status;
	}
	
	
	public void close()
	{
		producer.flush();
		producer.close();
	}
	
	

    // connects to a KAFKA with properties 'props' and publishes one single 'message' in a specific 'topic' 
	public boolean runSyncProducer(ConfigurationEnv env , String message) throws Exception
	{
		System.out.println("running 'runSyncProducer' in KafkaJavaProducer");
//		Thread.currentThread().setContextClassLoader(null);
		boolean status=false;
		final Producer<Long, String> producer = createProducer(env.getKafkaProps());
		long time = System.currentTimeMillis();
		
		try
		{
			long index = time;
			final ProducerRecord<Long, String> record = new ProducerRecord<>(env.getTopic(), index, message);
			RecordMetadata metadata = producer.send(record).get();
			long elapsedTime = System.currentTimeMillis() - time;
			System.out.printf("sent record(key=%s value=%s) " + "meta(partition=%d, offset=%d) topic=%s time=%d\n", record.key(), record.value(), metadata.partition(), metadata.offset(), record.topic(), elapsedTime);
			status = true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();		
		}
		finally
		{
			producer.flush();
			producer.close();
		}
		return status;
	}

//    //Synchronous a set of records
//    public void runSyncProducer(final int sendMessageCount, String topic, String message) throws Exception {
//        final Producer<Long, String> producer = createProducer();
//        long time = System.currentTimeMillis();
//        try {
//            for (long index = time; index < time + sendMessageCount; index++) {
//                final ProducerRecord<Long, String> record = new ProducerRecord<>(topic, index,message + index);
//                RecordMetadata metadata = producer.send(record).get();
//                long elapsedTime = System.currentTimeMillis() - time;
//                System.out.printf("sent record(key=%s value=%s) " + "meta(partition=%d, offset=%d) time=%d\n",
//                        record.key(), record.value(), metadata.partition(), metadata.offset(), elapsedTime);
//            }
//        } finally {
//            producer.flush();
//            producer.close();
//        }
//    }
    //Async
    public void runAsyncProducer(final int sendMessageCount, String topic, String message, Properties props) throws InterruptedException
    {
    	// TODO: rewrite this method
        final Producer<Long, String> producer = createProducer(props);
        long time = System.currentTimeMillis();
        final CountDownLatch countDownLatch = new CountDownLatch(sendMessageCount);

        try
        {
            for (long index = time; index < time + sendMessageCount; index++)
            {
                final ProducerRecord<Long, String> record =
                        new ProducerRecord<>(topic, index, message + index);
                producer.send(record, (metadata, exception) ->
                {
                    long elapsedTime = System.currentTimeMillis() - time;
                    if (metadata != null)
                    {
                        System.out.printf("sent record(key=%s value=%s) " +
                                        "meta(partition=%d, offset=%d) time=%d\n",
                                record.key(), record.value(), metadata.partition(),
                                metadata.offset(), elapsedTime);
                    }
                    else
                    {
                        exception.printStackTrace();
                    }
                    countDownLatch.countDown();
                });
            }
            countDownLatch.await(25, TimeUnit.SECONDS);
        }
        finally
        {
            producer.flush();
            producer.close();
        }
    }

	public ConfigurationEnv getEnv() {
		return env;
	}

	public void setEnv(ConfigurationEnv env) {
		this.env = env;
	}
}
