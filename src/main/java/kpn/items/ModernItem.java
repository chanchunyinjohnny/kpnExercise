package kpn.items;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

public class ModernItem {

	private String endDate;
	private String title;
	private String postalCodes;
	private String status;
	private String startDate;
	private String description;

	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPostalCodes() {
		return postalCodes;
	}
	public void setPostalCodes(String postalCodes) {
		this.postalCodes = postalCodes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public static List<ModernItem> parseToItemList (Date setTime, List<LegacyItem> input){

		List<ModernItem> result = new ArrayList<ModernItem>();

		for (LegacyItem item: input){
			
			ModernItem tmp = new ModernItem();

			tmp.setStartDate(convertStartDate(item));
			tmp.setEndDate(convertEndDate(item));
			tmp.setTitle(item.getTitle());
			tmp.setPostalCodes(item.getJamesPostalCodes());
			tmp.setDescription(item.getDescription());

			tmp.setStatus(convertStatus(setTime , tmp));
			
			result.add(tmp);			
		}

		return result;

	}

	private static String convertEndDate(LegacyItem item){
		String result = null;
		String tmpDesc = item.getDescription();
		String Eindtijd = tmpDesc.substring(tmpDesc.indexOf("Eindtijd: ")+10, tmpDesc.indexOf("&nbsp;", tmpDesc.indexOf("Eindtijd: ")+10));

		if(Eindtijd != null && Eindtijd.equals("onbekend")){
			result = "onbekend";
		}else{
			result = Eindtijd;
		}

		return result;
	}

	private static String convertStartDate(LegacyItem item){
		String result = null;
		String tmpDesc = item.getDescription();
		String Starttijd = tmpDesc.substring(tmpDesc.indexOf("Starttijd: ")+11, tmpDesc.indexOf("&nbsp;", tmpDesc.indexOf("Starttijd: ")+11));

		if(Starttijd != null && Starttijd.equals("onbekend")){
			result = "onbekend";
		}else{
			result = Starttijd;
		}

		return result;
	}

	private static String convertStatus(Date setTime, ModernItem item){

		String result = null;

		if (item.getStartDate().equals("onbekend")||item.getEndDate().equals("onbekend")){
			result = "Actueel";
		}
		else{
			SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				Date startDate = parser.parse(item.getStartDate());
				Date endDate = parser.parse(item.getEndDate());

				if (endDate.compareTo(setTime) > 0){
					result = "Actueel";
				}else if(startDate.compareTo(setTime) > 0){
					result = "Gepland";
				}else if(endDate.compareTo(setTime) < 0){
					result = "Opgelost";
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	@Override
	public String toString(){
		Gson gson = new Gson();
		String jsonInString = gson.toJson(this);
		return jsonInString;
	}

}
