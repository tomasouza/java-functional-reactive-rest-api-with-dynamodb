package br.com.oigarcom.api.users.core.mappers;

import br.com.oigarcom.api.users.core.entities.Page;
import br.com.oigarcom.api.users.core.presentables.PresentablePage;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class PageMapper {
    public Page mapToPage(PresentablePage presentablePage) {
        return Page.builder()
                .pageSize(presentablePage.getPageSize())
                .lastValuatedKey(presentablePage.getLastValuatedKey())
                .build();
    }
}
