package org.idsiom.utilbet.currentuse.interlocutor;

public class PickioInterlocutorImpl implements IPyckioInterlocutor {
	public static final String PAGE_SIGNIN_PYCKIO = "https://pyckio.com/signin";
	
	private PickioInterlocutorImpl instance;
	
	private PickioInterlocutorImpl() {
		if (this.instance == null) {
			this.instance = new PickioInterlocutorImpl();
		}
	}
	
	
	public void iniciarSesion(String usuario, String clave) {
		// TODO Auto-generated method stub
		/*
id email
id pwd

btn-signin
		 */
	}

}
