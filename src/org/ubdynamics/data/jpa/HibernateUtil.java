package org.ubdynamics.data.jpa;

import java.util.Collection;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	// private static ServiceRegistry serviceRegistry;

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			Configuration config = new Configuration();
			config.configure();

			// TODO: Remove
			// config.setProperty("hibernate.dialect",
			// "org.hibernate.dialect.MySQLInnoDBDialect");
			// config.setProperty("connection.driver_class", "org.h2.Driver");
			// config.setProperty("connection.url", "jdbc:h2:db");
			// config.setProperty("connection.username", "sa");
			// config.setProperty("connection.password", "");

			Reflections mappingClassReflections = new Reflections("db");

			Collection<Class<? extends Model>> mappingClasses = mappingClassReflections
					.getSubTypesOf(Model.class);

			for (Class<? extends Model> c : mappingClasses) {
				config.addAnnotatedClass(c);
			}

			return config.buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
