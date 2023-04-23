package com.example.application.views.data;

// import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;

/**
 * Main Bean class that we build the form for.
 * <p>
 * Uses Bean Validation (JSR-303) annotations for automatic validation.
 */
public class UserDetails {

    private Long id;

    @NotNull
    @Length(min = 1, max = 32)
    private String habitName;
    @NotNull
    @Length(min = 1, max = 32)
    private String habitDescription;

    @NotNull
    @Length(min = 4, max = 64)
    private String handle;

    private AvatarImage avatar;

   
    private String habitDate;

    // FIXME Passwords should never be stored in plain text!
    @NotNull
    @Length(min = 8, max = 64)
    private String password;

    private boolean allowsMarketing;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String gethabitName() {
        return habitName;
    }

    public void sethabitName(String habitName) {
        this.habitName = habitName;
    }

    public String gethabitDescription() {
        return habitDescription;
    }

    public void sethabitDescription(String habitDescription) {
        this.habitDescription = habitDescription;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public AvatarImage getAvatar() {
        return avatar;
    }

    public void setAvatar(AvatarImage avatar) {
        this.avatar = avatar;
    }

    public String gethabitDate() {
        return habitDate;
    }

    public void sethabitDate(String habitDate) {
        this.habitDate = habitDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAllowsMarketing() {
        return allowsMarketing;
    }

    public void setAllowsMarketing(boolean allowsMarketing) {
        this.allowsMarketing = allowsMarketing;
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return super.hashCode();
        }
        return id.intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (id == null) {
            return false;
        }
        if (!(obj instanceof UserDetails)) {
            return false;
        }
        UserDetails other = (UserDetails) obj;
        return id.equals(other.id);
    }
}