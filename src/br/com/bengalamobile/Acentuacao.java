package br.com.bengalamobile;

public class Acentuacao {

	public static String retirarAcentos(String textoAcentuado, boolean replaceSpecial) {

		String novoTexto = textoAcentuado;

		novoTexto = novoTexto.replaceAll("[áàãäâ]", "a");
		novoTexto = novoTexto.replaceAll("[éèêë]", "e");
		novoTexto = novoTexto.replaceAll("[íìîï]", "i");
		novoTexto = novoTexto.replaceAll("[óòõôö]", "o");
		novoTexto = novoTexto.replaceAll("[úùûü]", "u");
		novoTexto = novoTexto.replaceAll("[ç]", "ss");

		novoTexto = novoTexto.replaceAll("[ÁÀÂÃÄ]", "A");
		novoTexto = novoTexto.replaceAll("[ÈÉÊË]", "E");
		novoTexto = novoTexto.replaceAll("[ÍÌÎÏ]", "I");
		novoTexto = novoTexto.replaceAll("[ÓÒÔÕÖ]", "O");
		novoTexto = novoTexto.replaceAll("[ÚÙÛÜ]", "U");
		novoTexto = novoTexto.replaceAll("[Ç]", "SS");

		novoTexto = novoTexto.replaceAll("[ñ]", "n");

		if (replaceSpecial) {
			// apaga caracteres n�o-alfanum�ricos
			novoTexto = novoTexto.replaceAll("[^a-zA-Z0-9-\\s]{1,}", "");

			// apaga eventuais tra�os (aspas, pontos de interrogacao etc.) no
			// in�cio
			// e fim do link
			novoTexto = novoTexto.replaceAll("^-|-$", "");
		}

		return novoTexto;
	}
	
}
