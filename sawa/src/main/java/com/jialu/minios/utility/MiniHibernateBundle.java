package com.jialu.minios.utility;

import javax.persistence.Entity;

import org.reflections.Reflections;

import com.google.common.collect.ImmutableList;
import com.jialu.minios.configuration.MiniConfiguration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;

public class MiniHibernateBundle extends HibernateBundle<MiniConfiguration>
		implements ConfiguredBundle<MiniConfiguration> {

	public MiniHibernateBundle() {
		super(getEntities(MiniConstants.MODEL_PACKAGE), new SessionFactoryFactory());
	}

	private static ImmutableList<Class<?>> getEntities(String pageName) {
		Reflections reflections = new Reflections(pageName);
		ImmutableList<Class<?>> entities = ImmutableList.copyOf(reflections.getTypesAnnotatedWith(Entity.class));
		return entities;
	}

	@Override
	public PooledDataSourceFactory getDataSourceFactory(MiniConfiguration configuration) {
		return configuration.getDatabase();
	}
	
	@Override
	protected void configure(org.hibernate.cfg.Configuration configuration){
		configuration.addResource("query.xml");
		configuration.addResource("crud.xml");
	}

}
