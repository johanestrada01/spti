package co.edu.eci.cvds.model;

import jakarta.persistence.*;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

@Entity
@Table(name = "QUOTATION_USER")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "QUOTE_USER_ID")
    private int userId;

    @Column(name = "USER_NAME", nullable = false, unique = true)
    private String userName;

    @Column(name = "USER_IDENTIFICATION", nullable = false, unique = true)
    private int identificationNumber;

    @Column(name = "TELEPHONE_NUMBER", nullable = false, unique = true)
    private int telephoneNumber;

    @Column(name = "USER_EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "USER_PASSWORD", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Quotation> quotations;

    public User(){}

    public User(String userName, int identificationNumber, int telephoneNumber, String email, String password) {
        this.userName = userName;
        this.identificationNumber = identificationNumber;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        setPassword(password);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(int identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public int getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(int telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String password){
        String hashPassword = getPassword();
        return BCrypt.checkpw(password, hashPassword);
    }

    public List<Quotation> getQuotations(){
        return quotations;
    }

    public void setQuotations(List<Quotation> quotations){
        this.quotations = quotations;
    }

    public void addQuotation(Quotation quotation){
        quotations.add(quotation);
    }

}
