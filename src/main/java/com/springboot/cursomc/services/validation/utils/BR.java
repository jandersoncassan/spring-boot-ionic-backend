package com.springboot.cursomc.services.validation.utils;

public class BR {

	
    private static final int[] PESO_CPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    
    private static final int[] PESO_CNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

	/**
	 * 
	 * @param cpf
	 * @return
	 */
     public static boolean isValidCpf(final String cpf) {
        Integer digit1 = calculateDigit(cpf.substring(0, 9), PESO_CPF);
        Integer digit2 = calculateDigit(cpf.substring(0, 9) + digit1, PESO_CPF);
        return cpf.equals(cpf.substring(0, 9) + digit1.toString() + digit2.toString());
    }

    /**
     * Método responsável por validar o dígito do CNPJ informado.
     * 
     * @param cnpj
     *            : CNPJ que será validado
     * @return TRUE ou FALSE
     */
    public static boolean isValidCnpj(final String cnpj) {
        Integer digit1 = calculateDigit(cnpj.substring(0, 12), PESO_CNPJ);
        Integer digit2 = calculateDigit(cnpj.substring(0, 12) + digit1, PESO_CNPJ);
        return cnpj.equals(cnpj.substring(0, 12) + digit1.toString() + digit2.toString());
    }
    
    /**
     * Método responsável por calcular o dígito do cpf ou cnpj de acordo com a regra do módulo 11.
     * 
     * @param cpfCnpj
     *            : CPF ou CNPJ
     * @param peso
     *            : vetor de peso do cpf ou cnpj.
     * @return digito calculado
     */
    private static int calculateDigit(final String cpfCnpj, final int[] peso) {

        int soma = 0;
        for (int indice = cpfCnpj.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(cpfCnpj.substring(indice, indice + 1));
            soma += digito * peso[peso.length - cpfCnpj.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

}
