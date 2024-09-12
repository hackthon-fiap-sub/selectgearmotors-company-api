package br.com.selectgearmotors.company.commons.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.mapstruct.Named;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CompanyIdFormatter {

    @Named("formatCompanyId")
    public static String formatCompanyId(String companyId) {
        // Implementação para formatar o companyId, por exemplo:
        return companyId != null ? companyId.replaceAll("[^\\d]", "") : null;
    }

    @Named("revertCompanyId")
    public static String revertCompanyId(String formattedCompanyId) {
        // Implementação para reverter o companyId ao formato original
        // Exemplo: adicionar pontos, barras, etc.
        return formattedCompanyId != null && formattedCompanyId.length() == 14 ?
                formattedCompanyId.substring(0, 2) + "." +
                        formattedCompanyId.substring(2, 5) + "." +
                        formattedCompanyId.substring(5, 8) + "/" +
                        formattedCompanyId.substring(8, 12) + "-" +
                        formattedCompanyId.substring(12, 14) : null;
    }
}
