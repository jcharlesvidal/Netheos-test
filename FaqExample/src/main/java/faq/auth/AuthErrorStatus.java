package faq.auth;

/**
 * @author jcvidal
 *
 * Errors of Auth
 *
 */
public enum AuthErrorStatus {
	ERR_HEADER(1001,"Probleme dans le header : impossible de récupérer les credentials"),
	RET_USER(1002,"L'utilisateur n'existe pas"),
	RET_PWD(1003,"Le mot de passe n'est pas correct"),
	RET_ROLE(1004,"L'utilisateur n'a pas les bons droits");
	
	private int status;
	private String text;
	
	AuthErrorStatus(int status,String text) {
		this.status = status;
		this.text = text;
	}
	
	public int getStatus() {
		return status;
	}

	public String getText() {
		return text;
	}
}
