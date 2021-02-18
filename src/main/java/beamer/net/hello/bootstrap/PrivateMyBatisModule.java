package beamer.net.hello.bootstrap;

import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import com.google.inject.PrivateModule;

public abstract class PrivateMyBatisModule extends PrivateModule {

	protected <T> void register(Class<T> serviceInterface, Class<? extends T> serviceImpl) {
		bind(serviceInterface).to(serviceImpl).asEagerSingleton();
		expose(serviceInterface);
		bind(serviceImpl);
		expose(serviceImpl);
	}

	@Override
	protected void configure() {

		install(new MyBatisModule() {
			@Override
			protected void initialize() {
				install(JdbcHelper.MySQL);
				
				bindDataSourceProviderType(PooledDataSourceProvider.class);
				bindTransactionFactoryType(JdbcTransactionFactory.class);

				Class<?>[] classes = mapperClasses();
				for (Class<?> cls : classes) {
					addMapperClass(cls);
				}

				for (Class<?> cls : classes) {
					expose(cls);
				}
			}
		});
	}

	protected abstract Class[] mapperClasses();

}