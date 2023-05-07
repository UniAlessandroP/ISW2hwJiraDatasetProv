package app.com.isw2dsprov.entity;

import java.util.Date;
import java.util.Optional;

public class Version {
	private int id;
	private String name;
	private Optional<Boolean> isarchived;
	private Optional<Boolean> isreleased;
	private Date releaseDate;
	
	public Version(int id, String name, Date releasedate, Optional<Boolean> archived, Optional<Boolean> released) {
		this.id = id;
		this.name = name;
		this.releaseDate = releasedate;
		this.isarchived = archived; 
		this.isreleased = released;
	}

	public void printInfo() {
		StringBuilder composeinfo = new StringBuilder();
		composeinfo.append("id: " + this.id);
		composeinfo.append("\nname: " + this.name);
		composeinfo.append("\nisarchived: " + this.getIsArchived());
		composeinfo.append("\nisreleased: " + this.getIsReleased());
		composeinfo.append("\ndate: " + this.releaseDate + "\n\n");
		
		System.out.println(composeinfo);
		
	}

	private boolean getIsArchived() {
		return this.isarchived.get();
	}
	private boolean getIsReleased() {
		return this.isreleased.get();
	}
	
}
