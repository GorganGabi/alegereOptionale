package ro.uaic.info.optdist.internal;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

import org.xwiki.component.annotation.Component;

@Component
public class PackageAdministration {
    private List<Package> packageList;
    
    public PackageAdministration(){
        packageList = new ArrayList<>();
    }
    
    /**
    * Imports the optional packages from the given URL into
    * the packageList field. 
    * <p>
    * The argument must direct to a page containing a table
    * formatted containing courses, formatted in a specific
    * way, such as this one:
    * https://www.info.uaic.ro/bin/Programs/Undergraduate
    * <p>
    * The function parses the course table and looks for the cell
    * containing the package id, followed by a cell containing the
    * "Optional Course" keywords. Then it loops through the row 
    * and finds the cells containing the optional course names.
    * The function gathers the course year from the second character
    * of the package id and the semester from the third one.
    * The optional id is obtained by concatenation between the package
    * id and a 2 digit, zero padded number given by the order of listing
    * of an optional course in a package table row.
    * @param  url - an absolute URL giving the location of the courses page
    * @see PackageAdministration
    * @see Package
    * @see Optional
    * @throws java.net.MalformedURLException
    * @throws java.net.ProtocolException
    */
    public void importPackages(String url) throws Exception{
        URL request = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) request.openConnection();
        
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        
        int responseCode = connection.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        
        while((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            //System.out.println(inputLine);
        }
        
        ArrayList<Package> packs = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("<tr><td>[A-Z0-9]{6}</td><td>Optional Course [0-9]+<br/>.*?</tr>");
        Matcher matcher = pattern.matcher(response.toString());
        int matches = 0;

        while (matcher.find()) {
            matches++;            
            String optionalLine = matcher.group();
            //System.out.println(optionalLine);
                        
            Pattern idPattern = Pattern.compile("[A-Z0-9]{6}");
            Matcher idMatcher = idPattern.matcher(optionalLine);
            
            idMatcher.find();
            String id = idMatcher.group();
            
            Pattern optsPattern = Pattern.compile("<br/>.*");
            Matcher optsMatcher = optsPattern.matcher(optionalLine);
            
            optsMatcher.find();
            String opts = optsMatcher.group();
            
            Pattern optsCleanerPattern = Pattern.compile("</?span.*?>");
            opts = optsCleanerPattern.matcher(opts).replaceAll("");
            
            optsCleanerPattern = Pattern.compile("</?a.*?>");
            opts = optsCleanerPattern.matcher(opts).replaceAll("");
            
            optsCleanerPattern = Pattern.compile("</td><td>[0-9]</td><td>.*");
            opts = optsCleanerPattern.matcher(opts).replaceAll("");
            
            optsCleanerPattern = Pattern.compile("^<br/>");
            opts = optsCleanerPattern.matcher(opts).replaceAll("");
            
            //System.out.println("Package ID: " + id);
            
            int year = Integer.parseInt(id.substring(2, 3));
            int semester = Integer.parseInt(id.substring(3, 4));
            
            //System.out.println("Year: " + year);
            //System.out.println("Semester: " + semester);
            
            String[] optList = opts.split("<br/>");
            
            ArrayList<Optional> optionalList = new ArrayList<>();
            
            for (String opt : optList) {
                Pattern optIdPattern = Pattern.compile("O[0-9]:");
                Matcher optIdMatcher = optIdPattern.matcher(opt);
                
                optIdMatcher.find();
                String sufix = optIdMatcher.group();
                sufix = sufix.substring(1, sufix.length() - 1);
                
                String optId = String.format("%s%02d", id, Integer.parseInt(sufix));
                
                //System.out.println(optId);
                
                String optName = optIdMatcher.replaceAll("");
                
                //System.out.println(optName);
                
                optionalList.add(new Optional(optId, optName, year, semester));
            }
            
            Package pack = new Package(optionalList, year, semester, id);
            packs.add(pack);
        }

        this.packageList = packs;
    }
    
    /**
    * Adds a package into the packageList.
    * <p>
    * @param newPackage the package that will be added
    */
    public void addPackage (Package newPackage){
        packageList.add(newPackage);
    }

    /**
     * Gets a list of all the packages.
     * <p>
     * @return A list of 
     */
    public List<Package> getPackageList(){
        return packageList;
    }
}
