package entities.files;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Transient;

import model.Persister;
import utilities.*;

@Entity
public class AnsarUser extends AnsarFile
{
    private String username;
    private String password;
    @Embedded
    private UserDetails userDetails;

    public String getUserName()
    {
        return username;
    }

    public void setUserName(String userName)
    {
        this.username = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public UserDetails getUserDetails()
    {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails)
    {
        this.userDetails = userDetails;
    }

    @Transient
    public String getPersonType()
    {
        return Translator.translate(userDetails.getPersonType());
    }

    @Transient
    public String getPersonCode()
    {
        return userDetails.getPersonCode().toString();
    }

    @Override
    @Transient
    public Result isValidForCommit(Result result)
    {
        if (userDetails == null || ObjectChecker.isEmptyOrZeroOrNull(userDetails.getPersonType()))
            result.failure("you must select person type");
        if (userDetails == null || ObjectChecker.isEmptyOrZeroOrNull(userDetails.getPersonCode()))
            result.failure("you must select person code");
        if (ObjectChecker.isEmptyOrZeroOrNull(username))
            result.failure("you must enter username");
        if (ObjectChecker.isEmptyOrZeroOrNull(password))
            result.failure("you must enter password");
        if (ObjectChecker.isNotEmptyOrZeroOrNull(username) && username.length() < 3)
            result.failure("Username must be greater than or equal to 3 character");
        if (ObjectChecker.isNotEmptyOrZeroOrNull(password) && password.length() < 6)
            result.failure("Password must be greater than or equal to 6 character");
        if (result.isFailed())
            return result;
        int doesEntityExist = Persister.countOf(userDetails.getPersonType(), "WHERE code = :code",
                Persister.params("code", userDetails.getPersonCode()));
        if (ObjectChecker.isEmptyOrZeroOrNull(doesEntityExist))
            result.failure("Non-existent code for type: ", userDetails.getPersonType());
        return result;
    }

}
