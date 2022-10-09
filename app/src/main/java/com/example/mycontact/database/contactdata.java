package com.example.mycontact.database;

public class contactdata {


    public String name="";
    public String number="";
    public String workplace="";
    public String qunzu="";
    public String e_mail="";
    public String music="";
    public String _id="";

    public contactdata(String _id,String name,String phone,String place,String group,String email,String music) {
        this._id=_id;
        this.name = name;
        this.number=phone;
        this.workplace=place;
        this.qunzu=group;
        this.e_mail=email;
        this.music=music;

    }

}
