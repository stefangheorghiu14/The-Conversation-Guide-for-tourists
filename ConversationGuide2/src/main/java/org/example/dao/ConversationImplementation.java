/*
Prin aceasta clasa, implementam interfata ConversationAPI, care contine logica pentru a
interactiona cu baza de date si pentru a efectua operatiile CRUD.

Folosim incapsularea cand ascundem implementarea detaliata in spatele metodelor publice.
de exemplu in: get(), getByOptionAndLanguage(), getOptionVersion, getLanguageVersion(), delete(),save()
Prin implementarea lor, elementele de tip ConversationImplementation pot fi tratate polimorfic(in
sensul ca se poate folosi o referinta a unei clase de baza pentru a accesa obiecte ale clasei derivate
sau care implementeaza acea clasa de baza).
Cu ajutorul polimorfismului, implementam metodele din interfața ConversationAPI.
Clasa ConversationImplementation mosteneste comportamentul din interfața ConversationAPI.
 */

package org.example.dao;
//Importam modulele(clase+metode) Session, Transaction, Transactional, Configuration din Hibernate si clasa Conversation
import jakarta.transaction.Transactional;
import org.example.exception.ExceptionTableEmpty;
import org.example.model.Conversation;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.*;

public class ConversationImplementation implements ConversationAPI<Conversation> {
    //declaram clasa ConversationImplementation, care implementeaza interfata ConversationAPI
    //pentru obiecte de tip Conversation
    private final static String USERNAME = "root";//definim user-ul, parola si url-ul bazei de date
    private final static String PASSWORD = "";
    private final static String URL = "jdbc:mysql://localhost:3306/conversationguide2";
    private List<Conversation> conversations = new ArrayList<>();

    /*exemplu de utilizare conversation: in metoda get(), unde o astfel de lista de obiecte poate fi
    folosita pentru a prelua dupa ID-ul sau din lista un element de tip conversation
     */
    @Override
    public Optional<Conversation> get(Integer id) {
        /*
        Rolul metodei este de a obtine o conversatie din baza de date prin intermediul unui id specificat
         */
        try (Session session = HibernateUtil.provideSessionFactory().openSession()) {
         /*
         session- un obiect utilizat pentru a deschide o sesiune Hibernate(interfata de lucru cu datele, ce
         permite interactiunea cu baza de date)
          */
            String hql = "from Conversation p where p.id=(:id) ";
            //p este un alias pentru aceasta entitate in cadrul interogarii
            //acest string creeaza o interogare catre baza de date prin intermediul frameworkului Hibernate
            Conversation result =  session.createQuery(hql, Conversation.class).setParameter("id",
                    id).getSingleResult();
            //returneaza obiectul de tip Conversation care are acest p.id(id-ul specificat in parametrul "id")

            return Optional.ofNullable(result);//returnam rezultatul intr-un obiect Optional
            //ofNullable pentru ca ar putea fi si null

        } catch (Exception e) {
            e.printStackTrace(); //da mesaj cu eroarea
        }

        return Optional.empty();//returneaza null(un Optional gol) in caz contrar, in carenu se gaseste
        //niciun conversation cu ID-ul specificat in baza de date
    }

