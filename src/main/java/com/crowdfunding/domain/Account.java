package com.crowdfunding.domain;

import com.crowdfunding.common.enums.SexEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by LiMeiyuan on 2016/9/26 14:39.
 * description:
 */
@Entity
@Table(name = "account_ac")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_ac", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "student")
public class Account {
    @Id
    @GeneratedValue
    @Column(name = "id_ac")
    private Long id;

    @Column(name = "userpwd_ac")
    private String password;

    @Column(name = "opentype_ac")
    private int opentype; // 1:weibo 2:weixin 3:qq

    @Column(name = "openid_ac")
    private String openid;

    @Column(name = "photo_ac")
    private String imgpath;

    @Column(name = "username_ac")
    private String username;

    @Column(name = "screenname_ac")
    private String screenname;

    @Column(name = "sex_ac")
    @Type(type = "com.crowdfunding.common.util.EnumUserType", parameters = {
            @org.hibernate.annotations.Parameter(name = "enumClassName", value = "com.crowdfunding.common.enums.SexEnum"),
            @org.hibernate.annotations.Parameter(name = "recreateEnumMthd", value = "recreateEnum"),
            @org.hibernate.annotations.Parameter(name = "recreateStringMthd", value = "recreateString")
    })
    private SexEnum sex;

    @Column(name = "phonearea_ac")
    private String phonearea;

    @Column(name = "phonenumber_ac")
    private String phonenumber;

    @Column(name = "birthday_ac")
    private String birthday;//生日

    @Column(name = "email_ac")
    private String email;

    @Column(name = "study_date_ac")
    private String studyDate;//留学日期

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "account_invitation_ship_ais",
            joinColumns = {@JoinColumn(name = "account_id_ais")},
            inverseJoinColumns = {@JoinColumn(name = "inviter_id_ais")})
    @JsonBackReference
    private Account inviter;

    @OneToMany(mappedBy = "inviter", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Account> invitedAccounts;

    @Column(name = "lifeold_ac")
    private Integer lifeOld;//如果是buddy，需要填写海外生活的时间，单位年

    @Column(name = "createtime_ac")
    private Date creattime;

    @Column(name = "token_ac")
    private String token;//融云token

    @Column(name = "invitation_ac")
    private String invitationCode;

    @Column(name = "balance", columnDefinition = "decimal(10,2) DEFAULT '0.00'")
    private BigDecimal balance = BigDecimal.valueOf(0);

    @Column(name = "profit", columnDefinition = "decimal(10,2) DEFAULT '0.00'")
    private BigDecimal profit = BigDecimal.valueOf(0);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getOpentype() {
        return opentype;
    }

    public void setOpentype(int opentype) {
        this.opentype = opentype;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getScreenname() {
        return screenname;
    }

    public void setScreenname(String screenname) {
        this.screenname = screenname;
    }

    public String getPhonearea() {
        return phonearea;
    }

    public void setPhonearea(String phonearea) {
        this.phonearea = phonearea;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudyDate() {
        return studyDate;
    }

    public void setStudyDate(String studyDate) {
        this.studyDate = studyDate;
    }

    public Account getInviter() {
        return inviter;
    }

    public void setInviter(Account inviter) {
        this.inviter = inviter;
    }

    public List<Account> getInvitedAccounts() {
        return invitedAccounts;
    }

    public void setInvitedAccounts(List<Account> invitedAccounts) {
        this.invitedAccounts = invitedAccounts;
    }

    public SexEnum getSex() {
        return sex;
    }

    public void setSex(SexEnum sex) {
        this.sex = sex;
    }

    public Integer getLifeOld() {
        return lifeOld;
    }

    public void setLifeOld(Integer lifeOld) {
        this.lifeOld = lifeOld;
    }

    public Date getCreattime() {
        return creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }
}
