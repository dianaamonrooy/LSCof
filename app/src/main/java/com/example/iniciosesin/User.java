package com.example.iniciosesin;

public class User {
    private String Id;
    private String email;
    private String date;
    private String location;
    private String aprende_practica;
    private String url;
    private String nombre;
    private String apellidos;


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAprende_practica() {
        return aprende_practica;
    }

    public void setAprende_practica(String aprende_practica) {
        this.aprende_practica = aprende_practica;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public User(String nombre,String apellidos,String id, String email, String date, String location, String aprende_practica, String url) {
        this.setNombre(nombre);
        this.setApellidos(apellidos);
        this.setUrl(url);
        this.setAprende_practica(aprende_practica);
        this.setId(id);
        this.setEmail(email);
        this.setDate(date);
        this.setLocation(location);
    }



}
