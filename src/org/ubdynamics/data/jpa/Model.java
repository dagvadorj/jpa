package org.ubdynamics.data.jpa;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.Query;
import org.hibernate.Session;

@MappedSuperclass
public class Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@SuppressWarnings("unchecked")
	public <T extends Model> T save() {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(this);
        session.getTransaction().commit();

        return (T) this;

	}

	@SuppressWarnings("unchecked")
	public <T extends Model> T delete() {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.delete(this);
        session.getTransaction().commit();

        return (T) this;

	}
	
	public JPAQuery find() {

		Session session =
				HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Query query = session.createQuery("from " + 
				this.getClass().getSimpleName());

        return new JPAQuery(query, session);

	}

	public JPAQuery find(String queryString, Object... params) {

		Session session =
				HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Query query = session.createQuery("from " + this.getClass().getSimpleName() + 
        		((queryString.trim().length()>0) ? " where " + queryString : ""));

        for (int i=0; i<params.length; i++) {
        	query.setParameter(0, params[i]);
        }

        return new JPAQuery(query, session);

	}
	
	public static class JPAQuery {

		private Query query;
		private Session session;

		public JPAQuery(Query query, Session session) {
			this.query = query;
			this.session = session;
		}

		@SuppressWarnings({ "unchecked" })
		public <T extends Model> List<T> fetch() {
			List<T> results = this.query.list();
			this.session.getTransaction().commit();
			return results;
		}

		@SuppressWarnings({ "unchecked" })
		public <T extends Model> List<T> fetch(int max) {
			this.query.setMaxResults(max);
			List<T> results = this.query.list();
			this.session.getTransaction().commit();
			return results;
		}

		@SuppressWarnings({ "unchecked" })
		public <T extends Model> List<T> fetch(int pageIndex, int size) {
			if (pageIndex < 0) pageIndex = 0;
			this.query.setFirstResult(pageIndex*size);
			this.query.setMaxResults(size);
			List<T> results = this.query.list();
			this.session.getTransaction().commit();
			return results;
		}

		public JPAQuery from(int fromIndex) {
			this.query.setFirstResult(fromIndex);
			return this;
		}

	};
	
}
