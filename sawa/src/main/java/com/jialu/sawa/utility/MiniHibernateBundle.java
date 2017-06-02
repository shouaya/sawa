package com.jialu.sawa.utility;

import javax.persistence.Entity;

import org.reflections.Reflections;

import com.google.common.collect.ImmutableList;
import com.jialu.sawa.configuration.MiniConfiguration;

import io.dropwizard.ConfiguredBundle;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;

public class MiniHibernateBundle extends HibernateBundle<MiniConfiguration>
		implements ConfiguredBundle<MiniConfiguration> {

	private static String daoPackage;
	
	public MiniHibernateBundle() {
		super(getEntities(), new SessionFactoryFactory());
	}

	private static ImmutableList<Class<?>> getEntities() {
		Reflections reflections = new Reflections(daoPackage);
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
	
	public void setDaoPackage(String daoPackage){
		MiniHibernateBundle.daoPackage = daoPackage;
	}

	public String getPackageDao(MiniConfiguration configuration) {
		MiniHibernateBundle.daoPackage = configuration.getPackageDao();
		return MiniHibernateBundle.daoPackage;
	}
}
