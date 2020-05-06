package routines;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Base64;

import javax.imageio.ImageIO;

import routines.system.JSONArray;
import routines.system.JSONObject;
/*
 * user specification: the function's comment should contain keys as follows: 1. write about the function's comment.but
 * it must be before the "{talendTypes}" key.
 * 
 * 2. {talendTypes} 's value must be talend Type, it is required . its value should be one of: String, char | Character,
 * long | Long, int | Integer, boolean | Boolean, byte | Byte, Date, double | Double, float | Float, Object, short |
 * Short
 * 
 * 3. {Category} define a category for the Function. it is required. its value is user-defined .
 * 
 * 4. {param} 's format is: {param} <type>[(<default value or closed list values>)] <name>[ : <comment>]
 * 
 * <type> 's value should be one of: string, int, list, double, object, boolean, long, char, date. <name>'s value is the
 * Function's parameter name. the {param} is optional. so if you the Function without the parameters. the {param} don't
 * added. you can have many parameters for the Function.
 * 
 * 5. {example} gives a example for the Function. it is optional.
 */
public class TransformToLiferay {
    /**
     * setLocalizedField: return localizedFild value in JSON string.
     * 
     * {talendTypes} String
     *
     * {Category} User Defined
     * 
     * {param} string("locale") input: The string with the language is required.
     * {param} string("message") input: The string with the text field is required.
     * 
     * {example} setLocalizedField("pt_BR","text message") # hello
     */
    public static String setLocalizedField(String locale, String message) {
       
        JSONObject jsonObject= new JSONObject();
        
        jsonObject.put(locale, message);
        
        return jsonObject.toString();
        
    }
    /**
     * getLocalizedField: return localizedFild value in JSON.
     * 
     * {talendTypes} String
     *
     * {Category} User Defined
     * 
     * {param} string("locale") input: The string with the language is required.
     * {param} string("message") input: The string with the text field is required.
     * 
     * {example} setLocalizedField("pt_BR","text message") # hello
     */
    public static JSONObject getLocalizedField(String locale, String message){
    	  
    	  JSONObject jsonObject= new JSONObject();  
          jsonObject.put(locale, message);     
          return jsonObject;
	
    }

    
    /**
     * setSku: return a array of Sku values in JSON.
     * 
     * {Category} User Defined
     * 
     * {param} string("externalReferenceCode") input: The string with the externalRefenrece is required.
     * {param} string("sku") input: The string with the text field is required.
     * {param} string("gtin") input: The string with the text field is required.
     * {param} boolean("purchasable") input: The boolean with the text field is required.
     * {param} bigdecimal("price") input: The bigdecimal with the text field is required.
     * {param} bigdecimal("promoPrice") input: The bigdecimal with the text field is required.
     * {param} int("manufacturerPartNumber") input: The int with the text field is required.
     * {example} setSku("01023948576extRef","123sku", "1234eanCod", true, 10.00, 8.00, ) # return a JSON format to Liferay API !.
     */
    public static String setSku(String externalReferenceCode, String sku, String gtin, Boolean purchasable, BigDecimal price, BigDecimal promoPrice, int manufacturerPartNumber) {
        JSONObject jsonObject= new JSONObject();
        
        jsonObject.put("externalReferenceCode", externalReferenceCode);
        jsonObject.put("sku", sku);
        jsonObject.put("price", price);
        jsonObject.put("gtin", gtin);
        jsonObject.put("promoPrice", promoPrice);
        jsonObject.put("purchasable", purchasable);
        jsonObject.put("manufacturerPartNumber", String.valueOf(manufacturerPartNumber));
        
        JSONArray jsonArray = new JSONArray();
        
        jsonArray.put(jsonObject);
        
        return jsonArray.toString();
    }
     
    /**
     * setImage: return a array of Images Param values in JSON.
     * 
     * {Category} User Defined
     * {param} string("externalReferenceCode") input: The string with the externalRefenrece is required.
     * {param} string("gtin") input: The string with the text field is required.
     * {param} string("title") input: The image name
     * {param} string("pathname") input: pathname or url where the image besides
     * {param} boolean("remote") input: where the image is, remote or local
     * {example} setImage("01023948576extRef","name", "1234eanCod") # return a JSON format to Liferay API !.
     */
    public static String setImage(String externalReferenceCode,JSONObject title,String gtin,String pathname,String mymeType ,boolean remote) {
        JSONObject jsonObject= new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String imageB64="iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+P+/HgAFhAJ/wlseKgAAAABJRU5ErkJggg==";
        BufferedImage image = null;
        try{
	        if (!remote){
	        	StringBuffer sb=new StringBuffer(pathname).append("/").append(gtin).append(".").append(mymeType);  
				image = ImageIO.read(new File(sb.toString()));
	        }else{
	        	URL url = new URL(pathname);
	        	image = ImageIO.read(url);	
	        }
        	ByteArrayOutputStream os = new ByteArrayOutputStream();
        	ImageIO.write(image, mymeType, os);
        	imageB64 = Base64.getEncoder().encodeToString(os.toByteArray());
        }catch(IOException e){
        	new IOException(e.getMessage(), e.getCause());
        }
        
        jsonObject.put("attachment", imageB64);
        jsonObject.put("externalReferenceCode", externalReferenceCode);
        jsonObject.put("title", title);
        jsonObject.put("neverExpire", true);
        jsonArray.put(jsonObject);
      
        return jsonArray.toString();
    }
    
    /**
     * setInventory: return a array of Inventory values in JSON.
     * 
     * {Category} User Defined
     * 
     * {param} string("sku") input: The string with the sku is required.
     * {param} string("quantity") input: The int with the quantity is required.
     * {param} long("warehouseId") input: The double with the warehouse id
    
     * {example} setInventory("weight","10gr") # return a array of JSON format to Liferay API !.
     */
    public static String setInventory(String sku, String quantity, long warehouseId) {
    	JSONObject jsonObject = new JSONObject();
    	JSONArray jsonArray = new JSONArray();
    	jsonObject.put("sku", sku);
    	jsonObject.put("warehouseId", warehouseId);
    	int _quantity = quantity != null ? Integer.valueOf(quantity):0;
    	jsonObject.put("quantity", _quantity);
    	jsonArray.put(jsonObject);
        return jsonArray.toString();

    }
   
}