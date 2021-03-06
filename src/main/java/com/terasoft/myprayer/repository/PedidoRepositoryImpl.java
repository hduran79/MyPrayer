package com.terasoft.myprayer.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.aspectj.apache.bcel.generic.Type;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Hibernate;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class PedidoRepositoryImpl implements PedidoRepository {

	@Autowired
	private SessionFactory sessionFactory;  	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public Long insert(Pedido pedido) {
		return (Long) getSession().save(pedido);
	}

	public void update(Pedido pedido) {
		getSession().update(pedido);

	}

	public void delete(Pedido pedido) {
		getSession().delete(pedido);

	}

	public Long count() {
		return (Long) getSession().createQuery("select count(*) from Pedido").uniqueResult();
	}

	public List<Pedido> findAll() {
		return getSession().createQuery("from Pedido").list();
	}

	public List<Pedido> find(Pedido pedido) {
		// TODO Auto-generated method stub
		return null;
	}

	public Pedido findById(Long pedicons) {
		return (Pedido) getSession().createQuery("from Pedido p where p.pedicons = :pedicons")
				.setLong("pedicons", pedicons).uniqueResult();
	}

	public List<Pedido> findByMotivo(String pedimoti) {
		Query query = getSession().createQuery("from Pedido p where p.pedimoti like :pedimoti");
		query.setString("persnomb", "%"+pedimoti+'%');
		return query.list();
	}
	
	public List<Object[]> countBySexo() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		String sql = "select pedisexo, count(*) cant " + "\n" +
					 "from pedido " + "\n" +
					 "where pedifech >= :fecha " + "\n" +
					 "group by pedisexo";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql)
			.addScalar("pedisexo", StringType.INSTANCE)
			.addScalar("cant", LongType.INSTANCE)
			.setParameter("fecha", cal.getTime());
		
		return query.list();
	}
	
	public List<Object[]> countByMotivo() {
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
	    
		String sql = "select tbclave, count(*) cant " + "\n" +
					 "from tbtablas, pedido " + "\n" + 
					 "where pedifech >= :fecha " + "\n" +
					 "  and tbcodigo = 'PEDIMOTI' "+ "\n" +
					 "  and pedimoti like tbvalstr " + "\n" +
					 "group by tbclave ";
		Query query = getSession().createSQLQuery(sql)
				.addScalar("tbclave", StringType.INSTANCE)
				.addScalar("cant", LongType.INSTANCE)
				.setParameter("fecha", cal.getTime());
				
		return query.list();
	}

}
