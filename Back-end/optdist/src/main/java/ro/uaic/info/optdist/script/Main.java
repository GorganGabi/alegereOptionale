/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.optdist.script;

import java.util.ArrayList;
import java.util.List;
import ro.uaic.info.optdist.internal.Student;

/**
 *
 * @author C4theWin
 */
public class Main {
    public static void main (String[] args) throws Exception {
        OptDistService OptDist = new OptDistService();
        OptDist.init();
        OptDist.setStudentExcelPath("D:\\workspace\\ParseExcel\\STUDENTS2.xlsx");
        OptDist.importPackages();
        OptDist.importStudents();
        
        /*
        OptDist.createPackage(2, 2, "CS010101");
        OptDist.getPackages().getPackageByID("CS010101").getID();

        //## String packageID, String ID, String name, int year, int semester)
        OptDist.createOptional("CS010101", "CS010101O1", "programare orientata orizontal", 2, 2);
        OptDist.getPackages().getPackageByID("CS010101").getOptionals().get(0);
        */

        OptDist.getPackages().getPackageList().get(1).getID();
        OptDist.getPackages().getPackageList().get(1).getOptionals().get(0).getID();
        OptDist.getPackages().getPackageList().get(1).getOptionals().get(1).getID();
        OptDist.getPackages().getPackageList().get(1).getOptionals().get(2).getID();
        
        OptDist.getPackages().getPackageList().get(2).getID();
        OptDist.getPackages().getPackageList().get(2).getOptionals().get(0).getID();
        OptDist.getPackages().getPackageList().get(2).getOptionals().get(1).getID();
        OptDist.getPackages().getPackageList().get(2).getOptionals().get(2).getID();
        
        OptDist.getStudents().getStudentByReg("104PW4").getName();
        
        String nrmat = "104PW4";
        List<String> packIDs = new ArrayList<>();
        packIDs.add("CS2211");
        packIDs.add("CS3104");
        
        List<String> optIDs1 = new ArrayList<>();
        optIDs1.add("CS221101");
        optIDs1.add("CS221102");
        optIDs1.add("CS221103");
        
        List<String> optIDs12 = new ArrayList<>();
        optIDs12.add("CS310401");
        optIDs12.add("CS310402");
        optIDs12.add("CS310403");
        
        
        String nrmat2 = "160BN5";
        
        List<String> optIDs2 = new ArrayList<>();
        optIDs2.add("CS221101");
        optIDs2.add("CS221103");
        optIDs2.add("CS221102");
        
        List<String> optIDs22 = new ArrayList<>();
        optIDs22.add("CS310403");
        optIDs22.add("CS310402");
        optIDs22.add("CS310401");
        
        //OptDist.submitForm(nrmat, packIDs, optIDs1, optIDs12);
        
        OptDist.submitSinglePreference(nrmat, packIDs.get(0), optIDs1.get(0), 0);
        System.out.println(OptDist.submitSinglePreference(nrmat, packIDs.get(0), optIDs1.get(1), 1));
        System.out.println(OptDist.submitSinglePreference(nrmat, packIDs.get(1), optIDs12.get(2), 2));
        System.out.println(OptDist.submitSinglePreference(nrmat2, packIDs.get(0), optIDs2.get(1), 1));
        System.out.println(OptDist.submitSinglePreference(nrmat, packIDs.get(0), optIDs1.get(2), 2));
        
        //OptDist.submitForm(nrmat2, packIDs, optIDs2, optIDs22);
        System.out.println(OptDist.submitSinglePreference(nrmat2, packIDs.get(0), optIDs2.get(0), 0));
        System.out.println(OptDist.submitSinglePreference(nrmat2, packIDs.get(0), optIDs2.get(2), 2));
        OptDist.flushPreferences();
        
        
        System.out.println(OptDist.submitSinglePreference(nrmat, packIDs.get(1), optIDs12.get(0), 0));
        System.out.println(OptDist.submitSinglePreference(nrmat, packIDs.get(1), optIDs12.get(1), 1));
        System.out.println(OptDist.submitSinglePreference(nrmat2, packIDs.get(1), optIDs22.get(0), 0));
        System.out.println(OptDist.submitSinglePreference(nrmat2, packIDs.get(1), optIDs22.get(1), 1));
        System.out.println(OptDist.submitSinglePreference(nrmat2, packIDs.get(1), optIDs22.get(2), 2));
        
        OptDist.flushPreferences();
        
        System.out.println(OptDist.getStudents().getStudentByReg("104PW4").getPreference().getPreference().get(OptDist.getPackages().getPackageList().get(1)).get(0).getID());
         
        OptDist.distribute();
        
        Student unstudent = OptDist.getStudents().getStudentByReg("104PW4");
        System.out.println(OptDist.getDistribution().getResult().get(unstudent).get(0).getName());
        System.out.println(OptDist.getDistribution().getResult().get(unstudent).get(1).getName());
        
        System.out.println("==============");
        //System.out.println(OptDist.getPackages().getPackageList().getPackageByID ("CS3104"));
        
        OptDist.setExportExcelPath("D:\\workspace\\ParseExcel\\exported.xlsx");
        OptDist.exportDistribution();
    }
}
