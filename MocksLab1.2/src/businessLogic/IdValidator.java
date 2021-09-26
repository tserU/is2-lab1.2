package businessLogic;

/**
 * Clase que permite validar un DNI. Se crea un objeto del tipo ValidadorDNI y
 * se le pasa un String a validar.
 * 
 * @return true si DNI es correcto. Desarrollado por Manuel Mato.
 */

public class IdValidator {

	public static boolean validate(String id) {

		if (id.length() != 9 || !Character.isLetter(id.charAt(8))) {
			return false;
		}

		return soloNumeros(id) && letraDNICorrecta(id);

	}

	private static boolean soloNumeros(String dni) {
		for (int i = 0; i < dni.length() - 1; i++) {
			if (!Character.isDigit(dni.charAt(i)))
				return false;
		}
		return true;
	}

	private static boolean letraDNICorrecta(String dni) {
		Character letraDNI = (dni.substring(8)).toUpperCase().charAt(0);
		int numDNI = Integer.parseInt(dni.substring(0, dni.length() - 1));
		String asignacionLetra = "TRWAGMYFPDXBNJZSQVHLCKE";

		Character letraOK = asignacionLetra.charAt(numDNI % 23);

		return letraOK == letraDNI;
	}

}
