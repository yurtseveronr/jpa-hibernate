package _02_mapping;

import javax.persistence.*;

@Entity
@Table(name = "mails")
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "mail",unique = true,nullable = false, length = 100)
    private String mail;




}
