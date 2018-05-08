package OptDist;

import java.util.List;

public abstract class FormAdministration {
	List<Form> formList;
	List<Package> optionalList;
	
	abstract void importStudList();
	
	abstract void importOptList();
	
	abstract List<Package> deleteOptional();
	
	abstract List<Package> addOptional();
	
	abstract List<Package> modifyOptionalQuantity(String packetName, String name, int quantity);
	
	abstract List<Package> modifyOptionalYear(String packetName, String name, int year);
	
	abstract List<Package> modifyOptionalSemester(String packetName, String name, int semester);
}
