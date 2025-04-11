import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.IOException;

public class RentalSystem {
	
	//Under development
	//private static RentalSystem instance;
	//This is for the Singleton  TASK !!!!!!!!!!!!!!!!

    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private RentalHistory rentalHistory = new RentalHistory();
    
    
    //Calls the method when an object is made
    public RentalSystem()
    {
    	loadData();
    }
   
    public static RentalSystem getInstance() {
        if (instance == null) {
            instance = new RentalSystem();
        }
        return instance;
    }
    
     public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        saveVehicle(vehicle);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
        saveCustomer(customer);
    }

    public void rentVehicle(Vehicle vehicle, Customer customer, LocalDate date, double amount) 
    {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.AVAILABLE) 
        {
            vehicle.setStatus(Vehicle.VehicleStatus.RENTED);
            
            RentalRecord record = new RentalRecord(vehicle, customer, date, amount, "RENT");
			rentalHistory.addRecord(record);
			saveRecord(record);
			saveAllVehicles();
            System.out.println("Vehicle rented to " + customer.getCustomerName());
        }
        else {
            System.out.println("Vehicle is not available for renting.");
        }
    }

    public void returnVehicle(Vehicle vehicle, Customer customer, LocalDate date, double extraFees) 
    {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.RENTED)
        {
            vehicle.setStatus(Vehicle.VehicleStatus.AVAILABLE);
            RentalRecord record=new RentalRecord(vehicle, customer, date, extraFees, "RETURN");
            saveRecord(record);
			saveAllVehicles();
            System.out.println("Vehicle returned by " + customer.getCustomerName());
        }
        else {
            System.out.println("Vehicle is not rented.");
        }
    }    

    public void displayVehicles(boolean onlyAvailable) 
    {

    	System.out.println("|     Type         |\tPlate\t|\tMake\t|\tModel\t|\tYear\t|");
    	System.out.println("---------------------------------------------------------------------------------");
    	 
        for (Vehicle v : vehicles) 
        {
            if (!onlyAvailable || v.getStatus() == Vehicle.VehicleStatus.AVAILABLE) 
            {
                System.out.println("|     " + (v instanceof Car ? "Car          " : "Motorcycle   ") + "|\t" + v.getLicensePlate() + "\t|\t" + v.getMake() + "\t|\t" + v.getModel() + "\t|\t" + v.getYear() + "\t|\t");
            }
        }
        System.out.println();
    }
    
    public void displayAllCustomers() 
    {
        for (Customer c : customers) 
        {
            System.out.println("  " + c.toString());
        }
    }
    
    public void displayRentalHistory() 
    {
        for (RentalRecord record : rentalHistory.getRentalHistory())
        {
            System.out.println(record.toString());
        }
    }
    
    public Vehicle findVehicleByPlate(String plate) 
    {
        for (Vehicle v : vehicles) 
        {
            if (v.getLicensePlate().equalsIgnoreCase(plate)) 
            {
                return v;
            }
        }
        return null;
    }
    
    public Customer findCustomerById(String id) 
    {
        for (Customer c : customers)
            if (c.getCustomerId() == Integer.parseInt(id))
                return c;
        return null;
    }
    
    //This save it to the file and holds its status RENTED OR NOT RENTED
    private void saveAllVehicles() {
        try (FileWriter writer = new FileWriter("vehicles.txt", false)) { // false = overwrite
            for (Vehicle vehicle : vehicles) {
                String type = (vehicle instanceof Car) ? "Car" :
                              (vehicle instanceof Motorcycle) ? "Motorcycle" :
                              (vehicle instanceof Truck) ? "Truck" : "Unknown";

                writer.write(type + "," +
                             vehicle.getLicensePlate() + "," +
                             vehicle.getMake() + "," +
                             vehicle.getModel() + "," +
                             vehicle.getYear() + "," +
                             vehicle.getStatus().toString() + "\n");
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Error saving all vehicles: " + e.getMessage());
        }
    }
    //SAVES IT TO MEMORY
    private void saveVehicle(Vehicle vehicle) {
        try (FileWriter writer = new FileWriter("vehicles.txt", true)) {
            String type = (vehicle instanceof Car) ? "Car" :
                          (vehicle instanceof Motorcycle) ? "Motorcycle" :
                          (vehicle instanceof Truck) ? "Truck" :
                          (vehicle instanceof SportCar) ? "SportCar" : "Unknown";

            writer.write(type + "," +
                         vehicle.getLicensePlate() + "," +
                         vehicle.getMake() + "," +
                         vehicle.getModel() + "," +
                         vehicle.getYear() + "," +
                         vehicle.getStatus().toString() + "\n");
        } catch (IOException e) {
            System.out.println("Error saving vehicle: " + e.getMessage());
        }
    }

    private void saveCustomer(Customer customer) {
        try (FileWriter writer = new FileWriter("customers.txt", true)) {
            writer.write(customer.getCustomerId() + "," + customer.getCustomerName() + "\n");
        } catch (IOException e) {
            System.out.println("Error saving customer: " + e.getMessage());
        }
    }

    private void saveRecord(RentalRecord record) {
        try (FileWriter writer = new FileWriter("rental_records.txt", true)) {
            writer.write(record.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Error saving record: " + e.getMessage());
        }
    }
    
    private void loadData() 
    {
        loadVehicles();
        loadCustomers();
        loadRentalRecords();
    }

    private void loadVehicles() 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader("vehicles.txt"))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] parts = line.split(",");
                String type = parts[0];
                String plate = parts[1];
                String make = parts[2];
                String model = parts[3];
                int year = Integer.parseInt(parts[4]);
                String statusText = parts[5].trim(); // NEW

                Vehicle v = null;

                if (type.equalsIgnoreCase("Car")) 
                {
                    v = new Car(make, model, year, 5);
                } else if (type.equalsIgnoreCase("Motorcycle")) 
                {
                    v = new Motorcycle(make, model, year, false);
                } else if (type.equalsIgnoreCase("Truck")) {
                    v = new Truck(make, model, year, 5);
                }

                if (v != null) 
                {
                    v.setLicensePlate(plate);
                    // Set vehicle status from file
                    if (statusText.equalsIgnoreCase("RENTED")) 
                    {
                        v.setStatus(Vehicle.VehicleStatus.RENTED);
                    } else {
                        v.setStatus(Vehicle.VehicleStatus.AVAILABLE);
                    }
                    vehicles.add(v);
                }
            }
        } 
        catch (Exception e) 
        {
            System.out.println("Could not load vehicles: " + e.getMessage());
        }
    }
    private void loadCustomers() 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader("customers.txt")))
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] parts = line.split(",");
                Customer c = new Customer(Integer.parseInt(parts[0]), parts[1]);
                customers.add(c);
            }
        } catch (Exception e) 
        {
            System.out.println("Could not load customers.");
        }
    }


    
    private void loadRentalRecords() 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader("rental_records.txt")))
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] parts = line.split("\\|");

                if (parts.length < 5) continue;

                String type = parts[0].trim(); // RENT or RETURN
                String plate = parts[1].split(":")[1].trim();
                String customerName = parts[2].split(":")[1].trim();
                String dateText = parts[3].split(":")[1].trim();
                String amountText = parts[4].split("\\$")[1].replaceAll("[^\\d.]","");
                double amount=Double.parseDouble(amountText);
                Vehicle foundVehicle = null;
                for (Vehicle v : vehicles) 
                {
                    if (v.getLicensePlate().equalsIgnoreCase(plate)) 
                    {
                        foundVehicle = v;
                        break;
                    }
                }

                Customer foundCustomer = null;
                for (Customer c : customers) 
                {
                    if (c.getCustomerName().equalsIgnoreCase(customerName))
{
                        foundCustomer = c;
                        break;
                    }
                }

                if (foundVehicle != null && foundCustomer != null) 
                {
                    RentalRecord record = new RentalRecord(
                        foundVehicle,
                        foundCustomer,
                        LocalDate.parse(dateText),
                        amount,
                        type
                    );
                    rentalHistory.addRecord(record);
                }
            }
        } 
        catch (Exception e) 
        {
            System.out.println("Could not load rental records: " + e.getMessage());
        }
    }
    

    

}
