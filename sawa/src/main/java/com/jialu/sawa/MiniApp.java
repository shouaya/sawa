package com.jialu.sawa;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.AbstractDAO;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.hibernate.SessionFactory;

import com.google.common.reflect.ClassPath;
import com.jialu.sawa.base.dao.MiniUserDao;
import com.jialu.sawa.base.resource.CustOption;
import com.jialu.sawa.configuration.MiniConfiguration;
import com.jialu.sawa.utility.MiniAuthenticator;
import com.jialu.sawa.utility.MiniAuthorizer;
import com.jialu.sawa.utility.MiniBean;
import com.jialu.sawa.utility.MiniConstants;
import com.jialu.sawa.utility.MiniHibernateBundle;
import com.jialu.sawa.vo.OperatorRole;
import com.twilio.Twilio;

public class MiniApp extends Application<MiniConfiguration> {

	private final ClassLoader loader = Thread.currentThread().getContextClassLoader();

	private final MiniBean bean = new MiniBean();
	
	private MiniHibernateBundle hibernate;

	public static void main(String[] args) throws Exception {
		new MiniApp().run(args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.dropwizard.Application#getName()
	 */
	@Override
	public String getName() {
		return MiniConstants.APP_NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.dropwizard.Application#initialize(io.dropwizard.setup.Bootstrap)
	 */
	@Override
	public void initialize(Bootstrap<MiniConfiguration> bootstrap) {
		bootstrap.addBundle(new MigrationsBundle<MiniConfiguration>(){
	        @Override
	        public DataSourceFactory getDataSourceFactory(MiniConfiguration configuration) {
	            return configuration.getDatabase();
	        }
	    });
		final MiniHibernateBundle hibernate = new MiniHibernateBundle(){
			@Override
	        public String getPackageDao(MiniConfiguration configuration) {
	            return configuration.getPackageDao();
	        }
		};
		bootstrap.addBundle(hibernate);
		this.hibernate = hibernate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.dropwizard.Application#run(io.dropwizard.Configuration,
	 * io.dropwizard.setup.Environment)
	 */
	@Override
	public void run(MiniConfiguration configuration, Environment environment)
			throws IOException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {
		setConfig(configuration);
		setService(configuration);
		loadAllDao(configuration, hibernate.getSessionFactory());
		registerApi(configuration, environment);

	}

	/**
	 * @param configuration
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private void setConfig(MiniConfiguration configuration) throws IllegalAccessException, InvocationTargetException {
		BeanUtils.copyProperties(bean, configuration);
	}

	/**
	 * @param configuration
	 */
	private void setService(MiniConfiguration configuration) {
		// twilio SMS
		Twilio.init(configuration.getSms().getSid(), configuration.getSms().getToken());
	}

	/**
	 * @param factory
	 * @throws IOException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private void loadAllDao(MiniConfiguration configuration, SessionFactory factory) throws IOException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Set<Class<?>> allClasses = ClassPath.from(loader).getTopLevelClasses(configuration.getPackageDao()).stream()
				.map(info -> info.load()).collect(Collectors.toSet());
		for (Class<?> resource : allClasses) {
			Constructor<?> cs = resource.getDeclaredConstructor(new Class[] { SessionFactory.class });
			bean.getDaoList().put(resource.getName(), (AbstractDAO<?>) cs.newInstance(factory));
		}
		//load base dao
		bean.getDaoList().put(MiniUserDao.class.getName(), new MiniUserDao(factory));
	}

	/**
	 * @param environment
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	private void registerApi(MiniConfiguration configuration, Environment environment) throws InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, IOException {

		// filter
		final ResponseFilter repfilter = new ResponseFilter();
		environment.jersey().register(repfilter);

		// healthCheck
		final MiniHealthCheck healthCheck = new MiniHealthCheck(hibernate.getSessionFactory());
		environment.healthChecks().register(this.getName(), healthCheck);

		// resources
		registerAllResources(environment, configuration);

		// authorization
		environment.jersey()
				.register(new AuthDynamicFeature(new MiniAuthFilter.Builder<OperatorRole>()
						.setAuthenticator(new MiniAuthenticator(bean, hibernate.getSessionFactory()))
						.setAuthorizer(new MiniAuthorizer()).setRealm(MiniConstants.APP_NAME).buildAuthFilter()));
		environment.jersey().register(RolesAllowedDynamicFeature.class);
		environment.jersey().register(new AuthValueFactoryProvider.Binder<>(OperatorRole.class));

	}

	/**
	 * @param environment
	 * @param packageName
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private void registerAllResources(Environment environment, MiniConfiguration configuration)
			throws IOException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {
		Set<Class<?>> allClasses = ClassPath.from(loader).getTopLevelClasses(configuration.getPackageResource()).stream()
				.map(info -> info.load()).collect(Collectors.toSet());
		for (Class<?> resource : allClasses) {
			Constructor<?> cs = resource.getDeclaredConstructor(new Class[] { MiniBean.class });
			environment.jersey().register(cs.newInstance(bean));
		}
		//load base resource
		if(configuration.getUseBaseRegist()) {
			environment.jersey().register(new CustOption(bean));
		}
	}
}
