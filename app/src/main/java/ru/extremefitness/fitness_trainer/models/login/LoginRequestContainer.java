package ru.extremefitness.fitness_trainer.models.login;

import java.io.Serializable;

import ru.extremefitness.fitness_trainer.models.RootContainer;

/**
 * Created by Comp on 16.09.2015.
 */
public class LoginRequestContainer extends RootContainer {

    public static final String EXTRA_LOGIN_RESULT = "ru.extremefitness.fitness_trainer.models.login.LoginRequestContainer.EXTRA_LOGIN_RESULT";

    private UserData data;

    @Override
    public UserData getData() {
        return data;
    }

    public static class UserData implements Serializable {
        private String auth_method;
        private String avatar;
        private String birthday;
        private String email;
        private String first_name;
        private String gender;
        private String height;
        private String id;
        private String last_name;
        private String password = "";
        private String platform;
        private String pobf;
        private String social_id;
        private String status;
        private String weight;

        public String getAuthMethod() {
            return auth_method;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getBirthday() {
            return birthday;
        }

        public String getEmail() {
            return email;
        }

        public String getFirstName() {
            return first_name;
        }

        public String getGender() {
            return gender;
        }

        public String getHeight() {
            return height;
        }

        public String getId() {
            return id;
        }

        public String getLastName() {
            return last_name;
        }

        public String getPassword() {
            return password.toLowerCase();
        }

        public String getPlatform() {
            return platform;
        }

        public String getPobf() {
            return pobf;
        }

        public String getSocialId() {
            return social_id;
        }

        public String getStatus() {
            return status;
        }

        public String getWeight() {
            return weight;
        }

        public void setAuthMethod(final int method) {
            this.auth_method = String.valueOf(method);
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
