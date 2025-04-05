import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.IOException;

public class RentalSystem {
	private static RentalSystem instance;
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private RentalHistory rentalHistory = new RentalHistory();
    
      
     public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        saveVehicle(vehicle);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
        saveCustomer(customer);
    }

    public void rentVehicle(Vehicle vehicle, Customer customer, LocalDate date, double amount) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.AVAILABLE) {
            vehicle.setStatus(Vehicle.VehicleStatus.RENTED);
            
            RentalRecord record = new RentalRecord(vehicle, customer, date, amount, "RENT");
			rentalHistory.addRecord(record);
			saveRecord(record);
            System.out.println("Vehicle rented to " + customer.getCustomerName());
        }
        else {
            System.out.println("Vehicle is not available for renting.");
        }
    }

    public void returnVehicle(Vehicle vehicle, Customer customer, LocalDate date, double extraFees) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.RENTED) {
            vehicle.setStatus(Vehicle.VehicleStatus.AVAILABLE);
            RentalRecord record=new RentalRecord(vehicle, customer, date, extraFees, "RETURN");
            saveRecord(record);
            System.out.println("Vehicle returned by " + customer.getCustomerName());
        }
        else {
            System.out.println("Vehicle is not rented.");
        }
    }    

    public void displayVehicles(boolean onlyAvailable) {
    	System.out.println("|     Type         |\tPlate\t|\tMake\t|\tModel\t|\tYear\t|");
    	System.out.println("---------------------------------------------------------------------------------");
    	 
        for (Vehicle v : vehicles) {
            if (!onlyAvailable || v.getStatus() == Vehicle.VehicleStatus.AVAILABLE) {
                System.out.println("|     " + (v instanceof Car ? "Car          " : "Motorcycle   ") + "|\t" + v.getLicensePlate() + "\t|\t" + v.getMake() + "\t|\t" + v.getModel() + "\t|\t" + v.getYear() + "\t|\t");
            }
        }
        System.out.println();
    }
    
    public void displayAllCustomers() {
        for (Customer c : customers) {
            System.out.println("  " + c.toString());
        }
    }
    
    public void displayRentalHistory() {
        for (RentalRecord record : rentalHistory.getRentalHistory()) {
            System.out.println(record.toString());
        }
    }
    
    public Vehicle findVehicleByPlate(String plate) {
        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equalsIgnoreCase(plate)) {
                return v;
            }
        }
        return null;
    }
    
    public Customer findCustomerById(String id) {
        for (Customer c : customers)
            if (c.getCustomerId() == Integer.parseInt(id))
                return c;
        return null;
    }
    
    private void saveVehicle(Vehicle vehicle)
    {
    	try (FileWriter writer = new FileWriter("vehicles.txt", true))
    	{
    		writer.write(vehicle.toString() + "\n");
    	}
    	catch (IOException e)
    	{
    		System.out.println("Error saving vehicle: " + e.getMessage());
    	}
    }
    
    private void saveCustomer(Customer customer)
    {
    	try(FileWriter writer = new FileWriter("customers.txt", true))
    	{
    		writer.write(customer.toString() + "\n");
    	}
    	catch (IOException e)
    	{
    		System.out.println("Error saving customer: " + e.getMessage());
    	}
    	
    }
    private void saveRecord(RentalRecord record)
    {
    	try (FileWriter writer = new FileWriter("rental_records.txt", true))
    	{
    		writer.write(record.toString() + "\n");
    	}
    	catch (IOException e)
    	{
    		System.out.println("Error saving records: "+e.getMessage());
    	}
    }
    
    private void loadData() {
        loadVehicles();
        loadCustomers();
        loadRentalRecords();
    }

    private void loadVehicles() {
        try (BufferedReader reader = new BufferedReader(new FileReader("vehicles.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Vehicle v = new Vehicle(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
                vehicles.add(v);
            }
        } catch (Exception e) {
            System.out.println("Could not load vehicles.");
        }
    }

    private void loadCustomers() {
        try (BufferedReader reader = new BufferedReader(new FileReader("customers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Customer c = new Customer(Integer.parseInt(parts[0]), parts[1]);
                customers.add(c);
            }
        } catch (Exception e) {
            System.out.println("Could not load customers.");
        }
    }

    private void loadRentalRecords() {
        try (BufferedReader reader = new BufferedReader(new FileReader("rental_records.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String type = parts[0].trim();
                String plate = parts[1].split(":")[1].trim();
                String name = parts[2].split(":")[1].trim();
                String dateStr = parts[3].split(":")[1].trim();
                String amountStr = parts[4].split("\\$")[1].trim();

                Vehicle v = findVehicleByPlate(plate);
                Customer c = findCustomerByName(name);
                if (v != null && c != null) {
                    RentalRecord record = new RentalRecord(v, c, LocalDate.parse(dateStr), Double.parseDouble(amountStr), type);
                    rentalHistory.addRecord(record);
                }
            }
        } catch (Exception e) {
            System.out.println("Could not load rental records.");
        }
    }

    private Customer findCustomerByName(String name) {
        for (Customer c : customers) {
            if (c.getCustomerName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }

}
