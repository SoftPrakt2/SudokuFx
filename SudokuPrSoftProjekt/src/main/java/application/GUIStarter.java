package application;

/**
 * This class is used to start the application
 * 
 * @author grube
 *
 */
public class GUIStarter {
	
	/**
	 * 
	 * 
	 * 
	 * This method calls the {@link application.GUI#main(String[])} Method, this
	 * call is needed because a direct start using the {@link application.GUI} class
	 * leads to a JavaX Runtime Components are missing Error after researching the
	 * web the solution of calling the {@link application.GUI#main(String[])} method
	 * through another class was found and fixed the problem
	 * 
	 * @param args
	 * 
	 */
	public static void main(final String[] args) {
		GUI.main(args);

	}

}
