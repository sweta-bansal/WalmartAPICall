
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.lang.NullPointerException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// class Product stores the product name, product id and product recommendation points

public class Product {
		private static String WalmartapiKey = "bh8gxdhr4mgnuvddrd7zfgcx";
		public String productName;
	    public float points;
	    public String productId;
	    public Product(String productId, String product,float points){
	        this.productId = productId;
	        this.productName = product;
	        this.points = points;
	    }
	    
	    // reviewsAPI method returns the product points based on average rating by making Reviews API call 
	    
	    public static float reviewsAPI(String productId) throws ParseException, IOException{
	    	float points = 0;
	    	JSONParser jsonparser = new JSONParser();
	        String getStr = "http://api.walmartlabs.com/v1/reviews/"+productId+"?apiKey="+WalmartapiKey+"&format=json";
	        JSONObject jsonobject = (JSONObject) jsonparser.parse(apiCall(getStr));
            JSONObject reviewStatistics = (JSONObject) jsonobject.get("reviewStatistics");
            if(reviewStatistics!=null)
            	points = Float.valueOf((String) reviewStatistics.get("averageOverallRating"));
            //else 
            	//throw new NullPointerException("no review for this product");
            return points;
	    }
	    
	    // searchAPI method returns the products matching the productSearchString passed by the user by making search API call 
	    
	    public static JSONArray searchAPI(String productSearchString) throws ParseException, IOException{
	    	JSONParser jsonparser = new JSONParser();
	    	//productSearchString="iPod";
	        String getStr = "http://api.walmartlabs.com/v1/search?apiKey="+WalmartapiKey+"&query="+productSearchString;
	        JSONArray products = null;
	        JSONObject jsonobject = (JSONObject) jsonparser.parse(apiCall(getStr));
	        products = (JSONArray) jsonobject.get("items");
	        if (products.size() == 0) {
	              throw new NullPointerException("product not found");
	        }
	        return products;
	    }
	   
	    //productRecommendationAPI returns the list of recommended products in sorted order ranked from 1 to 10.
	    
	    public static List<Product> productRecommendationAPI(String itemId) throws ParseException, IOException{
	    	JSONParser jsonparser = new JSONParser();
	    	List<Product> productRecommendationList = new ArrayList<>();
	        String url = "http://api.walmartlabs.com/v1/nbp?apiKey="+WalmartapiKey+"&itemId="+itemId;
	        JSONArray jsonitems = (JSONArray) jsonparser.parse(apiCall(url));
            if (jsonitems.size() == 0) {
                throw new NullPointerException("no recommended items");
            }
            int maxsize=0;
            if(jsonitems.size()>10)
            	maxsize=10;
            else 
            	maxsize=jsonitems.size();
            for(int i =0;i<maxsize;i++){
                JSONObject product = (JSONObject)jsonitems.get(i);
                String productname = String.valueOf( product.get("name"));
                String productid= String.valueOf( product.get("itemId"));
                float points = reviewsAPI(productid);
                productRecommendationList.add(new Product(productid,productname,points));
            }
            Collections.sort(productRecommendationList, new Comparator<Product>() {
            	public int compare(Product r1, Product r2) {
                    if (r1.points>r2.points)
                    	return -1;
                    else
                        return 1;

                }
            });
	       
	        return productRecommendationList;
	    }
	    
	    // method for making API calls which returns the data in JSON format
	    
	    public static String apiCall(String str) throws IOException {
	    	StringBuilder sb = new StringBuilder();
	        URL link = new URL(str);
	        HttpURLConnection connection = (HttpURLConnection) link.openConnection();
	        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        String line= br.readLine();
	        while (line!= null) {
	            sb.append(line);
	            line = br.readLine();
	        }
	        br.close();
	        connection.disconnect();
	        return sb.toString();
	    }
	    
	    public static void main(String[] args) throws ParseException, IOException {
	    	//if(args.length==0)
	    		//throw new NullPointerException("Enter a product in command line argument");
	    	String productSearchString="";
	    	//if (args[0]!=null)
	    		//productSearchString=args[0];
	        for(int i=0;i<args.length;i++){
	                productSearchString=productSearchString+args[i];
	        }
	       JSONArray productList =  searchAPI(productSearchString);
	        if(productList.size()>0) {
	            JSONObject firstProduct = (JSONObject) productList.get(0);
	            String firstProductId = String.valueOf(firstProduct.get("itemId"));
	            List<Product> productRecommendationList = productRecommendationAPI(firstProductId);
	            for (int i=0;i<productRecommendationList.size();i++) {
	                Product p = productRecommendationList.get(i);
	                System.out.println("Rank:"+(i+1)+" "+p.productName);
	            }
	        }
	        else
	        	throw new NullPointerException("product does not exist");
	    }
}


