package br.com.oigarcom.api.users.core.entities;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Page {
    private int pageSize;
    private String lastValuatedKey;
    public String toString(){
        return "[PageSize = "+ this.pageSize
                +", LastValuatedKey = "+ this.lastValuatedKey+"]";
    }
}
