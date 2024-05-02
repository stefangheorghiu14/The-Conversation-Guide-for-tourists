/*
Aceasta clasa reprezinta modelul pentru o inregistrare din tabelul bazei de date.

Se utilizeaza incapsularea pentru a proteja membrii clasei(public, private)
Prin polimorfism, folosim metodele getter si setter pentru a accesa si modifica membrii clasei.
 */
package org.example.model;
import jakarta.persistence.*;
import lombok.*;
//Conversation reprezinta o linie din tabel

//Urmeaza sa adaugam adnotarile pentru a genera automat o metoda toString, getteri,setteri
//un constructor fara argumente, cu toti parametri
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity//aceasta adnotare marcheaza clasa ca o entitate Hibernate
@Table(name="translations")
//prin aceasta adnotare specificam numele tabelului din baza de date
public class Conversation {
    @Id//adnotare: specificam ca acest camp este id-ul
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//campul pentru id(si mai jos pentru optiune, limba, traducere)
    @Column(name="option_name", length=50, nullable=false, unique=false)
    //specificam coloana din baza de date
    private String option_name;
    @Column(name="language", length=50, nullable=false, unique=false)
    private String language;
    @Column(name="translation", length=50, nullable=false, unique=false)
    private String translation;

    /*
    Aici, constructorul Conversation primeste 3 argumente: "option_name","language" si "translate".
    Acest constructor initializeaza campurile corespunzatoare ale obiectului de tip Conversation cu
    valorile primite ca parametri.
    */
    public Conversation(String option_name, String language, String translation) {
        this.setOption_name(option_name);
        this.setLanguage(language);
        this.setTranslation(translation);
    }//aici am folosit setteri pentru a actualiza valorile atributelor obiectului
}
