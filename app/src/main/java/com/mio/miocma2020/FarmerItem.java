package com.mio.miocma2020;

import java.io.Serializable;

public class FarmerItem implements Serializable {
    String fno, fname, faddress, fgender, fphone,foto;

    public FarmerItem(String fno, String fname, String faddress, String fgender, String fphone, String fitem ) {
        this.fno = fno;
        this.fname = fname;
        this.faddress = faddress;
        this.fgender = fgender;
        this.fphone = fphone;
        this.foto= fitem;
    }


    public String getNo() {
        return fno;
    }

    public String getName() {
        return fname;
    }

    public String getAddress() {
        return faddress;
    }

    public String getGender(){
        return fgender;
    }

    public String getPhone() {
        return fphone;
    }

    public String getFoto(){
        return foto;
    }
}

