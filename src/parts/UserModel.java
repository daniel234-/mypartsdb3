package parts;

public class UserModel {

	private String[] name;
	private String[] email;
	private String[] password;
	
	public UserModel(){
		name = new String[3];
		email = new String[3];
		password = new String[3];
		setUsers();
		setEmails();
		setPassWords();
	}
	
	private void setPassWords(){
		password[0] = "tomjones1";
		password[1] = "smith2";
		password[2] = "rnelson3";
	}
	
	private void setUsers(){
		name[0] = "Tom Jones";
		name[1] = "Sue Smith";
		name[2] = "Ragnar Nelson";
	}
	
	private void setEmails(){
		email[0] = "TomJones@gmail.com";
		email[1] = "SueSmith@gmail.com";
		email[2] = "RagnarNelson@gmail.com";
	}
	
	public String getPassword(int index){
		return this.password[index];
	}
	
	public String getUser(int index){
		return this.name[index];
	}
	
	public String getEmail(int index){
		return this.email[index];
	}
}
