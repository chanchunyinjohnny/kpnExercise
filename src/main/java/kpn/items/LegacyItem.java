package kpn.items;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class LegacyItem {
	
	private String title;
	private String category;
	@SerializedName("james:ticketNumber")
	private String jamesTicketNumber;
	@SerializedName("james:postalCodes")
	private String jamesPostalCodes;
	@SerializedName("james:expectedEndDate")
	private String jamesExpectedEndDate;
	@SerializedName("james:category")
	private String jamesCategory;
	@SerializedName("james:locations")
	private String jamesLocations;
	private String description;
	private String link;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getJamesTicketNumber() {
		return jamesTicketNumber;
	}
	public void setJamesTicketNumber(String jamesTicketNumber) {
		this.jamesTicketNumber = jamesTicketNumber;
	}
	public String getJamesPostalCodes() {
		return jamesPostalCodes;
	}
	public void setJamesPostalCodes(String jamesPostalCodes) {
		this.jamesPostalCodes = jamesPostalCodes;
	}
	public String getJamesExpectedEndDate() {
		return jamesExpectedEndDate;
	}
	public void setJamesExpectedEndDate(String jamesExpectedEndDate) {
		this.jamesExpectedEndDate = jamesExpectedEndDate;
	}
	public String getJamesCategory() {
		return jamesCategory;
	}
	public void setJamesCategory(String jamesCategory) {
		this.jamesCategory = jamesCategory;
	}
	public String getJamesLocations() {
		return jamesLocations;
	}
	public void setJamesLocations(String jamesLocations) {
		this.jamesLocations = jamesLocations;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
}
