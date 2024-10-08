package br.com.selectgearmotors.company.commons.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TokenExtractor {

    public static void main(String[] args) {
        // URL completa com os tokens
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite a URL com os tokens: " + "\n");

        String url = scanner.nextLine();
        // Extrair os parâmetros da URL
        Map<String, String> tokens = extractTokensFromUrl(url);

        if (tokens.containsKey("id_token")) {
            System.out.println("ID Token: " + "\n "+ tokens.get("id_token"));
            decodeAndPrintToken(tokens.get("id_token"));
        }

        if (tokens.containsKey("access_token")) {
            System.out.println("Access Token: " + "\n "+tokens.get("access_token"));
            decodeAndPrintToken(tokens.get("access_token"));
        }
    }

    // Função para extrair o id_token e access_token da URL
    public static Map<String, String> extractTokensFromUrl(String url) {
        Map<String, String> tokens = new HashMap<>();

        // Extrair a parte da URL após o "?"
        String[] params = url.split("\\#");
        if (params.length > 1) {
            // Quebrar os parâmetros separados por "&"
            String[] queryParams = params[1].split("&");

            // Loop pelos parâmetros para extrair id_token e access_token
            for (String param : queryParams) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    tokens.put(keyValue[0], keyValue[1]);
                }
            }
        }

        return tokens;
    }

    // Função para decodificar e exibir informações do token JWT
    public static void decodeAndPrintToken(String token) {
        try {
            DecodedJWT decodedJwt = JWT.decode(token);
            System.out.println("Token decodificado: " + decodedJwt.getPayload());
        } catch (JWTDecodeException e) {
            System.out.println("Erro ao decodificar o token: " + e.getMessage());
        }
    }
}
