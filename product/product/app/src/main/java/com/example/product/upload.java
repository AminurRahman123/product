package com.example.product;

public class upload {
    private String iname;
    private String iimageurl;

    public upload(){
        //empty constructor needed
    }

    public upload (String name, String imageurl){
        if (name.trim().equals("")){
            name = "Unlabled Image";
        }
        iname = name;
        iimageurl = imageurl;
    }

    public String getName(){
        return iname;
    }

    public void setName(String name){
    iname = name;
    }

    public String getImageurl(){
        return iimageurl;
    }

    public void setImageurl(String imageurl){
       iimageurl = imageurl;
    }
}
