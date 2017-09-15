package com.wanted.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

/**
 * Created by t on 2017/2/27.
 */
@Entity
@Data
public class Freshmen {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String tel;
    private Date time;
    private String sex;
    private String profession;
    private String text;
    private String email;
    public Freshmen(){}
    public Freshmen(String email,String name,String tel,String pro,String text,String sex,Date time){
        this.name=name;
        this.tel=tel;
        this.email=email;
        this.profession=pro;
        this.text=text;
        this.sex=sex;
        this.time=time;
    }

}
