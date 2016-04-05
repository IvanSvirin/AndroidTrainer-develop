package ru.extremefitness.fitness_trainer.models.login;

import android.content.Context;
import android.text.TextUtils;

import com.facebook.GraphResponse;
import com.google.android.gms.plus.model.people.Person;
import com.twitter.sdk.android.core.models.User;
import com.vk.sdk.api.model.VKApiUser;

import org.json.JSONException;
import org.json.JSONObject;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.Utils;

/**
 * Created: Krylov
 * Date: 17.09.2015
 * Time: 1:28
 */
public class NetLogin {

    public enum SocialNet {
        NOT_DETERMINED,
        VKONTAKTE,
        TWITTER,
        FACEBOOK,
        GOOGLE,
        EXTREME,
        TRAINER;

        public static String getStringByInt(final Context context, final String ordinal) {
            return SocialNet.values()[Integer.parseInt(ordinal)].getName(context);
        }

        public String getName(final Context context) {
            if (this == NOT_DETERMINED) {
                return context.getString(R.string.not_determined);
            }
            return toString();
        }

        public static SocialNet getSocialNet(final String netNumber) {
            if (TextUtils.isEmpty(netNumber)) {
                throw new IllegalArgumentException("null == net");
            }

            final int number = Integer.parseInt(netNumber);
            final SocialNet[] array = SocialNet.values();
            if (number >= array.length) {
                throw new IllegalArgumentException("numberNumber not found");
            }
            return array[number];
        }

    }

    public enum Gender {
        NOT_DETERMINED(R.string.set_gender),
        FEMALE(R.string.female),
        MALE(R.string.male);

        Gender(final int nameResource) {
            this.nameResource = nameResource;
        }

        private int nameResource;

        public static String getStringByInt(final Context context, final String ordinal) {
            return Gender.values()[Integer.parseInt(ordinal)].getGender(context);
        }

        public static String getInByString(final Context context, final String name) {
            if (context.getString(R.string.set_gender).equals(name)) {
                return "0";
            } else if (context.getString(R.string.female).equals(name)) {
                return "1";
            } else if (context.getString(R.string.male).equals(name)) {
                return "2";
            } else {
                throw new IllegalArgumentException("not valid");
            }
        }

        public String getGender(final Context context) {
            return context.getString(nameResource);
        }
    }

    private String first_name;
    private String last_name;
    private String avatar;
    private String birthday;
    private String gender; //1 - женский пол, 2 - мужской
    private String socialid;
    private String auth_method; //1)вконтакте 2)twitter 3)Facebook 4)Google+

    public NetLogin(final VKApiUser user) {
        first_name = user.first_name;
        last_name = user.last_name;
        socialid = String.valueOf(user.id);
        auth_method = "1";

        final JSONObject object = user.fields;

        try {
            avatar = object.getString("photo_200");
            birthday = object.getString("bdate");
            gender = object.getString("sex");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        checkNotNull();

    }

    public NetLogin(final Person person) {
        first_name = person.getName().getGivenName();
        last_name = person.getName().getFamilyName();
        avatar = person.getImage().getUrl();
        socialid = person.getId();
        auth_method = "4";
        birthday = person.getBirthday();
        gender = String.valueOf(person.getGender() == 0 ? 2 : 1);
        checkNotNull();
    }

    public NetLogin(final GraphResponse response) {
        final JSONObject object = response.getJSONObject();

        auth_method = "3";
        try {
            first_name = object.getString("first_name");
            last_name = object.getString("last_name");
            birthday = Utils.getDateString(object.getString("birthday"), "MM/dd/yyyy", "dd.MM.yyyy");
            gender = object.getString("gender").equals("male") ? "2" : "1";
            socialid = object.getString("id");

            final JSONObject picture = object.getJSONObject("picture");
            final JSONObject data = picture.getJSONObject("data");
            avatar = data.getString("url");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        checkNotNull();
    }

    public NetLogin(final User user) {
        auth_method = "2";
        final String name = user.name;
        if (null != name) {
            final String[] array = name.split(" ");
            first_name = array[0];
            if (array.length > 1) {
                last_name = array[2];
            }
        }

        avatar = user.profileImageUrl;
        socialid = String.valueOf(user.getId());
        gender = "";
        birthday = "";

        checkNotNull();
    }

    private void checkNotNull() {
        if (TextUtils.isEmpty(first_name)) {
            first_name = "";
        }
        if (TextUtils.isEmpty(last_name)) {
            last_name = "";
        }
        if (TextUtils.isEmpty(avatar)) {
            avatar = "";
        }
        if (TextUtils.isEmpty(socialid)) {
            socialid = "";
        }
        if (TextUtils.isEmpty(auth_method)) {
            auth_method = "";
        }
        if (TextUtils.isEmpty(birthday)) {
            birthday = "";
        }
        if (TextUtils.isEmpty(gender)) {
            gender = "";
        }
    }

}
