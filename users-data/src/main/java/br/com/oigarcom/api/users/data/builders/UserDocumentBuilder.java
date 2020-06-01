package br.com.oigarcom.api.users.data.builders;

import br.com.oigarcom.api.users.data.documents.UserDocument;

public class UserDocumentBuilder {

    private String name;
    private String password;
    private String email;

    public UserDocumentBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UserDocumentBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserDocumentBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserDocument build() {
        return new UserDocument(this.name, this.password, this.email);
    }

}
