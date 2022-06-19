package me.remil.dto;

public class CheckEmailExists {
	private boolean userExist;

	public CheckEmailExists(boolean userExist) {
		this.userExist = userExist;
	}

	public boolean isUserExist() {
		return userExist;
	}

	public void setUserExist(boolean userExist) {
		this.userExist = userExist;
	}
	
		
}
