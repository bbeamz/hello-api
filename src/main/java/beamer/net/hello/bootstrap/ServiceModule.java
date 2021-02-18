package beamer.net.hello.bootstrap;

import java.util.Properties;
import java.util.ResourceBundle;

import com.google.inject.name.Names;

import beamer.net.hello.dao.HelloMapper;
import beamer.net.hello.service.HelloService;
import beamer.net.hello.service.HelloServiceImpl;

/**
 * Created by lanxiang on 16/9/6.
 */
public class ServiceModule extends PrivateMyBatisModule {

	private Properties properties;

	@Override
	protected void configure() {
		super.configure();
		register(HelloService.class, HelloServiceImpl.class);
		initDBProperties();
		Names.bindProperties(this.binder(), properties);
//        expose(Datastore.class);
	}

	private void initDBProperties() {
		properties = new Properties();
		properties.setProperty("mybatis.environment.id", "test");
		properties.setProperty("JDBC.schema", "hello");
//		properties.setProperty("derby.create", "true");
		properties.setProperty("JDBC.username", "root");
		properties.setProperty("JDBC.password", "habana7");
		properties.setProperty("JDBC.autoCommit", "false");
	}

	protected Class[] mapperClasses() {
		return new Class[] { HelloMapper.class, };
	}

//    @Provides
//    @Singleton
//    Datastore provideDataStore() {
//        Morphia morphia = new Morphia();
//        morphia.mapPackage("com.lanxiang.model.mongo");
//        return morphia.createDatastore(new MongoClient(), "learn_wbg_demo");
//    }
}