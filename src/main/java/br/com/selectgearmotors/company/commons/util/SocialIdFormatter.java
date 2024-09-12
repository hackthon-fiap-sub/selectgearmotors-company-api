package br.com.selectgearmotors.company.commons.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.mapstruct.Named;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SocialIdFormatter {

    @Named("formatSocialId")
    public static String formatCompanyId(String socialId) {
        // Implementação para formatar o companyId, por exemplo:
        return socialId != null ? socialId.replaceAll("[^\\d]", "") : null;
    }

    @Named("revertSocialId")
    public static String revertCompanyId(String formattedSocialId) {
        // Adiciona pontos e hífen para o formato XXX.XXX.XXX-XX
        return formattedSocialId != null && formattedSocialId.length() == 11 ?
                formattedSocialId.substring(0, 3) + "." +
                        formattedSocialId.substring(3, 6) + "." +
                        formattedSocialId.substring(6, 9) + "-" +
                        formattedSocialId.substring(9, 11) : null;
    }
}
