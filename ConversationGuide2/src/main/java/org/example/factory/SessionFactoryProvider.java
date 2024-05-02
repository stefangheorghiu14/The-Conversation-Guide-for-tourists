/*
Aceasta clasa este similara cu HibernateUtil, furnizeaza o instanta de SessionFactory.

Prin incapsulare, ascundem detaliile de configurare ale sesiunii Hibernate(de ex: specificatiile pentru baza de
date si alte detalii de implementare sunt gestionate intern in cadrul acestei clase si nu sunt expuse publicului
in alta parte a codului).
 */
package org.example.factory;
import org.hibernate.SessionFactory;//importam clasele SessionFactory si Configuration din Hibernate
import org.hibernate.cfg.Configuration;

public class SessionFactoryProvider {//Declaram clasa
        public static SessionFactory provideSessionFactory()//furnizam o instanta de SessionFactory
        {
            Configuration config=new Configuration();
            config.configure();//initializam si configuram obiectul Configuration
            return config.buildSessionFactory();//construim si returnam sessionFactory
        }
}
