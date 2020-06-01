package br.com.oigarcom.api.users.core.presentables;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PresentablePage {
    @JsonProperty("page_size")
    private int pageSize;
    @JsonProperty("last_valuated_key")
    private String lastValuatedKey;
    @Override
    public String toString(){
        return "[PageSize = "+ this.pageSize
                +", LastValuatedKey = "+ this.lastValuatedKey+"]";
    }
}
