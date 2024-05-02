/*
Aceasta este clasa utilitara care furnizeaza o instanta de SessionFactory pentru a interactiona cu
 Hibernate(clasa contine o metoda statica care creeaza si returneaza un obiect SessionFactory).
 Aceasta instanta de SessionFactory este esentiala pentru a interactiona cu Hibernate in cadrul aplicatiei.
Folosim incapsularea prin ascunderea detaliilor de configurare ale sesiunii Hibernate(acestea fiind ascunse
in interiorul metodei "provideSesionFactory", ce este statica si poate fi accesata prin intermediul clasei
HibernateUtil
 */

package org.example.dao;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.nio.file.Paths;

public class HibernateUtil {
    public static SessionFactory provideSessionFactory()
    { //prin aceasta metoda, furnizam o instanta de SessionFactory
        Configuration config=new Configuration(); //initializam obiectul config, de tip Configuration
        config.configure(Paths.get("src/main/resources/hibernate.cfg.xml").toFile());
        //utilizam Hibernate pentru a interactiona cu baza de date
        //configuram obiectul Configuration din fisierul hibernate.cfg.xml
        return config.buildSessionFactory();//construim si returnam SessionFactory
    }
}
