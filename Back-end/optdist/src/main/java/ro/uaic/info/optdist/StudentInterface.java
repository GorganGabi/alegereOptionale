package ro.uaic.info.optdist;

import org.xwiki.component.annotation.Role;

import ro.uaic.info.optdist.internal.Preference;

@Role
public interface StudentInterface {
    public String getNrMatricol();
    public String getName();
    public String getSurname();
    public String getGroup();
    public float getGrade();
    public void setPreference (Preference newPreference);
    public int getYear();
}

