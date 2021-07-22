package _02_mapping;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name="entity_person") // -> defines a entity
@Access(AccessType.FIELD)  // access type field (class level) or property (getter and setter)
@Table(name="users") // we can give table name
public class User {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment
    //@GeneratedValue(strategy = GenerationType.AUTO) // provider handle generation of primary key
    //@GeneratedValue(strategy = GenerationType.SEQUENCE) // uses sequence if database supports
    //@GeneratedValue(strategy = GenerationType.TABLE) // uses an underlying database table that holds segments of identifier generation values.
    private Long id;

    // changing default column properties
    @Column(name = "person_name",unique = false,nullable = false, length = 20)
    private String fullName;

    //@Transient -> makes entity not persistent
    @Column(name="person_age",unique = false,nullable = true,length = 3)
    private int age;

    //mapping dates
    //@Temporal(value = TemporalType.TIMESTAMP) // -> year-month-day-hour-minute-second
    @Temporal(value = TemporalType.DATE) // -> year,month,day
    // @Temporal(value = TemporalType.TIME) // -> hour minute second
    private Date birthDay;

    // mapping enums
    @Enumerated(value=EnumType.STRING)
    //@Enumerated(value = EnumType.ORDINAL)
    private Gender gender;

    // Unidirectional relationship
    // In mail classes, there won't be an instance for Person class
    // FetchType Lazy -> is a design pattern which is used to defer initialization of an object as long as it's possible
    // FetchType Eager -> Eager Loading is a design pattern in which data initialization occurs on the spot
    // cascade -> When we perform some action on the target entity, the same action will be applied to the associated entity.
    @OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "mail_id",unique = true) // if you do not use unique attribute this won't be real one-to-one relationship.
    private Mail mail;

    // Bidirectional relationship
    // In Address class, there will be instance variable for Person
    // We must declare owner side of relationship to prevent circular dependency
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    // unidirectional one to many relationship
    @OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinTable(
            name="user_course",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = @JoinColumn(name="company_id")
    )
    // or  @JoinColumn(name="company_id,unique=false") -> no extra table
    private List<Company> companyList;


    // Unidirectional relationship
    // There won't be an instance variable for person class in courses class
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(
            name="person_courses",
            joinColumns = @JoinColumn(name="person_id"),
            inverseJoinColumns = @JoinColumn(name="course_id")
    )
    private List<Course> coursesList;
}
