package com.example.deepchemocare;

public class DoctorClass {

    public String doctor_name;
    public String description;
    public String hospital;
    public String position;
    public String gender;


    public DoctorClass()
    {

    }

    public DoctorClass(String n,String d,String h,String p,String g)
    {
        this.doctor_name=n;
        this.description=d;
        this.hospital=h;
        this.position=p;
        this.gender=g;
    }

}
