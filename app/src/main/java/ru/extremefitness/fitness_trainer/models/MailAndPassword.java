package ru.extremefitness.fitness_trainer.models;

import java.io.Serializable;

import ru.extremefitness.fitness_trainer.Utils;

/**
 * Created by user on 28.09.2015.
 */
public class MailAndPassword implements Serializable {
    private String mail;
    private String password;

    public MailAndPassword(final String mail, final String password) {
        this.mail = mail;
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return Utils.getMd5Hash(password);
    }


}
