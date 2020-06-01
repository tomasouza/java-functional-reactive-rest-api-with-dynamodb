package br.com.oigarcom.api.users.data.documents;

import br.com.oigarcom.api.users.data.builders.UserDocumentBuilder;

public class UserDocument {

    private String name;
    private String password;
    private final String email;

    public static UserDocumentBuilder builder() {
        return new UserDocumentBuilder();
    }

    public UserDocument(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() { return password; }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User [Name=" + name
                + ", Email=" + email + "]";
    }

}