    @Override
    public Optional<String> getByOptionAndLanguage(String option_name, String language) {
        /*
        Rolul metodei este de a obtine traducerea pentru o anumita optiune si limba specificata din baza de
        date
         */

        try (Session session = HibernateUtil.provideSessionFactory().openSession()) {
            //prin acest session, ne conectam la baza de date
            String hql = "select p.translation from Conversation p where p.option_name=(:option_name) AND " +
                    "p.language=(:language)";

            String result = (String) session.createQuery(hql, String.class).setParameter("option_name",
                    option_name).setParameter("language", language).getSingleResult(); //cast
            /*
            Aceasta secventa de cod efectueaza o interogare catre baza de date pentru a obtine o singura
             valoare de tip String, reprezentand traducerea pentru o anumita optiune si limba.
             */

            return Optional.ofNullable(result);//returnam rezultatul intr-un obiect Optional
            // commit transaction

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
public Optional<Integer> getById(Integer id) {
        /*
        Aceasta metoda este utilizată pentru a evidenția mai clar faptul că obținem un obiect Conversation
         bazat pe ID-ul său. E mai explicita decat metoda get
         */
    try (Session session = HibernateUtil.provideSessionFactory().openSession()) {
        //Aici începe un bloc try-with-resources(o formă de gestionare a resurselor introdusă în Java)
        // pentru a deschide o sesiune Hibernate.

        String hql = "select p.id from Conversation p where p.id=(:id) ";
        Integer result = (Integer) session.createQuery(hql, Integer.class).setParameter("id",
                id).getSingleResult().intValue();

        return Optional.ofNullable(result);//returnam rezultatul intr-un obiect Optional

    } catch (Exception e) {
        e.printStackTrace();
    }
    return Optional.empty();
}


    @Override
    public Optional<LinkedHashSet<String>> getOptionVersion() {
        /*
        Aceasta metoda returneaza o lista distincta de optiuni disponibile pentru o "conversatie"
        din baza de date
         */
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>(20, 3);
        //acest hashset are numarul maxim de elemente de retinut 20 si factor de incarcare 3
        try (Session session = HibernateUtil.provideSessionFactory().openSession()) {
            String hql = "select distinct p.option_name from Conversation p ";
            //distinct=un cuvant cheie care indica faptul ca trebuie returnate doar rezultate distincte

            linkedHashSet.addAll(session.createQuery(hql, String.class).getResultList());
            //se adauga toate elementele in lista noastra
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        Aceasta constructie cu try-catch selecteaza toate optiunile unice pentru conversatie din
        baza de date si le adauga intr-un set pentru a le returna mai departe. De asemenea,
        are rolul de a furniza o lista de optiuni disponibile pentru utilizator.
         */
        return Optional.ofNullable(linkedHashSet);
    }

    @Override
    public Optional<LinkedHashSet<String>> getLanguageVersion(String option_name) {

        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>(20, 3);
        try (Session session = HibernateUtil.provideSessionFactory().openSession()) {
            String hql = "select distinct p.language from Conversation p WHERE p.option_name=:option_name";
            //pentru option_name ni se vor da toate posibilitatile de language
            linkedHashSet.addAll(session.createQuery(hql, String.class).setParameter
                    ("option_name", option_name).getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(linkedHashSet);
    }

    @Override
    public void update(Integer id, String option_name, String language, String translation) throws
            ExceptionTableEmpty {
        final Boolean isEmpty = getConversationByAttributes(option_name, language, translation).isEmpty();
        final Boolean isEmpty2 = getById(id).isEmpty();
        /* isEmpty si isEmpty2 sunt utilizate pentru a verifica daca rezultatul obtinut din
        apelul metodelor getConversationByAttributes, respectiv getById este gol sau nu
         */
        try{ //verificam daca elementul pe care vrem sa il actualizam exista deja
            if (!isEmpty)
                throw new ExceptionTableEmpty("Elementul deja exista");
            if(isEmpty2)//daca id-ul este valid
                throw new ExceptionTableEmpty("Elementul de actualizat nu exista");
        }catch (ExceptionTableEmpty e)
        {
            System.out.println(e.getMessage());
            return;
        }


        try (Session session = HibernateUtil.provideSessionFactory().openSession()) {
            //se updateaza indexul cerut
            Transaction txn = session.beginTransaction();
            /*
            txn(tranzactie Hibernate) este utilizata pentru a gestiona operatiunile de salvare si actualizare
            in baza de date
             */
            String hql = "UPDATE Conversation c SET c.option_name = ?1, c.language = ?2, c.translation=?3" +
                    " WHERE c.id = ?4";
            session.createQuery(hql).setParameter(1,option_name).setParameter(2,language).setParameter
                            (3,translation).setParameter(4,id).executeUpdate();
            //updateaza elementele noi
            txn.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //Implementarea metodei update din interfata ConversationAPI lipseste
    }

    private Optional<String> getConversationByAttributes(String option_name, String language, String translation) {
        try (Session session = HibernateUtil.provideSessionFactory().openSession()) {
            //asociem atributele
            String hql = "select p.option_name from Conversation p where p.option_name=(:option_name) AND " +
                    "p.language=(:language) AND p.translation=:translation";
            return Optional.of(session.createQuery(hql, String.class).setParameter("option_name",
                    option_name).setParameter("language", language).setParameter("translation",translation).getSingleResult()) ;//cast

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    /*
    Aceasta metoda este responsabila pentru obtinerea unei conversatii din baza de date in functie de anumite
    atribute precum "option_name","language" si "translation"
     */
    @Override
    @Transactional
    public void delete() throws ExceptionTableEmpty {
        //stergem ultimul element daca tabelul nu este gol
        final Boolean isEmpty = getOptionVersion().isEmpty();
        if (isEmpty)
            throw new ExceptionTableEmpty("Tabelul este gol");

        try (Session session = HibernateUtil.provideSessionFactory().openSession()) {

            Transaction txn = session.beginTransaction();
            String hql = "DELETE from Conversation p WHERE p.id=(select max(conversation2.id) " +
                    "from Conversation conversation2)";
            session.createQuery(hql).executeUpdate();
            txn.commit();


        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        Metoda delete este folosita pentru a sterge(in implementare) ultimul element din tabelul
        "translations".
         */
    }
    @Override
    public void save(Conversation conversation) throws ExceptionTableEmpty {

        try { //adaugam un element nou daca nu exista deja unul identic
            final Boolean isEmpty = getConversationByAttributes(conversation.getOption_name(),
                    conversation.getLanguage(), conversation.getTranslation()).isEmpty();
            if(!isEmpty)//daca  exista
                throw new ExceptionTableEmpty("Elementul deja exista");//arunca eroare
        }catch (ExceptionTableEmpty e)
        {
            System.out.println(e.getMessage());
            return;
        }
        Session session= HibernateUtil.provideSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(conversation);
        transaction.commit();
        session.close();
    }
}//Metoda save adauga o noua conversatie in tabelul "translations"

