package kpn;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kpn.items.ModernItem;
import kpn.items.LegacyItem;
import kpn.util.DateDeserializer;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.json.XML;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

public class Application {


	private static final int PRETTY_PRINT_INDENT_FACTOR = 4;
	private static final String GET_LEGACY_ITEM_JSONPATH = "$..item";
	private static final String GET_NUM_OF_ITEMS = "$..item.length()";

	public static void main(String[] args) {

		String inputXML = null;
		String rawJson = null;
		List<String> legacyItemJsonList = null;
		List<LegacyItem> legacyItemList = null;
		List<LegacyItem> legacyBussinessOutageList = null;
		List<LegacyItem> legacyClientOutageList = null;
		List<ModernItem> modernBussinessOutageList = null;
		List<ModernItem> modernClientOutageList = null;

		try{
			inputXML = readInputFile("src/main/resources/outages.xml");
			rawJson = convertToJson(inputXML);
			legacyItemJsonList = getLegacyItem(rawJson);
			legacyItemList = parseLegacyItems(legacyItemJsonList);
			legacyBussinessOutageList = getLegacyBusinessOutage(legacyItemList);
			legacyClientOutageList = getLegacyClientOutage(legacyItemList);
			
			//backdoor to generate the required Json files
			if (args.length != 0){
				String inputTime = args[0];
				
		        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		        Date setTime = parser.parse(inputTime);
		        
		        modernBussinessOutageList = ModernItem.parseToItemList(setTime, legacyBussinessOutageList);
		        modernClientOutageList = ModernItem.parseToItemList(setTime, legacyClientOutageList);
		        
			}else{
		        modernBussinessOutageList = ModernItem.parseToItemList(new Date(), legacyBussinessOutageList);
		        modernClientOutageList = ModernItem.parseToItemList(new Date(), legacyClientOutageList);
			}
			
			String resultBussinessOutageJson = modernBussinessOutageList.toString();
			String resultClientOutageJson = modernClientOutageList.toString();
			
			File businessFile = new File("src/main/resources/runtime/business_outages.json");
			FileUtils.writeStringToFile(businessFile, resultBussinessOutageJson);
			
			File clientFile = new File("src/main/resources/runtime/customer_outages.json");
			FileUtils.writeStringToFile(clientFile, resultClientOutageJson);
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

	}


	private static String readInputFile(String relativePath) throws Exception{
		File file = new File(relativePath);
		String str = FileUtils.readFileToString(file);

		return str;
	}

	private static String convertToJson(String xmlInput){

		JSONObject xmlJSONObj = XML.toJSONObject(xmlInput);
		String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
		//System.out.println(jsonPrettyPrintString);

		return jsonPrettyPrintString;
	}

	private static List<String> getLegacyItem(String jsonInput){
		
		List<String> resultJsonList = new ArrayList<String>();
		
		List<Integer> numberofItems = JsonPath.read(jsonInput, GET_NUM_OF_ITEMS);
		int num = numberofItems.get(0);
		
		for (int i=0; i < num; i++){
			List<String> itemJson = JsonPath.read(jsonInput, "$..item[" + i +"]");
			String tmpJson = itemJson.toString();
			resultJsonList.add(tmpJson.substring(1,tmpJson.length()-1));
		}

		return resultJsonList;
	}
	
	private static List<LegacyItem> parseLegacyItems(List<String> legacyItemJsonList){
		
		List<LegacyItem> result = new ArrayList<LegacyItem>();
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		//gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		Gson gson = gsonBuilder.setPrettyPrinting().create();

		for (String item : legacyItemJsonList){
		result.add(gson.fromJson(item, LegacyItem.class));
		}
		
		return result;
	}
	
	private static List<LegacyItem> getLegacyBusinessOutage(List<LegacyItem> input){
		
		List<LegacyItem> result = new ArrayList<LegacyItem>();
		
		for (LegacyItem item: input){
			if (item.getJamesLocations() != null && (item.getJamesLocations().contains("ZMST") || item.getJamesLocations().contains("ZMOH"))){
				result.add(item);
			}
		}
		
		return result;
		
	}
	
	private static List<LegacyItem> getLegacyClientOutage(List<LegacyItem> input){
		
		List<LegacyItem> result = new ArrayList<LegacyItem>();
		
		for (LegacyItem item: input){
			if (item.getJamesLocations() == null || (!item.getJamesLocations().contains("ZMST") && !item.getJamesLocations().contains("ZMOH"))){
				result.add(item);
			}
		}
		
		return result;
		
	}
	



}
