package _02_mapping;

import javax.persistence.*;

@Entity
@Table(name="companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="company_name",length = 40,nullable = false,unique = false)
    private String companyName;
}