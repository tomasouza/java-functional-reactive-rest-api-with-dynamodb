package br.com.oigarcom.api.users.data.utils;

public final class UserTableConstants {
    public static final String USER_TABLE_NAME = "Users";
    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";

    @Override
    public String toString() {
        return "[USER_TABLE_NAME = " + USER_TABLE_NAME
                + ", NAME = " + NAME
                + ", PASSWORD = " + PASSWORD
                + ", EMAIL = " + EMAIL + "]";
    }
}
