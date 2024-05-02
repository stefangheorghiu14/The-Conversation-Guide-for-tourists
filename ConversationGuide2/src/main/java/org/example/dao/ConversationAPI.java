/*
Interfata care defineste operatiile disponibile pentru manipularea datelor din baza de date.
Folosim abstractizarea cand definim operatiile fara a specifica implementarea lor(operatiile fiind reprezentate de
apelurile metodelor get(),getByOptionAndLanguage(),getOptionVersion(),getLanguageVersion(),save(),update(),
delete()
 */
package org.example.dao;
import org.example.exception.ExceptionTableEmpty;
import java.sql.SQLException; //importa SQLException
import java.util.LinkedHashSet;
import java.util.Optional; //importa Optional

public interface ConversationAPI<T> { //declaram interfata generics ConversationAPI
        //metoda pentru a obtine un obiect optional in functie de id
        Optional<T> get(Integer id) throws SQLException;//T-genericul
    //se returneaza obiectul cu id-ul ca parametru
        Optional <String> getByOptionAndLanguage(String option_name, String language);
        //returneaza translation in functie de option_name si language
        Optional <LinkedHashSet<String>> getOptionVersion();
        //returneaza optiunile unice
    Optional<LinkedHashSet<String>> getLanguageVersion(String option_name);
    //returneaza limbile disponibile pentru versiunea pe care o avem

    void save(T t) throws ExceptionTableEmpty;//salvam un obiect
    void update(Integer id, String option_name, String language, String translation) throws
            ExceptionTableEmpty;// poate arunca exceptia noastra(in caz de eroare)
    void delete() throws ExceptionTableEmpty;//stergem un obiect
}

