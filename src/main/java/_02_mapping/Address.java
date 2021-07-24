package _02_mapping;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="country",length = 25,nullable = false,unique = false)
    private String country;

    @Column(name="city",length = 100,nullable = false,unique = false)
    private String city;

    @Column(name="district",length = 100,nullable = false,unique = false)
    private String district;

    // Bidirectional mapping -- inverse side
    @OneToOne(mappedBy = "address") // -> address is the instance variable in User class
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
